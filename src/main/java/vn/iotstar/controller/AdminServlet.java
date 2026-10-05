package vn.iotstar.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Seller;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String path = request.getPathInfo();
        String type = path != null && path.endsWith("/product") ? "product" : "category";
        int page = Math.max(1, parseInt(request.getParameter("page"), 1));
        int pageSize = 5;
        long total = "product".equals(type) ? productService.countAll() : categoryService.countAll();
        int pageCount = Math.max(1, (int) ((total + pageSize - 1) / pageSize));
        page = Math.min(page, pageCount);
        List<?> records = "product".equals(type)
            ? productService.findPage((page - 1) * pageSize, pageSize)
            : categoryService.findPage((page - 1) * pageSize, pageSize);
        request.setAttribute("type", type);
        request.setAttribute("records", records);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("categories", categoryService.findAll());
        request.getRequestDispatcher("/views/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        String type = path != null && path.endsWith("/product") ? "product" : "category";
        String action = value(request.getParameter("action"), "save");
        Integer id = nullableInt(request.getParameter("id"));
        if ("category".equals(type)) {
            Category category = id == null ? new Category() : categoryService.findById(id);
            if (category != null) {
                category.setCategoryName(request.getParameter("name"));
                category.setImages(request.getParameter("images"));
                category.setStatus(parseInt(request.getParameter("status"), 1));
                if ("delete".equals(action)) categoryService.delete(category);
                else if (id == null) categoryService.insert(category);
                else categoryService.update(category);
            }
        } else {
            Product product = id == null ? new Product() : productService.findById(id);
            if (product != null) {
                product.setProductName(request.getParameter("name"));
                product.setProductCode(parseLong(request.getParameter("code"), 0L));
                product.setDescription(request.getParameter("description"));
                product.setPrice(parseDouble(request.getParameter("price"), 0));
                product.setAmount(parseInt(request.getParameter("amount"), 0));
                product.setStock(parseInt(request.getParameter("stock"), 0));
                product.setWishlist(parseInt(request.getParameter("wishlist"), 0));
                product.setImages(request.getParameter("images"));
                product.setStatus(parseInt(request.getParameter("status"), 1));
                product.setCreateDate(new Date());
                EntityManager em = JPAConfig.getEntityManager();
                try {
                    Integer categoryId = nullableInt(request.getParameter("categoryId"));
                    Integer sellerId = nullableInt(request.getParameter("sellerId"));
                    product.setCategory(categoryId == null ? null : em.find(Category.class, categoryId));
                    product.setSeller(sellerId == null ? null : em.find(Seller.class, sellerId));
                } finally { em.close(); }
                if ("delete".equals(action)) productService.delete(product);
                else if (id == null) productService.insert(product);
                else productService.update(product);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/" + type);
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        return user instanceof vn.iotstar.entity.User u && u.getRole() != null && u.getRole().getRoleId() == 1;
    }
    private static String value(String value, String fallback) { return value == null || value.isBlank() ? fallback : value; }
    private static int parseInt(String value, int fallback) { try { return Integer.parseInt(value); } catch (Exception e) { return fallback; } }
    private static Integer nullableInt(String value) { try { return value == null || value.isBlank() ? null : Integer.valueOf(value); } catch (Exception e) { return null; } }
    private static long parseLong(String value, long fallback) { try { return Long.parseLong(value); } catch (Exception e) { return fallback; } }
    private static double parseDouble(String value, double fallback) { try { return Double.parseDouble(value); } catch (Exception e) { return fallback; } }
}