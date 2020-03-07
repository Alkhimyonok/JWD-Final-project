package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.util.List;

public interface EditionService {
    Edition getEditionById(long idEdition) throws ServiceException;

    List<Edition> getListByType(String type) throws ServiceException;

    List<Edition> getTopList() throws ServiceException;

    List<Edition> getListByParameters(String title, String author) throws ServiceException;

    List<Edition> getEditionListByIdAuthor(long idAuthor) throws ServiceException;

    void deleteEditionById(long idEdition) throws ServiceException;

    void createEdition(String type, String author, String title, String description, String pathImg, String priceMonth) throws ServiceException;
}
