package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.exception.ServiceException;

public interface BankBillService {
    void pay(long accountNumber, long code, long total) throws ServiceException;
}
