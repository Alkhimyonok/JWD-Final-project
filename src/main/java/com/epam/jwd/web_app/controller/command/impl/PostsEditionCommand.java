package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.PostService;
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
 * Get list posts by id edition for admin+
 *
 * @see PostService
 */

public class PostsEditionCommand implements Command {
    private PostService postService = ServiceFactory.getInstance().getPostService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long idEdition = Long.parseLong(request.getParameter("idEdition"));
        try {
            List<Post> posts = postService.getPostsByIdEdition(idEdition);
            request.setAttribute("posts", posts);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.POSTS);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
