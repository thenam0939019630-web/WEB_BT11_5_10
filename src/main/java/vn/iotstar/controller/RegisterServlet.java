package vn.iotstar.controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.util.EmailUtil;


import java.io.IOException;
import java.security.SecureRandom;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;



@WebServlet("/register")
public class RegisterServlet
extends HttpServlet {

private UserService userService;

@Override
public void init() {
	userService = new UserServiceImpl();
}

protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	request.getRequestDispatcher("/views/register.jsp").forward(request, response);
}



protected void doPost(
HttpServletRequest request,
HttpServletResponse response)

throws ServletException, IOException{

request.setCharacterEncoding("UTF-8");


String username =
request.getParameter("username");


String email =
request.getParameter("email");


String password =
request.getParameter("password");

String fullname = request.getParameter("fullname");

if (username == null || username.isBlank() || email == null || email.isBlank()
		|| password == null || password.isBlank() || fullname == null || fullname.isBlank()) {
	request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
	request.getRequestDispatcher("/views/register.jsp").forward(request, response);
	return;
}

if (userService.findByUsername(username) != null || userService.findByEmail(email) != null) {
	request.setAttribute("error", "Username hoặc email đã được sử dụng.");
	request.getRequestDispatcher("/views/register.jsp").forward(request, response);
	return;
}



SecureRandom random = new SecureRandom();



String otp =
String.valueOf(
100000 +
random.nextInt(900000)
);



HttpSession session =
request.getSession();



session.setAttribute(
"OTP",
otp
);



session.setAttribute(
"username",
username
);


session.setAttribute(
"email",
email
);


session.setAttribute(
"password",
password
);

session.setAttribute("fullname", fullname);



try {
	EmailUtil.sendOTP(email, otp);
} catch (IllegalStateException e) {
	session.removeAttribute("OTP");
	session.removeAttribute("password");
	log("OTP email delivery failed", e);
	request.setAttribute("error", e.getMessage());
	request.getRequestDispatcher("/views/register.jsp").forward(request, response);
	return;
}



response.sendRedirect(request.getContextPath() + "/verify");



}


}
