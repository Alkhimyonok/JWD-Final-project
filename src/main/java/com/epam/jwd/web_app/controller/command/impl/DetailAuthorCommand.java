package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.AuthorService;
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
 * Get detail author and list edition by id author( redirect to detail author page).
 * If error service - redirect to error page.+
 *
 * @see AuthorService
 * @see EditionService
 */

public class DetailAuthorCommand implements Command {
    private AuthorService authorService = ServiceFactory.getInstance().getAuthorService();
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long idAuthor = Integer.parseInt(request.getParameter("idAuthor"));
        try {
            Author author = authorService.getAuthorById(idAuthor);
            request.setAttribute("author", author);
            List<Edition> editions = editionService.getEditionListByIdAuthor(idAuthor);
            request.setAttribute("list", editions);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.DETAIL_AUTHOR);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
