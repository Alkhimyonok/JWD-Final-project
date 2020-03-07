package com.epam.jwd.web_app.controller.filter;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.*;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private AdminService adminService = ServiceFactory.getInstance().getAdminService();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Login login = new Login(email, password);
        String param = request.getParameter("param");
        if (email != null && password != null) {
            if (password.equals("") || email.equals("")) {
                request.setAttribute("err", "locale.err.noFull");
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.LOGIN);
                rd.forward(request, response);
                return;
            }
            if (param == null) {
                try {
                    if (userService.isLoginUser(login)) {
                    } else {
                        request.setAttribute("err", "locale.err.login.false");
                        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.LOGIN);
                        rd.forward(request, response);
                        return;
                    }
                } catch (ServiceException e) {
                    response.sendRedirect(PageName.ERROR);
                    return;
                }
            } else {
                try {
                    if (adminService.isAdmin(login)) {
                        session.setAttribute("user", "admin");
                    } else {
                        request.setAttribute("err", "locale.err.login.false");
                        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.ADMIN_LOGIN);
                        rd.forward(request, response);
                        return;
                    }
                } catch (ServiceException e) {
                    response.sendRedirect(PageName.ERROR);
                    return;
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
