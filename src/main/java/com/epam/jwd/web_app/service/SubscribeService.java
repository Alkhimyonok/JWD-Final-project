package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.util.List;

public interface SubscribeService {

    Subscribe addToCart(User user, long idEdition) throws ServiceException;

    void subscribe(long idUser) throws ServiceException;

    List<Subscribe> getListCart(long id) throws ServiceException;

    List<Subscribe> getListByIdUser(long idUser) throws ServiceException;

    Subscribe getDeleteByIdSubscribe(long idSubscribe) throws ServiceException;

    void unsubscribe(long idSubscribe) throws ServiceException;

    void subscribeCreditByUser(User user) throws ServiceException;

    Subscribe getSubscribeById(long id) throws ServiceException;

    void payCredit(long idSubscribe) throws ServiceException;

    List<Subscribe> getListInCredit() throws ServiceException;

    List<Subscribe> getListInCreditByIdUser(long idUser) throws ServiceException;

    void deleteSubscribeCreditByIdUser(long idUser) throws ServiceException;
}
