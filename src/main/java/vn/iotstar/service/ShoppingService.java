package vn.iotstar.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.*;
import java.util.*;
import java.util.function.Function;

public class ShoppingService {
    private final java.util.function.Supplier<EntityManager> entityManagers;
    public ShoppingService() { this(JPAConfig::getEntityManager); }
    public ShoppingService(java.util.function.Supplier<EntityManager> entityManagers) {
        this.entityManagers = entityManagers;
    }
    private <T> T transaction(Function<EntityManager, T> action) {
        EntityManager em = entityManagers.get();
        try {
            em.getTransaction().begin();
            T result = action.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    private User lockUser(EntityManager em, int userId) {
        User user = em.find(User.class, userId, LockModeType.PESSIMISTIC_WRITE);
        if (user == null || user.getRole() == null || !Integer.valueOf(2).equals(user.getRole().getRoleId()))
            throw new IllegalArgumentException("Chức năng chỉ dành cho tài khoản User.");
        return user;
    }

    private Cart active(EntityManager em, int userId) {
        return em.createQuery("select c from Cart c where c.user.userid=:uid and c.status=0", Cart.class)
                .setParameter("uid", userId).getResultStream().findFirst().orElse(null);
    }

    private void initialize(Cart cart) {
        if (cart != null) for (CartItem item : cart.getItems()) item.getProduct().getProductName();
    }

    public Cart getCart(int userId) {
        return transaction(em -> { Cart cart = active(em, userId); initialize(cart); return cart; });
    }

    private void validate(Product product, long quantity) {
        if (product == null || !Integer.valueOf(1).equals(product.getStatus()))
            throw new IllegalArgumentException("Sản phẩm không còn được bán.");
        if (quantity < 1 || product.getStock() == null || quantity > product.getStock())
            throw new IllegalArgumentException("Số lượng phải từ 1 đến tồn kho hiện tại (" +
                    (product.getStock() == null ? 0 : product.getStock()) + ").");
        if (product.getPrice() == null || !Double.isFinite(product.getPrice()) || product.getPrice() < 0)
            throw new IllegalArgumentException("Giá sản phẩm không hợp lệ.");
    }

    public void change(int userId, int productId, int quantity, String action) {
        transaction(em -> {
            User user = lockUser(em, userId);
            Cart cart = active(em, userId);
            CartItem item = cart == null ? null : cart.getItems().stream()
                    .filter(i -> i.getProduct().getProductId() == productId).findFirst().orElse(null);
            if ("remove".equals(action)) {
                if (item == null) throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng.");
                cart.getItems().remove(item);
                return null;
            }
            if (!"add".equals(action) && !"update".equals(action))
                throw new IllegalArgumentException("Thao tác không hợp lệ.");
            if (quantity < 1) throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
            if ("update".equals(action) && item == null)
                throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng.");
            Product product = em.find(Product.class, productId, LockModeType.PESSIMISTIC_WRITE);
            long desired = "add".equals(action) && item != null ? (long) item.getQuantity() + quantity : quantity;
            validate(product, desired);
            if (cart == null) {
                cart = new Cart(); cart.setUser(user); cart.setStatus(0); em.persist(cart);
            }
            if (item == null) {
                item = new CartItem(); item.setCart(cart); item.setProduct(product); cart.getItems().add(item);
            }
            item.setQuantity((int) desired); item.setUnitPrice(product.getPrice());
            item.setProductName(product.getProductName());
            return null;
        });
    }

    public int checkout(int userId, int cartId, String name, String phone, String address) {
        final String receiver = required(name, 100, "Tên người nhận");
        final String contact = required(phone, 20, "Số điện thoại");
        final String destination = required(address, 500, "Địa chỉ");
        if (!contact.matches("\\+?[0-9]{9,15}")) throw new IllegalArgumentException("Số điện thoại phải có 9–15 chữ số.");
        return transaction(em -> {
            lockUser(em, userId);
            Cart cart = active(em, userId);
            if (cart == null || cart.getCartId() != cartId || cart.getItems().isEmpty())
                throw new IllegalArgumentException("Giỏ hàng trống hoặc đơn này đã được đặt. Vui lòng kiểm tra lịch sử.");
            List<CartItem> items = new ArrayList<>(cart.getItems());
            items.sort(Comparator.comparing(i -> i.getProduct().getProductId()));
            for (CartItem item : items) {
                Product product = item.getProduct();
                em.refresh(product, LockModeType.PESSIMISTIC_WRITE);
                validate(product, item.getQuantity());
                if (Double.compare(item.getUnitPrice(), product.getPrice()) != 0)
                    throw new IllegalArgumentException("Giá sản phẩm đã thay đổi. Hãy cập nhật sản phẩm trong giỏ trước khi đặt hàng.");
                product.setStock(product.getStock() - item.getQuantity());
                item.setProductName(product.getProductName());
            }
            cart.setReceiverName(receiver); cart.setReceiverPhone(contact); cart.setShippingAddress(destination);
            cart.setPaymentMethod("COD"); cart.setBuyDate(new Date()); cart.setStatus(1);
            return cart.getCartId();
        });
    }

    private String required(String value, int max, String label) {
        if (value == null || value.isBlank() || value.trim().length() > max)
            throw new IllegalArgumentException(label + " bắt buộc, tối đa " + max + " ký tự.");
        return value.trim();
    }

    public List<Cart> orders(int userId, Integer status) {
        if (status != null && !OrderStatus.VALUES.containsKey(status))
            throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ.");
        return transaction(em -> {
            var query = em.createQuery("select c from Cart c where c.user.userid=:uid and c.status between 1 and 8"
                    + (status == null ? "" : " and c.status=:status") + " order by c.buyDate desc, c.cartId desc", Cart.class)
                    .setParameter("uid", userId);
            if (status != null) query.setParameter("status", status);
            List<Cart> orders = query.getResultList(); orders.forEach(this::initialize); return orders;
        });
    }
}
