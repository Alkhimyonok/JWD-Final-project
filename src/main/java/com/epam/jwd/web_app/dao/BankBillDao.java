package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.dao.exception.DaoException;

public interface BankBillDao {
    void pay(long accountNumber, long code, long total) throws DaoException;
}
