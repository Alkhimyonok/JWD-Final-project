package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.dao.AdminDao;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.AdminService;
import com.epam.jwd.web_app.service.exception.ServiceException;

public class AdminServiceImpl implements AdminService {

    @Override
    public boolean isAdmin(Login login) throws ServiceException {
        AdminDao adminDao = DaoFactory.getInstance().getAdminDao();
        if (login.getPassword().equals("") || login.getEmail().equals("")) {
            throw new ServiceException("locale.err.login.noFull");
        }
        try {
            return adminDao.isAdmin(login);
        } catch (DaoException e) {
            //loggger!!!!!!!!!!!!!!!!!!!!!!
            throw new ServiceException();
        }
    }
}
