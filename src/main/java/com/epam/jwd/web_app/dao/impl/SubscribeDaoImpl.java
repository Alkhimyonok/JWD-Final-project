package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.SubscribeDao;
import com.epam.jwd.web_app.dao.UserDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SubscribeDaoImpl implements SubscribeDao {
    private static final Logger logger = LogManager.getLogger(SubscribeDaoImpl.class);
    private UserDao userDao = DaoFactory.getInstance().getUserDao();
    private EditionDao editionDao = DaoFactory.getInstance().getEditionDao();

    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private final static String SELECT_BY_USER_STATUS = "SELECT * FROM subscribe WHERE idUser=? AND status=?::statusSubscribe;";
    private final static String INSERT = "INSERT INTO subscribe(id, idUser, idEdition, date, status) VALUES (?,?,?,?,?::statusSubscribe);";
    private final static String SELECT_BY_USER_EDITION = "SELECT * FROM subscribe WHERE idUser=? AND idEdition=?;";
    private final static String DELETE_BY_ID = "DELETE FROM subscribe WHERE id=?;";
    private final static String SELECT_BY_ID = "SELECT * FROM subscribe WHERE id=?;";
    private final static String UPDATE_STATUS_BY_USER_STATUS = "UPDATE subscribe  set status=?::statusSubscribe WHERE idUser=? AND status=?::statusSubscribe;";
    private final static String UPDATE_STATUS = "UPDATE subscribe  set status=?::statusSubscribe WHERE id=?;";
    private final static String SELECT_BY_STATUS = "SELECT * FROM subscribe WHERE status=?::statusSubscribe;";
    private final static String DELETE_BY_USER_STATUS = "DELETE FROM subscribe WHERE idUser=? AND status=?::statusSubscribe;";

    private final static int ID_COLUMN = 1;
    private final static int ID_USER_COLUMN = 2;
    private final static int ID_EDITION_COLUMN = 3;
    private final static int DATE_COLUMN = 4;
    private final static int STATUS_COLUMN = 5;

    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SubscribeDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_BY_USER_STATUS);
            setPreparedStatement(connection, INSERT);
            setPreparedStatement(connection, SELECT_BY_USER_EDITION);
            setPreparedStatement(connection, DELETE_BY_ID);
            setPreparedStatement(connection, SELECT_BY_ID);
            setPreparedStatement(connection, UPDATE_STATUS_BY_USER_STATUS);
            setPreparedStatement(connection, UPDATE_STATUS);
            setPreparedStatement(connection, SELECT_BY_STATUS);
            setPreparedStatement(connection, DELETE_BY_USER_STATUS);
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

    private Subscribe createSubscribe(ResultSet resultSet) throws SQLException, DaoException {
        long id = resultSet.getLong(ID_COLUMN);
        long idUser = resultSet.getLong(ID_USER_COLUMN);
        User user = userDao.getUserById(idUser);
        long idEdition = resultSet.getLong(ID_EDITION_COLUMN);
        Edition edition = editionDao.getEditionById(idEdition);
        String status = resultSet.getString(STATUS_COLUMN);
        Date date = resultSet.getDate(DATE_COLUMN);
        return new Subscribe(id, user, edition, status, date);
    }

    /**
     * @param idUser long
     * @param status String statusSubscribe(in process, done, credit)
     * @return List Subscribe
     * @throws DaoException if error JDBC
     */
    @Override
    public List<Subscribe> getListForUserByStatus(long idUser, String status) throws DaoException {
        List<Subscribe> subscribeList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_USER_STATUS);
            preparedStatement.setLong(1, idUser);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Subscribe subscribe = createSubscribe(resultSet);
                subscribeList.add(subscribe);
            }
        } catch (SQLException | DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return subscribeList;
    }

    /**
     * @param subscribe Subscribe
     * @throws DaoException if error JDBC
     */
    @Override
    public void addSubscribe(Subscribe subscribe) throws DaoException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(INSERT);
            preparedStatement.setLong(1, subscribe.getId());
            preparedStatement.setLong(2, subscribe.getUser().getId());
            preparedStatement.setLong(3, subscribe.getEdition().getId());
            preparedStatement.setString(5, subscribe.getStatus());
            preparedStatement.setDate(4, new java.sql.Date(subscribe.getDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser    long
     * @param idEdition long
     * @return boolean "is the user subscribe to edition?"
     * @throws DaoException if error JDBC
     * @see com.epam.jwd.web_app.controller.command.impl.AddToCartCommand
     */
    @Override
    public boolean isSubscribeUserByIdEdition(long idUser, long idEdition) throws DaoException {
        boolean answer;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_USER_EDITION);
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idEdition);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                answer = true;
            } else {
                logger.error("No such subscribe");
                answer = false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return answer;
    }

    /**
     * @param idSubscribe long
     * @return Subscribe
     * @throws DaoException if no such subscribe or error JDBC
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteSubscribeCommand
     */
    @Override
    public Subscribe getDeleteByIdSubscribe(long idSubscribe) throws DaoException {
        try {
            PreparedStatement statementSelect = preparedStatementMap.get(SELECT_BY_ID);
            PreparedStatement statementDelete = preparedStatementMap.get(DELETE_BY_ID);
            statementSelect.setLong(1, idSubscribe);
            statementDelete.setLong(1, idSubscribe);
            ResultSet resultSelect = statementSelect.executeQuery();
            if (resultSelect.next()) {
                Subscribe subscribe = createSubscribe(resultSelect);
                statementDelete.executeUpdate();
                return subscribe;
            } else {
                logger.error("No such subscribe");
                throw new DaoException();
            }
        } catch (SQLException | DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * @param idUser long
     * @param status String statusSubscribe(in process, done, credit)
     * @throws DaoException if error JDBC
     */
    @Override
    public void subscribeByStartStatus(long idUser, String status) throws DaoException {
        try {
            PreparedStatement statementUpdate = preparedStatementMap.get(UPDATE_STATUS_BY_USER_STATUS);
            PreparedStatement statementSelect = preparedStatementMap.get(SELECT_BY_USER_STATUS);
            statementSelect.setLong(1, idUser);
            statementSelect.setString(2, status);
            statementUpdate.setString(1, "done");
            statementUpdate.setLong(2, idUser);
            statementUpdate.setString(3, status);
            ResultSet resultSelect = statementSelect.executeQuery();
            statementUpdate.executeUpdate();
            while (resultSelect.next()) {
                long idEdition = resultSelect.getLong("idEdition");
                Edition edition = editionDao.getEditionById(idEdition);
                long countSubscribers = edition.getCountSubscribers();
                editionDao.updateSubscribers(idEdition, countSubscribers + 1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idSubscribe long
     * @throws DaoException if no such subscribe or error JDBC
     */
    @Override
    public void unsubscribe(long idSubscribe) throws DaoException {
        try {
            PreparedStatement statementSelect = preparedStatementMap.get(SELECT_BY_ID);
            PreparedStatement statementDelete = preparedStatementMap.get(DELETE_BY_ID);
            statementSelect.setLong(1, idSubscribe);
            ResultSet resultSet = statementSelect.executeQuery();
            long idEdition;
            if (resultSet.next()) {
                idEdition = resultSet.getLong("idEdition");
            } else {
                throw new DaoException();
            }
            Edition edition = editionDao.getEditionById(idEdition);
            long countSubscribers = edition.getCountSubscribers();
            editionDao.updateSubscribers(idEdition, countSubscribers - 1);
            statementDelete.setLong(1, idSubscribe);
            statementDelete.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param id long
     * @return Subscribe
     * @throws DaoException if no such subscribe or error JDBC
     */
    @Override
    public Subscribe getSubscribeById(long id) throws DaoException {
        Subscribe subscribe;
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                subscribe = createSubscribe(resultSet);
            } else {
                logger.error("No such subscribe");
                throw new DaoException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return subscribe;
    }

    /**
     * @param idSubscribe long
     * @throws DaoException if error JDBC
     */
    @Override
    public void payCredit(long idSubscribe) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPDATE_STATUS);
            statement.setString(1, "done");
            statement.setLong(2, idSubscribe);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @return List Subscribe
     * @throws DaoException if error JDBC
     */
    @Override
    public List<Subscribe> getListInCredit() throws DaoException {
        List<Subscribe> subscribeList = new ArrayList<>();
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_BY_STATUS);
            statement.setString(1, "credit");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subscribe subscribe = createSubscribe(resultSet);
                subscribeList.add(subscribe);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return subscribeList;
    }

    /**
     * @param idUser long
     * @param status String statusSubscribe(in process, done, credit)
     * @throws DaoException if error Jdbc
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteUserCommand
     */
    @Override
    public void deleteSubscribeForUserByStatus(long idUser, String status) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(DELETE_BY_USER_STATUS);
            statement.setLong(1, idUser);
            statement.setString(2, status);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser long
     * @throws DaoException if error Jdbc
     */
    @Override
    public void subscribeCreditByIdUser(long idUser) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPDATE_STATUS_BY_USER_STATUS);
            statement.setString(1, "credit");
            statement.setLong(2, idUser);
            statement.setString(3, "in process");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }
}
