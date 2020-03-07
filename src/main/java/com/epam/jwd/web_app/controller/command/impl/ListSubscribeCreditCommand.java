package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Subscribe;
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
 * Create list subscribe in credit for admin( redirect to subscribe list page).
 * If error service - redirect to error page.+
 *
 * @see SubscribeService
 */

public class ListSubscribeCreditCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<Subscribe> creditList = subscribeService.getListInCredit();
            request.setAttribute("creditList", creditList);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.SUBSCRIBE_LIST);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
