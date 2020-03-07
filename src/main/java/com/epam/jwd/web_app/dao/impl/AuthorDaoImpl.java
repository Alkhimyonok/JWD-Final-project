package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.dao.AuthorDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class AuthorDaoImpl implements AuthorDao {
    private static final Logger logger = LogManager.getLogger(AuthorDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    private static final String SELECT_BY_ID = "SELECT * FROM authors where id=?;";
    private static final String SELECT_BY_NAME = "SELECT * FROM authors where name=?;";

    private static final int ID_COLUMN = 1;
    private static final int NAME_COLUMN = 2;
    private static final int DESCRIPTION_COLUMN = 3;


    public AuthorDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_BY_ID);
            setPreparedStatement(connection, SELECT_BY_NAME);
        } catch (ConnectionPoolException | SQLException | DaoException e) {
            logger.error(e.getMessage());
        }
    }

    private void setPreparedStatement(Connection connection, String sql) throws DaoException {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException();
            }
        }
    }

    private Author createAuthor(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        return new Author(id, name, description);
    }

    /**
     * @param name_ - name author
     * @return long id Author
     * @throws DaoException if error in JDBC
     */

    @Override
    public Author getAuthorLikeName(String name_) throws DaoException {
        name_ = name_.toUpperCase();
        String SELECT_BY_NAME = "SELECT * FROM authors WHERE UPPER(name) LIKE '%" + name_ + "%';";
        try (Statement statement = POOL.takeConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_BY_NAME)) {
            if (resultSet.next()) {
                return createAuthor(resultSet);
            } else {
                logger.error("No such author");
                return new Author();
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public Author getAuthorById(long idAuthor) throws DaoException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_ID);
            preparedStatement.setLong(1, idAuthor);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createAuthor(resultSet);
            } else {
                logger.error("No such author");
                throw new DaoException("locale.err.admin.author");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    @Override
    public Author getAuthorByName(String authorName) throws DaoException {
        Author author;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_NAME);
            preparedStatement.setString(1, authorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = createAuthor(resultSet);
            } else {
                logger.error("No such author");
                throw new DaoException("locale.err.admin.author");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return author;
    }
}
