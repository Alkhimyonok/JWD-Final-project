package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.*;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link Command} interface.
 * User pay subscribe cart or credit or block( redirect to news page).
 * If error service - redirect to error page.+
 *
 * @see BankBillService
 * @see SubscribeService
 * @see UserService
 */

public class PayCommand implements Command {
    private BankBillService bankBillService = ServiceFactory.getInstance().getBankBillService();
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        String paramBlock = request.getParameter("param");
        long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
        long code = Long.parseLong(request.getParameter("code"));
        long total = Long.parseLong(request.getParameter("total"));
        String id = request.getParameter("id");
        try {
            bankBillService.pay(accountNumber, code, total);
            PostService postService = ServiceFactory.getInstance().getPostService();
            HttpSession session = request.getSession();
            if (paramBlock.equals("")) {
                if (!id.equals("")) {//user pay credit
                    long idSubscribe = Long.parseLong(id);
                    subscribeService.payCredit(idSubscribe);
                } else {//user pay cart
                    subscribeService.subscribe(user.getId());
                    session.setAttribute("cart", new ArrayList<>());
                    List<Post> news = postService.getNews(user.getId());
                    session.setAttribute("news", news);
                }
            }
            if (paramBlock.equals("block")) {//user block and pay credit
                List<Post> news = postService.getNews(user.getId());
                session.setAttribute("news", news);
                subscribeService.deleteSubscribeCreditByIdUser(user.getId());
                session.setAttribute("news", news);
                User goodUser = userService.getUserById(user.getId());
                session.setAttribute("user", goodUser);
            }
            response.sendRedirect(PageName.NEWS);
        } catch (ServiceException e) {
            request.setAttribute("err", e.getMessage());
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.CART);
            rd.forward(request, response);
        }
    }
}
