package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.UserService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Implements {@link Command} interface.
 * Reset password for user+
 *
 * @see UserService
 */


public class ResetPasswordCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String newPassword1 = request.getParameter("newPassword1");
        String newPassword2 = request.getParameter("newPassword2");
        if (newPassword1.equals(newPassword2)) {
            try {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    userService.resetPassword(user.getId(), newPassword1);
                    response.sendRedirect(PageName.INDEX);
                    return;
                } else {
                    user = userService.getUserByParam(firstName, lastName, email);
                    if (user == null) {
                        request.setAttribute("err", "locale.err.restorePassword");
                        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(PageName.RESTORE_PASSWORD);
                        requestDispatcher.forward(request, response);
                        return;
                    } else {
                        userService.resetPassword(user.getId(), newPassword1);
                        response.sendRedirect(PageName.LOGIN);
                        return;
                    }
                }
            } catch (ServiceException e) {
                response.sendRedirect(PageName.ERROR);
                return;
            }
        } else {
            request.setAttribute("err", "locale.err.resetPassword");
            String URL = PageName.RESET_PASSWORD;
            if (firstName != null) {
                URL = PageName.RESTORE_PASSWORD;
            }
            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(URL);
            requestDispatcher.forward(request, response);
        }
    }
}
