package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface EditionDao {
    List<Edition> getListByParameters(String title_, String author_) throws DaoException;

    List<Edition> getAllList() throws DaoException;

    Edition getEditionById(long idEdition) throws DaoException;

    List<Edition> getTopList() throws DaoException;

    List<Edition> getListByType(String type_) throws DaoException;

    List<Edition> getListByIdAuthor(long idAuthor_) throws DaoException;

    void updateSubscribers(long idEdition, long countSubscribers) throws DaoException;

    void deleteEditionById(long idEdition) throws DaoException;

    boolean isBlockedEdition(long idEdition) throws DaoException;

    Edition getEditionByTypeTitle(String type, String title) throws DaoException;

    void createEdition(Edition edition) throws DaoException;
}
