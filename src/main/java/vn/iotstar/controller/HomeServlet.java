package vn.iotstar.controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;



@WebServlet("/home")
public class HomeServlet extends HttpServlet {



@Override
protected void doGet(
HttpServletRequest request,
HttpServletResponse response)

throws IOException, ServletException {

		response.setBufferSize(64 * 1024);

request.getRequestDispatcher(
"/views/home.jsp"
)
.forward(
request,
response
);


}


}