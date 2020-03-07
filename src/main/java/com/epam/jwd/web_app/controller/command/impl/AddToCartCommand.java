package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.EditionService;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
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
 * Add to cart user by id Edition(redirect to cart page).
 * If edition is blocked or user-guest or user is already subscribe - send the massage.
 * If error service - redirect to error page.+
 *
 * @see EditionService
 * @see SubscribeService
 */

public class AddToCartCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();
    private EditionService editionService = ServiceFactory.getInstance().getEditionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        long idEdition = Long.parseLong(request.getParameter("idEdition"));
        try {
            Edition edition = editionService.getEditionById(idEdition);
            session.setAttribute("edition", edition);//for err message
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("err", "locale.err.add");
            RequestDispatcher rd = request.getServletContext()
                    .getRequestDispatcher(PageName.DETAIL);
            rd.forward(request, response);
        }

        List<Subscribe> cart = (List<Subscribe>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        try {
            Subscribe subscribe = subscribeService.addToCart(user, idEdition);
            cart.add(subscribe);
            session.setAttribute("cart", cart);
            response.sendRedirect(PageName.CART);
        } catch (ServiceException e) {
            if (e.getMessage() != null) {
                request.setAttribute("err", e.getMessage());
                RequestDispatcher rd = request.getServletContext()
                        .getRequestDispatcher(PageName.DETAIL);
                rd.forward(request, response);
            } else response.sendRedirect(PageName.ERROR);
        }
    }
}
