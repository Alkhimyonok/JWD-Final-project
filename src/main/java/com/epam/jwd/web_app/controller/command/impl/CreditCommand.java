package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.PostService;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;
import com.epam.jwd.web_app.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link Command} interface.
 * Subscribe in credit( redirect to news page).
 * If error service - redirect to error page.+
 *
 * @see SubscribeService
 */

public class CreditCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            subscribeService.subscribeCreditByUser(user);
            HttpSession session = request.getSession();
            session.setAttribute("cart", new ArrayList<>());
            PostService postService = new PostServiceImpl();
            List<Post> news = postService.getNews(user.getId());
            session.setAttribute("news", news);
            response.sendRedirect(PageName.NEWS);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
