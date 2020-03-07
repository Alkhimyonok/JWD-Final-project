package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.UserService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link Command} interface.
 * Admin blocks user by id user and send message.
 * Send message if user is already blocked.
 * If error service - redirect to error page or no such user.+
 *
 * @see UserService
 */

public class BlockUserCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long idUser = Long.parseLong(request.getParameter("idUser"));
        try {
            userService.blockUserById(idUser);
            request.setAttribute("err", "locale.admin.deleteDone");///////////////////////////////////////
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.ADMIN_MAIN);
            rd.forward(request, response);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.SUBSCRIBE_LIST);
                rd.forward(request, response);
            } else {
                response.sendRedirect(PageName.ERROR);
            }
        }
    }
}
