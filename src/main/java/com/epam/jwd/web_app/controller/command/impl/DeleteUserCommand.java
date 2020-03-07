package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.UserService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Implements {@link Command} interface.
 * Delete user( redirect to index page).
 * If error service - redirect to error page.+
 * @see UserService
 */

public class DeleteUserCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                userService.deleteUser(user);
                session.removeAttribute("user");
                session.removeAttribute("news");
                session.removeAttribute("cart");
                response.sendRedirect(PageName.INDEX);
            } catch (ServiceException e) {
                response.sendRedirect(PageName.ERROR);
            }
        }
    }
}
