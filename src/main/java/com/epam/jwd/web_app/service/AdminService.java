package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Login;
import com.epam.jwd.web_app.service.exception.ServiceException;

public interface AdminService {
    boolean isAdmin(Login login) throws ServiceException;
}
