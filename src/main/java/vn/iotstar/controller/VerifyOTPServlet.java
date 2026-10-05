package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.entity.UserRole;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet({"/verify", "/verify-otp"})
public class VerifyOTPServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        String submittedOtp = request.getParameter("otp");
        String expectedOtp = session == null ? null : (String) session.getAttribute("OTP");

        if (session == null || expectedOtp == null || !expectedOtp.equals(submittedOtp)) {
            request.setAttribute("error", "OTP không chính xác hoặc đã hết phiên đăng ký.");
            request.getRequestDispatcher("/views/verify.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername((String) session.getAttribute("username"));
        user.setEmail((String) session.getAttribute("email"));
        user.setPassword((String) session.getAttribute("password"));
        user.setFullname((String) session.getAttribute("fullname"));
        user.setStatus(1);
        UserRole role = new UserRole();
        role.setRoleId(2);
        user.setRole(role);

        try {
            userService.register(user);
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } catch (RuntimeException e) {
            request.setAttribute("error", "Không lưu được tài khoản. Username hoặc email có thể đã tồn tại.");
            request.getRequestDispatcher("/views/verify.jsp").forward(request, response);
        }
    }
}