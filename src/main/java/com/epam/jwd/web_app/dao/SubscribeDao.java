package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.exception.DaoException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SubscribeDao {
    List<Subscribe> getListForUserByStatus(long idUser, String status) throws DaoException;

    void addSubscribe(Subscribe subscribe) throws DaoException;

    boolean isSubscribeUserByIdEdition(long idUser, long idEdition) throws DaoException;

    Subscribe getDeleteByIdSubscribe(long idSubscribe) throws DaoException;

    void subscribeByStartStatus(long idUser, String status) throws DaoException;

    void unsubscribe(long idSubscribe) throws DaoException;

    Subscribe getSubscribeById(long id) throws DaoException;

    void payCredit(long idSubscribe) throws DaoException;

    List<Subscribe> getListInCredit() throws DaoException;

    void deleteSubscribeForUserByStatus(long idUser, String status) throws DaoException;

    void subscribeCreditByIdUser(long idUser) throws DaoException;
}
