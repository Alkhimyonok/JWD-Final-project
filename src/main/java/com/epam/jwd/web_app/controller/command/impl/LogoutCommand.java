package com.epam.jwd.web_app.controller.command.impl;

import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.util.PageName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Implements {@link Command} interface.
 * Logout user or admin( redirect to index page).+
 */

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String param = request.getParameter("param");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        if (param == null) {
            session.removeAttribute("news");
            session.removeAttribute("cart");
        }
        response.sendRedirect(PageName.INDEX);
    }
}
