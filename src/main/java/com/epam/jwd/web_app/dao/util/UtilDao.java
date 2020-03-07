package com.epam.jwd.web_app.dao.util;

import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UtilDao {
    private final static ConnectionPool POOL = ConnectionPool.getInstance();

    private static Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    private static final String SELECT_USERS = "SELECT * FROM users ORDER BY id DESC LIMIT 1;";
    private static final String SELECT_SUBSCRIBE = "SELECT * FROM subscribe ORDER BY id DESC LIMIT 1;";
    private static final String SELECT_EDITIONS = "SELECT * FROM editions ORDER BY id DESC LIMIT 1;";

    static {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_USERS);
            setPreparedStatement(connection, SELECT_SUBSCRIBE);
            setPreparedStatement(connection, SELECT_EDITIONS);
        } catch (ConnectionPoolException | SQLException | DaoException e) {
            //  logger.error(e);
        }
    }

    private static void setPreparedStatement(Connection connection, String sql) throws DaoException {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                // logger.error(e);
                throw new DaoException(e);

            }
        }
    }

    public static long generateIdByTable(String table) throws DaoException {
        String PSQL_SELECT = "";
        if (table.equals("users")) {
            PSQL_SELECT = SELECT_USERS;
        }
        if (table.equals("subscribe")) {
            PSQL_SELECT = SELECT_SUBSCRIBE;
        }
        if (table.equals("editions")) {
            PSQL_SELECT = SELECT_EDITIONS;
        }
        long id = 0;
        try {
            PreparedStatement statement = preparedStatementMap.get(PSQL_SELECT);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            ////
            throw new DaoException();
        }
        return id + 1;
    }
}
