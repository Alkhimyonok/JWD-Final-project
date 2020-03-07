package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.dao.AuthorDao;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class EditionDaoImpl implements EditionDao {
    private static final Logger logger = LogManager.getLogger(EditionDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private static final String SELECT = "SELECT * FROM editions;";
    private static final String SELECT_BY_ID = "SELECT * FROM editions WHERE id=?;";
    private static final String SELECT_ORDER_SUBSCRIBERS_LIMIT = "SELECT * FROM editions ORDER BY countSubscribers DESC LIMIT 4;";
    private static final String SELECT_BY_TYPE = "SELECT * FROM editions where type=?::typeEdition;";
    private static final String UPDATE_SUBSCRIBERS =
            "UPDATE editions SET countSubscribers=? WHERE id=?;";
    private static final String SELECT_BY_AUTHOR = "SELECT * FROM editions WHERE idAuthor=?;";
    private static final String UPDATE_STATUS_BY_ID = "UPDATE editions set status=?::statusEdition WHERE id=?;";
    private static final String SELECT_STATUS_BY_ID = "SELECT status from editions WHERE id=?;";
    private static final String SELECT_BY_TYPE_TITLE = "SELECT * FROM editions WHERE type=?::typeEdition AND title=?;";
    private final static String INSERT = "INSERT INTO editions(id, type, idAuthor, title, description, pathImg, priceMonth) values (?, ?::typeEdition, ?, ?, ?, ?, ?);";
    private final static String DELETE = "DELETE editions where id=?;";

    private static final int ID_COLUMN = 1;
    private static final int TYPE_COLUMN = 2;
    private static final int IDAUTHOR_COLUMN = 3;
    private static final int TITLE_COLUMN = 4;
    private static final int DESCRIPTION_COLUMN = 5;
    private static final int PATHIMG_COLUMN = 6;
    private static final int PRICEMONTH_COLUMN = 7;
    private static final int COUNTSUBSCRIBERS_COLUMN = 8;
    private static final int STATUS_COLUMN = 9;


    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public EditionDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT);
            setPreparedStatement(connection, SELECT_BY_ID);
            setPreparedStatement(connection, SELECT_ORDER_SUBSCRIBERS_LIMIT);
            setPreparedStatement(connection, SELECT_BY_TYPE);
            setPreparedStatement(connection, UPDATE_SUBSCRIBERS);
            setPreparedStatement(connection, SELECT_BY_AUTHOR);
            setPreparedStatement(connection, UPDATE_STATUS_BY_ID);
            setPreparedStatement(connection, SELECT_STATUS_BY_ID);
            setPreparedStatement(connection, SELECT_BY_TYPE_TITLE);
            setPreparedStatement(connection, INSERT);
            setPreparedStatement(connection, DELETE);
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
                throw new DaoException(e);
            }
        }
    }

    private Edition createEdition(ResultSet resultSet) throws SQLException, DaoException {
        AuthorDao authorDao = DaoFactory.getInstance().getAuthorDao();
        long id = resultSet.getLong(ID_COLUMN);
        String type = resultSet.getString(TYPE_COLUMN);
        long idAuthor = resultSet.getLong(IDAUTHOR_COLUMN);
        Author author = authorDao.getAuthorById(idAuthor);
        String title = resultSet.getString(TITLE_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        long priceMonth = resultSet.getLong(PRICEMONTH_COLUMN);
        long countSubscribers = resultSet.getLong(COUNTSUBSCRIBERS_COLUMN);
        String pathImg = resultSet.getString(PATHIMG_COLUMN);
        String status = resultSet.getString(STATUS_COLUMN);
        return new Edition(id, type, author, title, description, pathImg, priceMonth, countSubscribers, status);
    }

    /**
     * @param title_  String title edition
     * @param author_ String name author
     * @return List Edition
     * @throws DaoException if no such edition or error JDBC
     */

    @Override
    public List<Edition> getListByParameters(String title_, String author_) throws DaoException {
        AuthorDao authorDao = DaoFactory.getInstance().getAuthorDao();
        String SELECT_EDITIONS_PSQL = "";
        List<Edition> searchList = new ArrayList<>();
        if (title_.equals("")) {
            long idAuthor_ = authorDao.getAuthorLikeName(author_).getId();
            if (idAuthor_ == 0) {
                logger.error("No such edition.");
                throw new DaoException("locale.err.search.noSuch");
            }
            SELECT_EDITIONS_PSQL = "SELECT * FROM editions WHERE idAuthor='" + idAuthor_ + "';";
        }
        if (author_.equals("")) {
            title_ = title_.toUpperCase();
            SELECT_EDITIONS_PSQL = "SELECT * FROM editions WHERE UPPER(title) LIKE '%" + title_ + "%';";
        }
        if (!title_.equals("") && !author_.equals("")) {
            long idAuthor_ = authorDao.getAuthorLikeName(author_).getId();
            if (idAuthor_ == 0) {
                logger.error("No such edition.");
                throw new DaoException("locale.err.search.noSuch");
            }
            title_ = title_.toUpperCase();
            SELECT_EDITIONS_PSQL = "SELECT * FROM editions WHERE title LIKE '%" + title_ + "%' AND idAuthor='" + idAuthor_ + "';";
        }
        try (Statement preparedStatement = POOL.takeConnection().createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(SELECT_EDITIONS_PSQL);) {
            while (resultSet.next()) {
                Edition edition = createEdition(resultSet);
                searchList.add(edition);
            }
            if (searchList.size() == 0) {
                logger.error("No such edition.");
                throw new DaoException("locale.err.search.noSuch");
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        Collections.reverse(searchList);
        return searchList;
    }

    /**
     * @return list edition
     * @throws DaoException if error JDBC
     */

    public List<Edition> getAllList() throws DaoException {
        List<Edition> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Edition edition = createEdition(resultSet);
                list.add(edition);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return list;
    }

    /**
     * @param idEdition
     * @return edition
     * @throws DaoException if no such id or error JDBC
     */
    @Override
    public Edition getEditionById(long idEdition) throws DaoException {
        Edition edition;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_ID);
            preparedStatement.setLong(1, idEdition);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                edition = createEdition(resultSet);
            } else {
                logger.error("No such edition.");
                throw new DaoException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return edition;
    }

    /**
     * @return List Edition size 4 sort by count subscribers.
     * @throws DaoException if error JDBC
     * @see com.epam.jwd.web_app.controller.command.impl.IndexCommand
     */
    @Override
    public List<Edition> getTopList() throws DaoException {
        List<Edition> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_ORDER_SUBSCRIBERS_LIMIT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Edition edition = createEdition(resultSet);
                list.add(edition);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return list;
    }

    /**
     * @param type_ string typeEdition(magazines, newspapers, comics)
     * @return List Edition
     * @throws DaoException if error Jdbc
     */
    @Override
    public List<Edition> getListByType(String type_) throws DaoException {
        List<Edition> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_TYPE);
            preparedStatement.setString(1, type_);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Edition edition = createEdition(resultSet);
                list.add(edition);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return list;
    }

    /**
     * @param idAuthor_
     * @return list edition
     * @throws DaoException if error JDBC
     * @see com.epam.jwd.web_app.controller.command.impl.DetailAuthorCommand
     */
    @Override
    public List<Edition> getListByIdAuthor(long idAuthor_) throws DaoException {
        List<Edition> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_AUTHOR);
            preparedStatement.setLong(1, idAuthor_);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Edition edition = createEdition(resultSet);
                list.add(edition);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return list;
    }

    /**
     * @param idEdition        long
     * @param countSubscribers long
     * @throws DaoException if error JDBC
     */
    @Override
    public void updateSubscribers(long idEdition, long countSubscribers) throws DaoException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(UPDATE_SUBSCRIBERS);
            preparedStatement.setLong(1, countSubscribers);
            preparedStatement.setLong(2, idEdition);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idEdition long
     * @throws DaoException if error Jdbc
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteEditionCommand
     */
    @Override
    public void deleteEditionById(long idEdition) throws DaoException {
        String status = "block";
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(UPDATE_STATUS_BY_ID);
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, idEdition);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idEdition
     * @return boolean is blocked edition
     * @throws DaoException if no such edition or error jdbc
     */
    public boolean isBlockedEdition(long idEdition) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_STATUS_BY_ID);
            statement.setLong(1, idEdition);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("status").equals("block")) {
                    return true;
                } else {
                    logger.error("Edition is blocked");
                    return false;
                }
            } else {
                logger.error("No such edition");
                throw new DaoException();
            }
        } catch (SQLException | DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param type  String type edition(magazines, newspapers, comics)
     * @param title String tittle edition
     * @return Edition
     * @throws DaoException no such edition or error JDBC
     */

    @Override
    public Edition getEditionByTypeTitle(String type, String title) throws DaoException {
        Edition edition;
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_BY_TYPE_TITLE);
            statement.setString(1, type);
            statement.setString(2, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                edition = createEdition(resultSet);
            } else {
                logger.error("No such edition");
                throw new DaoException("locale.err.admin.createPost");
            }
        } catch (SQLException | DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return edition;
    }

    /**
     * @param edition Edition
     * @throws DaoException if error Jdbc
     * @see com.epam.jwd.web_app.controller.command.impl.CreateEditionCommand
     */
    @Override
    public void createEdition(Edition edition) throws DaoException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(INSERT);
            preparedStatement.setLong(1, edition.getId());
            preparedStatement.setString(2, edition.getType());
            preparedStatement.setLong(3, edition.getAuthor().getId());
            preparedStatement.setString(4, edition.getTitle());
            preparedStatement.setString(5, edition.getDescription());
            preparedStatement.setString(6, edition.getPathImg());
            preparedStatement.setLong(7, edition.getPriceMonth());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }
}
