package com.epam.jwd.web_app.controller.command.impl;

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

/**
 * Implements {@link Command} interface.
 * Admin creates post by id Edition and send message.
 * Send message if no full info.
 * If error service - redirect to error page.+
 *
 * @see PostService
 */

public class CreatePostCommand implements Command {
    private PostService postService = ServiceFactory.getInstance().getPostService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        String pathImg = request.getParameter("pathImg");
        String post = request.getParameter("post");
        try {
            postService.createPost(type, title, pathImg, post);
            request.setAttribute("err", "locale.admin.deleteDone");//////////////////////////
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.CREATE_POST);
            rd.forward(request, response);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.CREATE_POST);
                rd.forward(request, response);
            } else response.sendRedirect(PageName.ERROR);
        }
    }
}
