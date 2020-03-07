package com.epam.jwd.web_app.controller.filter;

import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.controller.util.PageName;
import com.epam.jwd.web_app.service.ServiceFactory;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "BlockFilter")
public class BlockFilter implements Filter {
    private SubscribeService subscribeService = ServiceFactory.getInstance().getSubscribeService();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        String command = request.getParameter("command");
        if (user != null && !(user instanceof String)) {
            try {
                User us = (User) user;
                if (us.getStatus().equals("block") && !"pay".equals(command)) {
                    List<Subscribe> subscribeList = subscribeService.getListInCreditByIdUser(us.getId());
                    req.setAttribute("blockList", subscribeList);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(PageName.BLOCK);
                    rd.forward(request, response);
                    return;
                }
            } catch (ServiceException e) {
                response.sendRedirect(PageName.ERROR);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
