package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.dao.impl.*;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private DaoFactory() {
    }

    private final static UserDao userDao = new UserDaoImpl();
    private final static EditionDao editionDao = new EditionDaoImpl();
    private final static SubscribeDao subscribeDao = new SubscribeDaoImpl();
    private final static AdminDao adminDao = new AdminDaoImpl();
    private final static PostDao postDao = new PostDaoImpl();
    private final static AuthorDao authorDao = new AuthorDaoImpl();
    private final static BankBillDao bankBillDao = new BankBillDaoImpl();


    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public EditionDao getEditionDao() {
        return editionDao;
    }

    public SubscribeDao getSubscribeDao() {
        return subscribeDao;
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public PostDao getPostDao() {
        return postDao;
    }

    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    public BankBillDao getBankBillDao() {
        return bankBillDao;
    }
}
