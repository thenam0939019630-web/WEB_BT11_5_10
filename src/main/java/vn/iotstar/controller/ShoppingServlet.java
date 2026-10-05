package vn.iotstar.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.*;
import vn.iotstar.service.ShoppingService;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart", "/checkout", "/orders"})
public class ShoppingServlet extends HttpServlet {
    private final ShoppingService service = new ShoppingService();
    private int userId(HttpServletRequest req) { return ((User) req.getSession().getAttribute("user")).getUserid(); }
    private int number(String input) {
        try { return Integer.parseInt(input); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Mã hoặc số lượng không hợp lệ."); }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            if (path.equals("/orders")) {
                String raw = req.getParameter("status");
                Integer status = raw == null || raw.isBlank() ? null : number(raw);
                req.setAttribute("orders", service.orders(userId(req), status));
                req.setAttribute("selectedStatus", status);
                req.setAttribute("statuses", OrderStatus.VALUES);
            } else {
                Cart cart = service.getCart(userId(req));
                if (path.equals("/checkout") && (cart == null || cart.getItems().isEmpty())) {
                    req.getSession().setAttribute("shoppingMessage", "Hãy thêm sản phẩm vào giỏ trước khi thanh toán.");
                    res.sendRedirect(req.getContextPath() + "/cart"); return;
                }
                req.setAttribute("cart", cart);
            }
            req.setAttribute("message", req.getSession().getAttribute("shoppingMessage"));
            req.getSession().removeAttribute("shoppingMessage");
            req.getRequestDispatcher("/WEB-INF/views" + path + ".jsp").forward(req, res);
        } catch (IllegalArgumentException e) { res.sendError(400, e.getMessage()); }
        catch (RuntimeException e) { throw new ServletException("Không thể tải dữ liệu mua hàng.", e); }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/orders")) { res.sendError(405); return; }
        try {
            if (path.equals("/checkout")) {
                int id = service.checkout(userId(req), number(req.getParameter("cartId")),
                        req.getParameter("receiverName"), req.getParameter("receiverPhone"), req.getParameter("shippingAddress"));
                req.getSession().setAttribute("shoppingMessage", "Đặt đơn #" + id + " thành công. Thanh toán tiền mặt khi nhận hàng (COD).");
                res.sendRedirect(req.getContextPath() + "/orders");
            } else {
                String action = req.getParameter("action");
                service.change(userId(req), number(req.getParameter("productId")),
                        "remove".equals(action) ? 0 : number(req.getParameter("quantity")), action);
                req.getSession().setAttribute("shoppingMessage", "Đã cập nhật giỏ hàng.");
                res.sendRedirect(req.getContextPath() + "/cart");
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage()); doGet(req, res);
        } catch (RuntimeException e) {
            log("Shopping operation failed", e);
            req.setAttribute("error", "Không thể thực hiện thao tác. Vui lòng tải lại giỏ hàng và thử lại.");
            doGet(req, res);
        }
    }
}
