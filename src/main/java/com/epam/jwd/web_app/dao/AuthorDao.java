package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.dao.exception.DaoException;

public interface AuthorDao {
    Author getAuthorById(long id) throws DaoException;

    Author getAuthorLikeName(String name_) throws DaoException;

    Author getAuthorByName(String authorName) throws DaoException;
}
