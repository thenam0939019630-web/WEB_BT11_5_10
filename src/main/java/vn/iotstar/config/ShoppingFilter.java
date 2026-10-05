package vn.iotstar.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class ShoppingFilter implements Filter {
    public void doFilter(ServletRequest input, ServletResponse output, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) input;
        HttpServletResponse res = (HttpServletResponse) output;
        HttpSession session = req.getSession();
        synchronized (session) {
            if (session.getAttribute("shoppingToken") == null)
                session.setAttribute("shoppingToken", UUID.randomUUID().toString());
        }
        String path = req.getServletPath();
        if (path.equals("/cart") || path.equals("/checkout") || path.equals("/orders")) {
            User user = (User) session.getAttribute("user");
            if (user == null) { res.sendRedirect(req.getContextPath() + "/login"); return; }
            if (user.getRole() == null || !Integer.valueOf(2).equals(user.getRole().getRoleId())) {
                res.sendError(403, "Chức năng chỉ dành cho User."); return;
            }
            res.setHeader("Cache-Control", "no-store");
            if ("POST".equals(req.getMethod()) && !session.getAttribute("shoppingToken").equals(req.getParameter("token"))) {
                res.sendError(403, "Phiên thao tác không hợp lệ. Vui lòng tải lại trang."); return;
            }
        }
        chain.doFilter(input, output);
    }
}
