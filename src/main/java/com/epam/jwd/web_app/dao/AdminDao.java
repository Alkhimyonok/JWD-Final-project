package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.dao.exception.DaoException;

public interface AdminDao {
    boolean isAdmin(Login login) throws DaoException;
}
