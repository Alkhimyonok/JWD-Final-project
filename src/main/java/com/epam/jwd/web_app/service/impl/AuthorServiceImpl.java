package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.dao.AuthorDao;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.AuthorService;
import com.epam.jwd.web_app.service.exception.ServiceException;

/**
 * Implements {@link AuthorService} interface.
 * @see AuthorDao
 */

public class AuthorServiceImpl implements AuthorService {
    private AuthorDao authorDao = DaoFactory.getInstance().getAuthorDao();

    /**
     * @param idAuthor long
     * @return Author
     * @throws ServiceException if error Dao
     */
    @Override
    public Author getAuthorById(long idAuthor) throws ServiceException {
        try {
            return authorDao.getAuthorById(idAuthor);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
