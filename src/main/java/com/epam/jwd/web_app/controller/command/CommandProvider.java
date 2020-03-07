package com.epam.jwd.web_app.controller.command;

import com.epam.jwd.web_app.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTER, new RegisterCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.ADD_EDITION, new AddToCartCommand());
        commands.put(CommandName.DETAIL_EDITION, new DetailEditionCommand());
        commands.put(CommandName.LIST_EDITIONS, new ListEditionsCommand());
        commands.put(CommandName.SEARCH, new SearchCommand());
        commands.put(CommandName.PAY, new PayCommand());
        commands.put(CommandName.LIST_SUBSCRIPTIONS, new ListSubscriptionsCommand());
        commands.put(CommandName.DELETE_SUBSCRIBE, new DeleteSubscribeCommand());
        commands.put(CommandName.UNSUBSCRIBE, new UnsubscribeCommand());
        commands.put(CommandName.CREATE_POST, new CreatePostCommand());
        commands.put(CommandName.DETAIL_AUTHOR, new DetailAuthorCommand());
        commands.put(CommandName.DELETE_EDITION, new DeleteEditionCommand());
        commands.put(CommandName.POSTS_EDITION, new PostsEditionCommand());
        commands.put(CommandName.DELETE_POST, new DeletePostCommand());
        commands.put(CommandName.CREATE_EDITION, new CreateEditionCommand());
        commands.put(CommandName.CREDIT, new CreditCommand());
        commands.put(CommandName.LIST_SUBSCRIBE_CREDIT, new ListSubscribeCreditCommand());
        commands.put(CommandName.BLOCK_USER, new BlockUserCommand());
        commands.put(CommandName.INDEX, new IndexCommand());
        commands.put(CommandName.UPDATE_USER, new UpdateUserCommand());
        commands.put(CommandName.RESET_PASSWORD, new ResetPasswordCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.NO_SUCH, new NoSuchCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        Command command = commands.get(name);
        return command;
    }
}
