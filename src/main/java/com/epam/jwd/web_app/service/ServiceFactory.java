package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.service.impl.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    private final static UserService userService = new UserServiceImpl();
    private final static EditionService editionService = new EditionServiceImpl();
    private final static SubscribeService subscribeService = new SubscribeServiceImpl();
    private final static AdminService adminService = new AdminServiceImpl();
    private final static PostService postService = new PostServiceImpl();
    private final static BankBillService bankBillService = new BankBillServiceImpl();
    private final static AuthorService authorService = new AuthorServiceImpl();


    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public EditionService getEditionService() {
        return editionService;
    }

    public SubscribeService getSubscribeService() {
        return subscribeService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public PostService getPostService() {
        return postService;
    }

    public BankBillService getBankBillService() {
        return bankBillService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }
}
