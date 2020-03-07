package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.UserDao;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import com.epam.jwd.web_app.dao.util.UserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private static final String SELECT_LOGIN_BY_EMAIL_PASSWORD = "SELECT * FROM login where email=? and password=?;";
    private static final String INSERT_USER = "INSERT INTO users (id, firstName, lastName, email) VALUES (?, ?, ?, ?);";
    private static final String INSERT_LOGIN = "INSERT INTO login (idUser, email, password) VALUES (?, ?, ?);";
    private static final String SELECT_USERS_BY_ID = "SELECT * FROM users WHERE id=?;";
    private static final String SELECT_USERS_BY_EMAIL = "SELECT * FROM users WHERE email=?;";
    private static final String UPDATE_USERS_STATUS_BY_ID = "UPDATE users SET status=?::userStatus WHERE id=?;";
    private static final String UPDATE_USER = "UPDATE users SET firstName=?, lastName=?, email=? WHERE id=?;";
    private static final String UPDATE_LOGIN = "UPDATE login SET email=? WHERE idUser=?;";
    private static final String UPDATE_PASSWORD = "UPDATE login SET password=? where idUser=?;";
    private static final String SELECT_USER_FIRSTNAME_LASTNAME_EMAIL = "SELECT * FROM users WHERE firstName=? AND lastName=? AND email=?;";
    private static final String DELETE_USER = "DELETE FROM users where id=?;";
    private static final String DELETE_LOGIN = "DELETE FROM login where idUser=?;";

    private static final int ID_COLUMN = 1;
    private static final int FIRST_NAME_COLUMN = 2;
    private static final int LAST_NAME_COLUMN = 3;
    private static final int EMAIL_COLUMN = 4;
    private static final int EMAIL_COLUMN_LOGIN = 2;
    private static final int PASSWORD_COLUMN_LOGIN = 3;
    private static final int STATUS_COLUMN = 5;

    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public UserDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_LOGIN_BY_EMAIL_PASSWORD);
            setPreparedStatement(connection, INSERT_USER);
            setPreparedStatement(connection, INSERT_LOGIN);
            setPreparedStatement(connection, SELECT_USERS_BY_ID);
            setPreparedStatement(connection, UPDATE_USERS_STATUS_BY_ID);
            setPreparedStatement(connection, UPDATE_USER);
            setPreparedStatement(connection, UPDATE_LOGIN);
            setPreparedStatement(connection, SELECT_USERS_BY_EMAIL);
            setPreparedStatement(connection, UPDATE_PASSWORD);
            setPreparedStatement(connection, SELECT_USER_FIRSTNAME_LASTNAME_EMAIL);
            setPreparedStatement(connection, DELETE_USER);
            setPreparedStatement(connection, DELETE_LOGIN);
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

    private User createUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String firstName = resultSet.getString(FIRST_NAME_COLUMN);
        String lastName = resultSet.getString(LAST_NAME_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        String status = resultSet.getString(STATUS_COLUMN);
        return new User(id, firstName, lastName, email, status);
    }

    /**
     * @param login Login
     * @return boolean isAuthorization
     * @throws DaoException if error Jdbc
     */
    @Override
    public boolean isLoginUser(Login login) throws DaoException {
        boolean answer;
        try {
            String password = UserUtil.passwordHash(login.getPassword());
            PreparedStatement statement = preparedStatementMap.get(SELECT_LOGIN_BY_EMAIL_PASSWORD);
            statement.setString(1, login.getEmail());
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            answer = resultSet.next();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return answer;
    }

    /**
     * @param user  User
     * @param login Login
     * @throws DaoException if error Jdbc
     */
    public void registerUser(User user, Login login) throws DaoException {
        try {
            String password = UserUtil.passwordHash(login.getPassword());
            PreparedStatement statementRegister = preparedStatementMap.get(INSERT_USER);
            PreparedStatement statementLogin = preparedStatementMap.get(INSERT_LOGIN);
            statementRegister.setLong(1, user.getId());
            statementRegister.setString(2, user.getFirstName());
            statementRegister.setString(3, user.getLastName());
            statementRegister.setString(4, user.getEmail());
            statementLogin.setLong(1, user.getId());
            statementLogin.setString(2, login.getEmail());
            statementLogin.setString(3, password);
            statementLogin.executeUpdate();
            statementRegister.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser long
     * @return User
     * @throws DaoException if no such user or error Sql
     */
    @Override
    public User getUserById(long idUser) throws DaoException {
        User user;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_USERS_BY_ID);
            preparedStatement.setLong(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            } else {
                logger.error("No such user");
                throw new DaoException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return user;
    }

    /**
     * @param idUser long
     * @param status String statusUser(good, block)
     * @throws DaoException if error Sql
     */
    @Override
    public void updateStatusByIdUser(long idUser, String status) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPDATE_USERS_STATUS_BY_ID);
            statement.setString(1, status);
            statement.setLong(2, idUser);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser  long
     * @param newUser User
     * @throws DaoException if error SQL
     */
    @Override
    public void updateUser(long idUser, User newUser) throws DaoException {
        try {
            PreparedStatement statementLogin = preparedStatementMap.get(UPDATE_LOGIN);
            statementLogin.setString(1, newUser.getEmail());
            statementLogin.setLong(2, idUser);
            statementLogin.executeUpdate();
            PreparedStatement statementUser = preparedStatementMap.get(UPDATE_USER);
            statementUser.setString(1, newUser.getFirstName());
            statementUser.setString(2, newUser.getLastName());
            statementUser.setString(3, newUser.getEmail());
            statementUser.setLong(4, idUser);
            statementUser.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param email String
     * @return User
     * @throws DaoException if no such user or error SQl
     */
    @Override
    public User getUserByEmail(String email) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_USERS_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            } else {
                logger.error("No such user");
                throw new DaoException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser      long
     * @param newPassword String
     * @throws DaoException if error SQL
     */
    @Override
    public void resetPassword(long idUser, String newPassword) throws DaoException {
        String password = UserUtil.passwordHash(newPassword);
        try {
            PreparedStatement statement = preparedStatementMap.get(UPDATE_PASSWORD);
            statement.setString(1, password);
            statement.setLong(2, idUser);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param firstName String
     * @param lastName  String
     * @param email     String
     * @return User
     * @throws DaoException if no such user or error SQL
     */
    @Override
    public User gerUserByParam(String firstName, String lastName, String email) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_USER_FIRSTNAME_LASTNAME_EMAIL);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            } else {
                logger.error("No such user");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param idUser long
     * @throws DaoException if no such user or error SQL
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteUserCommand
     */
    public void deleteUserById(long idUser) throws DaoException {
        try {
            PreparedStatement statementUser = preparedStatementMap.get(DELETE_USER);
            PreparedStatement statementLogin = preparedStatementMap.get(DELETE_LOGIN);
            statementUser.setLong(1, idUser);
            statementLogin.setLong(1, idUser);
            statementLogin.executeUpdate();
            statementUser.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }
}
