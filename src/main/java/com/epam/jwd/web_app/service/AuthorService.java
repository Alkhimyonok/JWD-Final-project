package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    Author getAuthorById(long idAuthor) throws ServiceException;
}
