package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.EditionService;
import com.epam.jwd.web_app.service.PostService;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Implements {@link Command} interface.
 *
 * Create novelty posts and top editions( redirect to index page).
 * If error service - redirect to error page.+
 *
 * @see EditionService
 * @see PostService
 */

public class IndexCommand implements Command {
    private PostService postService = ServiceFactory.getInstance().getPostService();
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            List<Post> novelty = postService.getNovelty();
            List<Edition> top = editionService.getTopList();
            session.setAttribute("novelty", novelty);
            session.setAttribute("top", top);
            response.sendRedirect(PageName.INDEX);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
