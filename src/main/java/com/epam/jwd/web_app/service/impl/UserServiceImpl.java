package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.SubscribeDao;
import com.epam.jwd.web_app.dao.UserDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.util.UtilDao;
import com.epam.jwd.web_app.service.UserService;
import com.epam.jwd.web_app.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
    private UserDao userDao = DaoFactory.getInstance().getUserDao();
    private SubscribeDao subscribeDao = DaoFactory.getInstance().getSubscribeDao();

    @Override
    public boolean isLoginUser(Login login) throws ServiceException {
        try {
            return userDao.isLoginUser(login);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void registerUser(User user, Login login) throws ServiceException {
        try {
            userDao.registerUser(user, login);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     *
     * @param idUser long
     * @return User
     * @throws ServiceException if error Dao
     */
    @Override
    public User getUserById(long idUser) throws ServiceException {
        try {
            return userDao.getUserById(idUser);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     *
     * @param idUser long
     * @throws ServiceException if error Dao or user is already blocked
     */
    @Override
    public void blockUserById(long idUser) throws ServiceException {
        try {
            User user = userDao.getUserById(idUser);
            if(user.getStatus().equals("block")){
                throw new DaoException("locale.");////////////////////////////////////////////////////
            }
            userDao.updateStatusByIdUser(idUser, "block");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void updateUser(User newUser) throws ServiceException {
        try {
            userDao.updateUser(newUser.getId(), newUser);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return userDao.getUserByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public long generateId() throws ServiceException {
        try {
            return UtilDao.generateIdByTable("users");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void resetPassword(long idUser, String newPassword) throws ServiceException {
        try {
            userDao.resetPassword(idUser, newPassword);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public User getUserByParam(String firstName, String lastName, String email) throws ServiceException {
        if (firstName != null && lastName != null && email != null) {
            try {
                return userDao.gerUserByParam(firstName, lastName, email);
            } catch (DaoException e) {
                throw new ServiceException();
            }
        } else return null;
    }

    /**
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteUserCommand
     * @param user User
     * @throws ServiceException if error Dao
     */
    @Override
    public void deleteUser(User user) throws ServiceException {
        try {
            userDao.deleteUserById(user.getId());
            subscribeDao.deleteSubscribeForUserByStatus(user.getId(), "in process");
            subscribeDao.deleteSubscribeForUserByStatus(user.getId(), "done");
            subscribeDao.deleteSubscribeForUserByStatus(user.getId(), "credit");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
