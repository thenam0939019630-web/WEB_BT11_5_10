package vn.iotstar.service;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import vn.iotstar.entity.*;
import java.util.Map;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingServiceTest {
    static EntityManagerFactory factory;
    ShoppingService service;
    int userId, otherId, productId, secondId;
    @BeforeAll static void database() {
        factory = Persistence.createEntityManagerFactory("WebShopPU", Map.of(
                "jakarta.persistence.jdbc.driver", "org.h2.Driver",
                "jakarta.persistence.jdbc.url", "jdbc:h2:mem:shopping;DB_CLOSE_DELAY=-1",
                "jakarta.persistence.jdbc.user", "sa", "jakarta.persistence.jdbc.password", "",
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect",
                "hibernate.hbm2ddl.auto", "create-drop", "hibernate.show_sql", "false"));
        edit(em -> {
            UserRole admin = new UserRole(); admin.setRoleName("Admin"); em.persist(admin);
            UserRole user = new UserRole(); user.setRoleName("User"); em.persist(user);
        });
    }
    @AfterAll static void close() { factory.close(); }
    static void edit(Consumer<EntityManager> action) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin(); action.accept(em); em.getTransaction().commit();
        }
    }
    @BeforeEach void seed() {
        service = new ShoppingService(factory::createEntityManager);
        edit(em -> {
            User user = new User(); user.setRole(em.find(UserRole.class, 2)); em.persist(user); userId = user.getUserid();
            User other = new User(); other.setRole(em.find(UserRole.class, 2)); em.persist(other); otherId = other.getUserid();
            Product p = new Product(); p.setProductName("Laptop"); p.setPrice(1000.0); p.setStock(5); p.setStatus(1); em.persist(p); productId = p.getProductId();
            Product q = new Product(); q.setProductName("Phone"); q.setPrice(500.0); q.setStock(5); q.setStatus(1); em.persist(q); secondId = q.getProductId();
        });
    }
    int stock(int id) { try (EntityManager em = factory.createEntityManager()) { return em.find(Product.class, id).getStock(); } }
    int checkout(int cart) { return service.checkout(userId, cart, "Người nhận", "0912345678", "01 Võ Văn Ngân"); }

    @Test void cartMergesAndEnforcesBoundsAndOwnership() {
        service.change(userId, productId, 2, "add"); service.change(userId, productId, 2, "add");
        assertEquals(4, service.getCart(userId).getItems().get(0).getQuantity());
        assertThrows(IllegalArgumentException.class, () -> service.change(userId, productId, Integer.MAX_VALUE, "add"));
        assertThrows(IllegalArgumentException.class, () -> service.change(userId, productId, 0, "update"));
        assertThrows(IllegalArgumentException.class, () -> service.change(otherId, productId, 1, "update"));
        assertNull(service.getCart(otherId));
        service.change(userId, productId, 1, "update");
        assertEquals(1, service.getCart(userId).getItems().get(0).getQuantity());
        service.change(userId, productId, 0, "remove");
        assertTrue(service.getCart(userId).getItems().isEmpty());
    }
    @Test void codSnapshotsOrderAndPreventsReplay() {
        service.change(userId, productId, 2, "add"); int cart = service.getCart(userId).getCartId();
        assertEquals(cart, checkout(cart)); assertEquals(3, stock(productId)); assertNull(service.getCart(userId));
        assertThrows(IllegalArgumentException.class, () -> checkout(cart)); assertEquals(3, stock(productId));
        edit(em -> em.find(Product.class, productId).setPrice(9000.0));
        Cart order = service.orders(userId, 1).get(0);
        assertEquals("COD", order.getPaymentMethod()); assertEquals(2000.0, order.getTotal().doubleValue());
        assertNotNull(order.getBuyDate()); assertTrue(service.orders(otherId, null).isEmpty());
        for (int status = 1; status <= 8; status++) {
            final int state = status; edit(em -> em.find(Cart.class, cart).setStatus(state));
            assertEquals(1, service.orders(userId, status).size());
            assertTrue(service.orders(userId, status == 8 ? 1 : status + 1).isEmpty());
        }
        assertThrows(IllegalArgumentException.class, () -> service.orders(userId, 0));
    }
    @Test void checkoutRollsBackAllStockWhenOneItemUnavailable() {
        service.change(userId, productId, 2, "add"); service.change(userId, secondId, 2, "add");
        int cart = service.getCart(userId).getCartId();
        edit(em -> em.find(Product.class, secondId).setStock(1));
        assertThrows(IllegalArgumentException.class, () -> checkout(cart));
        assertEquals(5, stock(productId)); assertEquals(1, stock(secondId));
        assertEquals(2, service.getCart(userId).getItems().size()); assertTrue(service.orders(userId, null).isEmpty());
    }
    @Test void rejectsInvalidRecipientAndPriceChanges() {
        service.change(userId, productId, 1, "add"); int cart = service.getCart(userId).getCartId();
        assertThrows(IllegalArgumentException.class, () -> service.checkout(userId, cart, " ", "bad", ""));
        edit(em -> em.find(Product.class, productId).setPrice(2000.0));
        assertThrows(IllegalArgumentException.class, () -> checkout(cart)); assertEquals(5, stock(productId));
        service.change(userId, productId, 1, "update"); checkout(cart);
        assertEquals(2000.0, service.orders(userId, null).get(0).getTotal().doubleValue());
    }
    @Test void concurrentBuyersCannotOversell() throws Exception {
        service.change(userId, productId, 4, "add"); service.change(otherId, productId, 4, "add");
        int firstCart = service.getCart(userId).getCartId(), secondCart = service.getCart(otherId).getCartId();
        var start = new java.util.concurrent.CountDownLatch(1);
        try (var pool = java.util.concurrent.Executors.newFixedThreadPool(2)) {
            var first = pool.submit(() -> { start.await(); try { checkout(firstCart); return 1; } catch (IllegalArgumentException e) { return 0; } });
            var second = pool.submit(() -> { start.await(); try { service.checkout(otherId, secondCart, "Other", "0912345678", "Address"); return 1; } catch (IllegalArgumentException e) { return 0; } });
            start.countDown(); assertEquals(1, first.get() + second.get());
        }
        assertEquals(1, stock(productId));
        assertEquals(1, service.orders(userId, null).size() + service.orders(otherId, null).size());
    }
}
