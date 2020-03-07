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
 * Admin deletes edition and send message(redirect to main admin page).
 * Send message if edition is already block.
 * If error service - redirect to error page.+
 *
 * @see EditionService
 */

public class DeleteEditionCommand implements Command {
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id = Long.parseLong(request.getParameter("idEdition"));
        try {
            editionService.deleteEditionById(id);
            request.setAttribute("err", "locale.admin.deleteDone");
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.ADMIN_MAIN);
            rd.forward(request, response);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.ADMIN_MAIN);
                rd.forward(request, response);
            } else response.sendRedirect(PageName.ERROR);
        }
    }
}
