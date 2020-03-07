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

/**
 * Implements {@link Command} interface.
 *
 * Get detail edition by id( redirect to detail page).
 * If error service - redirect to error page.+
 *
 * @see EditionService
 */

public class DetailEditionCommand implements Command {
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long idEdition = Integer.parseInt(request.getParameter("idEdition"));
        try {
            Edition edition = editionService.getEditionById(idEdition);
            request.setAttribute("edition", edition);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.DETAIL);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
