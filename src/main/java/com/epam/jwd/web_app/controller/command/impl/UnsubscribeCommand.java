package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Implements {@link Command} interface.
 * Unsubscribe by idSubscribe+
 *
 * @see SubscribeService
 */

public class UnsubscribeCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long idSubscribe = Integer.parseInt(request.getParameter("idSubscribe"));
        try {
            subscribeService.unsubscribe(idSubscribe);
            response.sendRedirect(PageName.INDEX);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
