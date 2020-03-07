package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.UserService;
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
 * User Register+
 *
 * @see UserService
 */

public class RegisterCommand implements Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("newFirstName");
        String lastName = request.getParameter("newLastName");
        String email = request.getParameter("newEmail");
        String password = request.getParameter("newPassword");
        if (firstName.equals("")&&lastName.equals("")&&email.equals("")&&password.equals("")){
            request.setAttribute("err", "locale.err.noFull");
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.REGISTER);
            rd.forward(request, response);
        }
        String status = "good";
        try {
            long id = userService.generateId();
            Login login = new Login(email, password);
            User user = new User(id, firstName, lastName, email, status);
            userService.registerUser(user, login);
            List<Post> news = new ArrayList<>();
            List<Subscribe> cart = new ArrayList<>();
            HttpSession session = request.getSession();
            session.setAttribute("news", news);
            session.setAttribute("cart", cart);
            session.setAttribute("user", user);
            response.sendRedirect(PageName.INDEX);
        } catch (ServiceException e) {
            response.sendRedirect(PageName.ERROR);
        }
    }
}
