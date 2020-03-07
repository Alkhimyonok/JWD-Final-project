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
 * Search edition+
 *
 * @see EditionService
 */

public class SearchCommand implements Command {
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        List<Edition> list;
        try {
            list = editionService.getListByParameters(title, author);
            request.setAttribute("list", list);
            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(PageName.EDITIONS);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(PageName.EDITIONS);
                requestDispatcher.forward(request, response);
            } else response.sendRedirect(PageName.ERROR);
        }
    }
}
