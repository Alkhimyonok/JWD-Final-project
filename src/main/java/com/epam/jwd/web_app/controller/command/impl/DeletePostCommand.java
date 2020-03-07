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
 * Admin deletes edition and send message( redirect to admin main page).
 * If error service - redirect to error page.+
 *
 * @see PostService
 */

public class DeletePostCommand implements Command {
    private PostService postService = ServiceFactory.getInstance().getPostService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String post = request.getParameter("post");
        try {
            postService.deletePostByPost(post);
            request.setAttribute("err", "locale.admin.deleteDone");/////////////////////////////////////
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.ADMIN_MAIN);
            rd.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
