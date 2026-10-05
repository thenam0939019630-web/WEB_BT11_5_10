package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.User;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

import java.io.IOException;

@WebServlet("/seller/home")
public class SellerHomeServlet extends HttpServlet {
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object currentUser = request.getSession().getAttribute("user");
        if (!(currentUser instanceof User user) || user.getRole() == null
                || !Integer.valueOf(3).equals(user.getRole().getRoleId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (user.getSeller() == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Tài khoản chưa được gán cửa hàng.");
            return;
        }
        request.setAttribute("products", productService.findBySellerId(user.getSeller().getSellerId()));
        request.getRequestDispatcher("/views/seller-home.jsp").forward(request, response);
    }
}