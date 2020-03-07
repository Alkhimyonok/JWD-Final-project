package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * Implements {@link Command} interface.
 * Get list subscriptions for user( redirect to subscriptions page).
 * If error service - redirect to error page.+
 *
 * @see SubscribeService
 */

public class ListSubscriptionsCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            List<Subscribe> subscribeList = subscribeService.getListByIdUser(user.getId());
            request.setAttribute("list", subscribeList);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.SUBSCRIPTIONS);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
