package vn.iotstar.controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;


import java.io.IOException;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {



    private UserService userService;



    @Override
    public void init(){

        userService =
        new UserServiceImpl();

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)

            throws IOException, ServletException {



        String username =
        request.getParameter("username");



        String password =
        request.getParameter("password");



        User user =
        userService.login(
                username,
                password
        );



        if(user != null){


            HttpSession session =
            request.getSession();



            session.setAttribute(
                    "user",
                    user
            );



            /*
                Phân quyền

                1 Admin
                2 User
                3 Seller
            */


            Integer roleId =
            user.getRole()
                .getRoleId();



            if(Integer.valueOf(1).equals(roleId)){


                response.sendRedirect(request.getContextPath() + "/admin/category");


            }
            else if(Integer.valueOf(3).equals(roleId)){


                response.sendRedirect(request.getContextPath() + "/seller/home");


            }
            else{


                response.sendRedirect(request.getContextPath() + "/home");


            }



        }
        else{


            request.setAttribute(
            "error",
            "Sai tài khoản hoặc mật khẩu"
            );


            request.getRequestDispatcher(
            "/views/login.jsp"
            )
            .forward(
            request,
            response
            );


        }


    }


}