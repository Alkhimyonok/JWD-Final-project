package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.*;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Implements {@link Command} interface.
 * Login user, create subscriptions, cart and news for user( redirect to index page).
 * If error service - redirect to error page.+
 *
 * @see UserService
 * @see SubscribeService
 * @see PostService
 */

public class LoginCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();
    private PostService postService = ServiceFactory.getInstance().getPostService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String param = request.getParameter("param");
        if (param != null) {
            session.setAttribute("user", "admin");
            response.sendRedirect(PageName.ADMIN_MAIN);
            return;
        } else {
            try {
                User user = userService.getUserByEmail(email);
                session.setAttribute("user", user);
                List<Subscribe> cart = subscribeService.getListCart(user.getId());
                session.setAttribute("cart", cart);
                List<Post> news = postService.getNews(user.getId());
                session.setAttribute("news", news);
                response.sendRedirect(PageName.INDEX);
                return;
            } catch (ServiceException e) {
                response.sendRedirect(PageName.ERROR);
                return;
            }
        }
    }
}
