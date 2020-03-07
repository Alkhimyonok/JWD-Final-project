package com.epam.jwd.web_app.controller.command.impl;

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

/**
 * Implements {@link Command} interface.
 * Admin creates edition by parameters and send message.
 * Send message if no full info or no such author.
 * If error service - redirect to error page.+
 * @see EditionService
 */


public class CreateEditionCommand implements Command {
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String type = request.getParameter("type");
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String pathImg = request.getParameter("pathImg");
        String priceMonth = request.getParameter("priceMonth");
        try {
            editionService.createEdition(type, author, title, description, pathImg, priceMonth);
            request.setAttribute("err", "locale.admin.deleteDone");///////////////////////////////////////////
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.CREATE_EDITION);
            rd.forward(request, response);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.CREATE_EDITION);
                rd.forward(request, response);
            } else response.sendRedirect(PageName.ERROR);
        }
    }
}
