package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdminDaoTest {
    private static final AdminDao adminDao = DaoFactory.getInstance().getAdminDao();
    private static final Logger logger = LogManager.getLogger(UserDaoTest.class);

    private static Login testingLogin;

    @BeforeClass
    public static void setAdmin() {
        String testingEmail = "admin@gmail.ru";
        String testingPassword = "yaAdmin";
        testingLogin = new Login(testingEmail, testingPassword);
    }

    @Test
    public void isAdmin() {
        boolean isAdmin = false;
        try {
            isAdmin = adminDao.isAdmin(testingLogin);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        Assert.assertTrue(isAdmin);
    }
}
