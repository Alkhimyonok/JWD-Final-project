package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.dao.BankBillDao;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.BankBillService;
import com.epam.jwd.web_app.service.exception.ServiceException;

public class BankBillServiceImpl implements BankBillService {

    @Override
    public void pay(long accountNumber, long code, long total) throws ServiceException {
        BankBillDao bankBillDao = DaoFactory.getInstance().getBankBillDao();
        if (total == 0) {
            return;
        }
        try {
            bankBillDao.pay(accountNumber, code, total);
        } catch (DaoException e) {
            if (e.getMessage() != null) {
                throw new ServiceException(e.getMessage());
            } else throw new ServiceException();
        }
    }
}
