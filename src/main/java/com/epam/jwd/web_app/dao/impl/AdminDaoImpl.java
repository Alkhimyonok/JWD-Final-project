package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.dao.AdminDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import com.epam.jwd.web_app.dao.util.UserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminDaoImpl implements AdminDao {

    private final static Logger logger = LogManager.getLogger(AdminDaoImpl.class);
    private final static ConnectionPool POOL = ConnectionPool.getInstance();
    private final static Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    private final String SELECT_BY_EMAIL_PASSWORD = "SELECT * FROM admin WHERE email=? AND password=?;";

    public AdminDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_BY_EMAIL_PASSWORD);
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

    /**
     * checked authorization admin by Login
     * @param login - object Login with email and password admin
     * @return boolean is Admin
     * @throws DaoException if error in JDBC
     */

    @Override
    public boolean isAdmin(Login login) throws DaoException {
        boolean answer;
        try {
            String password = UserUtil.passwordHash(login.getPassword());//хешируем пароль MD5
            PreparedStatement statement = preparedStatementMap.get(SELECT_BY_EMAIL_PASSWORD);
            statement.setString(1, login.getEmail());
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            answer = resultSet.next();
            logger.info("Admin login");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return answer;
    }
}
