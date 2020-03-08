package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Author;
import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.controller.command.Command;
import com.epam.jwd.web_app.dao.AuthorDao;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.impl.EditionDaoImpl;
import com.epam.jwd.web_app.dao.util.UtilDao;
import com.epam.jwd.web_app.service.EditionService;
import com.epam.jwd.web_app.service.PostService;
import com.epam.jwd.web_app.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EditionServiceImpl implements EditionService {
    /**
     * Implements {@link EditionService} interface.
     *
     * @see EditionDao
     * @see AuthorDao
     */

    private static final Logger logger = LogManager.getLogger(EditionServiceImpl.class);

    private AuthorDao authorDao = DaoFactory.getInstance().getAuthorDao();
    private EditionDao editionDao = DaoFactory.getInstance().getEditionDao();

    /**
     * Delete blocked edition from list.
     *
     * @param editionList List Edition
     */
    private void deleteBlockEdition(List<Edition> editionList) {
        for (Edition edition : editionList) {
            if (edition.getStatus().equals("block")) {
                editionList.remove(edition);
            }
        }
    }

    /**
     * Sort list by count subscribers.
     *
     * @param editions List Edition startList
     * @return List Edition sortList
     */
    private List<Edition> sortByCountSubscribers(List<Edition> editions) {
        Collections.sort(editions, new Comparator<Edition>() {
            @Override
            public int compare(Edition o1, Edition o2) {
                if (o1.getCountSubscribers() - (o2.getCountSubscribers()) > 0) {
                    return 1;
                }
                if (o1.getCountSubscribers() - o2.getCountSubscribers() == 0) {
                    return 0;
                } else return -1;
            }
        });
        return editions;
    }

    /**
     * @param idEdition long
     * @return Edition
     * @throws ServiceException if error Dao
     */
    @Override
    public Edition getEditionById(long idEdition) throws ServiceException {
        try {
            return editionDao.getEditionById(idEdition);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     * Get List Edition sort by count subscribers and delete blocked editions.
     *
     * @param type String typeEdition(magazine, newspaper, comics)
     * @return List Edition
     * @throws ServiceException if error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.ListEditionsCommand
     */
    @Override
    public List<Edition> getListByType(String type) throws ServiceException {
        List<Edition> editionList;
        if (type.equals("")) {
            try {
                editionList = editionDao.getAllList();
                sortByCountSubscribers(editionList);
                deleteBlockEdition(editionList);
                return editionList;
            } catch (DaoException e) {
                throw new ServiceException();
            }
        } else {
            try {
                editionList = editionDao.getListByType(type);
                sortByCountSubscribers(editionList);
                deleteBlockEdition(editionList);
                return editionList;
            } catch (DaoException e) {
                throw new ServiceException();
            }
        }
    }

    /**
     * Get List Edition without blocked editions.
     *
     * @return List Edition
     * @throws ServiceException if error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.IndexCommand
     */
    @Override
    public List<Edition> getTopList() throws ServiceException {
        try {
            List<Edition> editionList = editionDao.getTopList();
            deleteBlockEdition(editionList);
            return editionList;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     * Get List Edition by parameters, sort by count subscribers and delete blocked editions.
     *
     * @param title  String
     * @param author String
     * @return List Edition
     * @throws ServiceException if no full info or error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.SearchCommand
     */
    @Override
    public List<Edition> getListByParameters(String title, String author) throws ServiceException {
        EditionDao editionDao = DaoFactory.getInstance().getEditionDao();
        if (title.equals("") && author.equals("")) {
            throw new ServiceException("locale.err.noFull");
        }
        try {
            List<Edition> searchList = editionDao.getListByParameters(title, author);
            sortByCountSubscribers(searchList);
            deleteBlockEdition(searchList);
            return searchList;
        } catch (DaoException e) {
            if (e.getMessage() != null) {
                throw new ServiceException(e.getMessage());
            }
            throw new ServiceException();
        }
    }

    /**
     * Get List Edition by id author, sort by count subscribers and delete blocked editions.
     *
     * @param idAuthor long
     * @return List Edition
     * @throws ServiceException
     * @see com.epam.jwd.web_app.controller.command.impl.DetailAuthorCommand
     */
    @Override
    public List<Edition> getEditionListByIdAuthor(long idAuthor) throws ServiceException {
        EditionDao editionDao = DaoFactory.getInstance().getEditionDao();
        try {
            List<Edition> searchList = editionDao.getListByIdAuthor(idAuthor);
            sortByCountSubscribers(searchList);
            deleteBlockEdition(searchList);
            return searchList;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     * @param idEdition long
     * @throws ServiceException if edition is already block or error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.DeleteEditionCommand
     */
    @Override
    public void deleteEditionById(long idEdition) throws ServiceException {
        try {
            if (editionDao.isBlockedEdition(idEdition)) {
                logger.error("Edition is blocked");
                throw new ServiceException("locale.err.admin.delete.already");
            }
            editionDao.deleteEditionById(idEdition);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     * @param type        String typeEdition(magazine, newspaper, comics)
     * @param authorName  String
     * @param title       String
     * @param description String
     * @param pathImg     String
     * @param priceMonth_ String
     * @throws ServiceException if no full info or error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.CreateEditionCommand
     */
    @Override
    public void createEdition(String type, String authorName, String title, String description, String pathImg, String priceMonth_) throws ServiceException {
        if (type.equals("") || authorName.equals("") || title.equals("") || pathImg.equals("") || priceMonth_.equals("")) {
            throw new ServiceException("locale.err.noFull");
        }
        try {
            Author author = authorDao.getAuthorByName(authorName);
            long idEdition = UtilDao.generateIdByTable("editions");
            long priceMonth = Long.parseLong(priceMonth_);
            Edition edition = new Edition(idEdition, type, author, title, description, pathImg, priceMonth);
            editionDao.createEdition(edition);
        } catch (DaoException e) {
            if (e.getMessage() != null) {
                throw new ServiceException(e.getMessage());
            } else {
                throw new ServiceException();
            }
        }
    }
}
