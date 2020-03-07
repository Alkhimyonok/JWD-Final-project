package com.epam.jwd.web_app.controller;

import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final CommandProvider provider = CommandProvider.getInstance();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName;
        Command command;
        commandName = request.getParameter("command");
        if(commandName==null){
            commandName="no_such";
        }
        command = provider.getCommand(commandName);
        command.execute(request, response);
    }
}
