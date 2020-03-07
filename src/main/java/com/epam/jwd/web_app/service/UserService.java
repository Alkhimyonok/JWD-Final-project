package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.exception.ServiceException;

public interface UserService {

    boolean isLoginUser(Login login) throws ServiceException;

    void registerUser(User user, Login login) throws ServiceException;

    User getUserById(long id) throws ServiceException;

    void blockUserById(long id) throws ServiceException;

    void updateUser(User newUser) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    long generateId() throws ServiceException;

    void resetPassword(long idUser, String newPassword) throws ServiceException;

    User getUserByParam(String firstName, String lastName, String email) throws ServiceException;

    void deleteUser(User user) throws ServiceException;

}
