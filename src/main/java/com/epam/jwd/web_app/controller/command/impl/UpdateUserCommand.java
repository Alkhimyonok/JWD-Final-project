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
 * Update user info+
 *
 * @see UserService
 */

public class UpdateUserCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String newFirstName = request.getParameter("newFirstName");
        String newLastName = request.getParameter("newLastName");
        String newEmail = request.getParameter("newEmail");
        User newUser = new User(user.getId(), newFirstName, newLastName, newEmail, "good");
        try {
            userService.updateUser(newUser);
            session.setAttribute("user", newUser);
            response.sendRedirect(PageName.INDEX);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
