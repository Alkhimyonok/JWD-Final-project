package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Implements {@link Command} interface.
 * User delete subscribe from cart( redirect to cart page).
 * If error service - redirect to error page.+
 *
 * @see SubscribeService
 */

public class DeleteSubscribeCommand implements Command {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        long id = Integer.parseInt(request.getParameter("idSubscribe"));
        List<Subscribe> cart = (List<Subscribe>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        try {
            Subscribe subscribe = subscribeService.getDeleteByIdSubscribe(id);
            cart.remove(subscribe);
            session.setAttribute("cart", cart);
            response.sendRedirect(PageName.CART);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
