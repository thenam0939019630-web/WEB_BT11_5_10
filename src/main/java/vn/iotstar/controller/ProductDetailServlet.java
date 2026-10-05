package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

import java.io.IOException;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var product = productService.findById(Integer.valueOf(request.getParameter("id")));
            if (product == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
            request.setAttribute("product", product);
            request.getRequestDispatcher("/views/product-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) { response.sendError(HttpServletResponse.SC_BAD_REQUEST); }
    }
}