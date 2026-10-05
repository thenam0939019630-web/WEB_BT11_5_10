package vn.iotstar.controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import vn.iotstar.entity.Product;
import vn.iotstar.service.ProductService;
import vn.iotstar.service.impl.ProductServiceImpl;


import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;



@WebServlet("/product")
public class ProductServlet extends HttpServlet {



private ProductService productService;



@Override
public void init(){

    productService =
    new ProductServiceImpl();

}





@Override
protected void doGet(
HttpServletRequest request,
HttpServletResponse response)

throws ServletException, IOException {

    response.setBufferSize(64 * 1024);

    Map<Integer, List<Product>> sellerProducts = new LinkedHashMap<>();
    for (Product product : productService.findAll()) {
        Integer sellerId = product.getSeller() == null ? null : product.getSeller().getSellerId();
        sellerProducts.computeIfAbsent(sellerId, ignored -> new java.util.ArrayList<>()).add(product);
    }
    request.setAttribute("sellerProducts", sellerProducts);



    request.getRequestDispatcher(
        "/views/product.jsp"
    )
    .forward(
        request,
        response
    );


}



}