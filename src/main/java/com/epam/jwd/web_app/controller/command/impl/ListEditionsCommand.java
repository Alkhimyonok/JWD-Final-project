package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.EditionService;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Implements {@link Command} interface.
 * Get list for user(redirect to editions page) or admin(redirect to editions admin page).
 * If error service - redirect to error page.+
 *
 * @see EditionService
 */

public class ListEditionsCommand implements Command {
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String param = request.getParameter("param");
        String type = "";
        if (request.getParameter("type") != null) {
            type = request.getParameter("type");
        }
        try {
            List<Edition> editionList = editionService.getListByType(type);
            request.setAttribute("list", editionList);
            String url;
            if (param != null) {
                url = PageName.EDITIONS_ADMIN;
            } else url = PageName.EDITIONS;
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
