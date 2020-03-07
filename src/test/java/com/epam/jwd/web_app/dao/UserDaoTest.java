package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.util.UtilDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {

    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static final Logger logger = LogManager.getLogger(UserDaoTest.class);

    private static User testingUser;
    private static Login testingLogin;

    @BeforeClass
    public static void registerUser() {
        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "Email";
        String password = "Password";
        String status = "good";
        try {
           // long id = UtilDao.generateIdByTable("users");
            long id = -1;
            testingUser = new User(id, firstName, lastName, email, status);
            testingLogin = new Login(email, password);
            userDao.registerUser(testingUser, testingLogin);
        } catch (DaoException e) {
            logger.error(e);
        }
    }

    @Before
    public void setupUser() {
        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "Email";
        String password = "Password";
        String status = "good";
        testingUser.setFirstName(firstName);
        testingUser.setLastName(lastName);
        testingUser.setEmail(email);
        testingUser.setStatus(status);
        testingLogin.setEmail(email);
        testingLogin.setPassword(password);
    }

    @Test
    public void isLoginUser() {
        boolean isLogin = false;
        try {
            isLogin = userDao.isLoginUser(testingLogin);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        Assert.assertTrue(isLogin);
    }

    @Test
    public void getUser() {
        boolean isUser;
        User findUser = new User();
        try {
            findUser = userDao.getUserById(testingUser.getId());
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        isUser = findUser.equals(testingUser);
        Assert.assertTrue(isUser);
    }

    @Test
    public void updateStatusByIdUser() {
        boolean isUpdated;
        String status = "block";
        User user = new User(testingUser.getId(), testingUser.getFirstName(), testingUser.getLastName(), testingUser.getEmail(), "block");
        try {
            userDao.updateStatusByIdUser(testingUser.getId(), status);
            testingUser.setStatus("block");
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        isUpdated = user.equals(testingUser);
        try {
            userDao.updateStatusByIdUser(testingUser.getId(), "good");
        } catch (DaoException e) {
            logger.error(e);
        }
        Assert.assertTrue(isUpdated);
    }

    @Test
    public void updateUser() {
        boolean isUpdated;
        User user = new User(testingUser.getId(), "newFirstName", "newLastName", "newEmail", testingUser.getStatus());
        try {
            userDao.updateUser(testingUser.getId(), user);
            testingUser.setFirstName("newFirstName");
            testingUser.setLastName("newLastName");
            testingUser.setEmail("newEmail");
            testingLogin.setEmail("newEmail");
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        isUpdated = user.equals(testingUser);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("Email");
        try {
            userDao.updateUser(testingUser.getId(), user);
        } catch (DaoException e) {
            logger.error(e);
        }
        Assert.assertTrue(isUpdated);
    }

    @Test
    public void getUserByEmail() {
        boolean isUser;
        User findUser = new User();
        try {
            findUser = userDao.getUserByEmail(testingUser.getEmail());
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        isUser = findUser.equals(testingUser);
        Assert.assertTrue(isUser);
    }

    @Test
    public void resetPassword() {
        boolean isReset = false;
        String newPassword = "Hoba";
        testingLogin.setPassword(newPassword);
        try {
            userDao.resetPassword(testingUser.getId(), newPassword);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        try {
            isReset = userDao.isLoginUser(testingLogin);
            userDao.resetPassword(testingUser.getId(), "Password");
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        Assert.assertTrue(isReset);
    }

    @Test
    public void getUserByParam() {
        boolean isUser;
        User findUser = new User();
        try {
            findUser = userDao.gerUserByParam(testingUser.getFirstName(), testingUser.getLastName(), testingUser.getEmail());
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        isUser = findUser.equals(testingUser);
        Assert.assertTrue(isUser);
    }
}
