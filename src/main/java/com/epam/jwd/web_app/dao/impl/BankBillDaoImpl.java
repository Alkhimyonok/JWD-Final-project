package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.dao.BankBillDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BankBillDaoImpl implements BankBillDao {
    private static final Logger logger = LogManager.getLogger(BankBillDaoImpl.class);
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    private static final String SELECT_BY_ID_CODE = "SELECT * FROM bankBill WHERE id=? AND code=?;";
    private static final String UPDATE_BILL_BY_ID_CODE = "UPDATE bankBill set bill=? WHERE id=? AND code=?;";

    public BankBillDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_BY_ID_CODE);
            setPreparedStatement(connection, UPDATE_BILL_BY_ID_CODE);
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

    /**
     * @param accountNumber - long number of account bank bill
     * @param code          - long private code
     * @param total         - long sum of payments
     * @throws DaoException if no such account or no enough money or JDBC error
     */

    @Override
    public void pay(long accountNumber, long code, long total) throws DaoException {
        long bill = 0;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_ID_CODE);
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setLong(2, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bill = resultSet.getLong("bill");
            } else {
                logger.error("No such bank account.");
                throw new DaoException("locale.err.pay.auth");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        if (bill >= total) {
            bill = bill - total;
            try {
                PreparedStatement preparedStatement = preparedStatementMap.get(UPDATE_BILL_BY_ID_CODE);
                preparedStatement.setLong(1, bill);
                preparedStatement.setLong(2, accountNumber);
                preparedStatement.setLong(3, code);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException();
            }
        } else {
            logger.error("No money!");
            throw new DaoException("locale.err.pay.total");
        }
    }
}
