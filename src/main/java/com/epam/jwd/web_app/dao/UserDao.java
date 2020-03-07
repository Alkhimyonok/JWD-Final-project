package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.exception.DaoException;

public interface UserDao {
    boolean isLoginUser(Login login) throws DaoException;

    void registerUser(User user, Login login) throws DaoException;

    User getUserById(long idUser) throws DaoException;

    void updateStatusByIdUser(long idUser, String status) throws DaoException;

    void updateUser(long idUser, User newUser) throws DaoException;

    User getUserByEmail(String email) throws DaoException;

    void resetPassword(long idUser, String newPassword) throws DaoException;

    User gerUserByParam(String firstName, String lastName, String email) throws DaoException;

    void deleteUserById(long idUser) throws DaoException;
}
