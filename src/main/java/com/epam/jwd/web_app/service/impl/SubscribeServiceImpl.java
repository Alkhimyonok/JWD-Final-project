package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.bean.User;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.SubscribeDao;
import com.epam.jwd.web_app.dao.UserDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.util.UtilDao;
import com.epam.jwd.web_app.service.SubscribeService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubscribeServiceImpl implements SubscribeService {
    private SubscribeDao subscribeDao = DaoFactory.getInstance().getSubscribeDao();
    private EditionDao editionDao = DaoFactory.getInstance().getEditionDao();
    private UserDao userDao = DaoFactory.getInstance().getUserDao();

    @Override
    public Subscribe addToCart(User user, long idEdition) throws ServiceException {
        try {
            if (subscribeDao.isSubscribeUserByIdEdition(user.getId(), idEdition)) {
                throw new ServiceException("locale.err.subscribe.already");
            }
            if (editionDao.isBlockedEdition(idEdition)) {
                throw new ServiceException("locale.err.add.block");
            }
        } catch (DaoException e) {
            //logggg
            throw new ServiceException();
        }
        try {
            long idSubscribe = UtilDao.generateIdByTable("subscribe");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(new Date()));
            String status = "in process";
            Edition edition = editionDao.getEditionById(idEdition);
            Subscribe subscribe = new Subscribe(idSubscribe, user, edition, status, date);
            subscribeDao.addSubscribe(subscribe);
            return subscribe;
        } catch (DaoException | ParseException e) {
            //
            throw new ServiceException();
        }
    }

    @Override
    public void subscribe(long idUser) throws ServiceException {
        try {
            subscribeDao.subscribeByStartStatus(idUser, "in process");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Subscribe> getListCart(long idUser) throws ServiceException {
        try {
            return subscribeDao.getListForUserByStatus(idUser, "in process");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Subscribe> getListByIdUser(long idUser) throws ServiceException {
        List<Subscribe> subscribeList = new ArrayList<>();
        try {
            subscribeList.addAll(subscribeDao.getListForUserByStatus(idUser, "done"));
            subscribeList.addAll(subscribeDao.getListForUserByStatus(idUser, "credit"));
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return subscribeList;
    }

    /**@see com.epam.jwd.web_app.controller.command.impl.DeleteSubscribeCommand
     * @param idSubscribe long
     * @return Subscribe
     * @throws ServiceException if error Dao
     */
    @Override
    public Subscribe getDeleteByIdSubscribe(long idSubscribe) throws ServiceException {
        try {
            return subscribeDao.getDeleteByIdSubscribe(idSubscribe);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void unsubscribe(long idSubscribe) throws ServiceException {
        try {
            subscribeDao.unsubscribe(idSubscribe);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     *
     * @param user User
     * @throws ServiceException if error Dao
     */
    @Override
    public void subscribeCreditByUser(User user) throws ServiceException {
        try {
            subscribeDao.subscribeCreditByIdUser(user.getId());
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public Subscribe getSubscribeById(long id) throws ServiceException {
        Subscribe subscribe;
        try {
            subscribe = subscribeDao.getSubscribeById(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return subscribe;
    }

    @Override
    public void payCredit(long idSubscribe) throws ServiceException {
        try {
            subscribeDao.payCredit(idSubscribe);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**@see com.epam.jwd.web_app.controller.command.impl.ListSubscribeCreditCommand
     *
     * @return List Subscribe sort by date
     * @throws ServiceException if error Dao
     */
    @Override
    public List<Subscribe> getListInCredit() throws ServiceException {
        try {
            return sortByDate(subscribeDao.getListInCredit());
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    private List<Subscribe> sortByDate(List<Subscribe> subscribeList) {
        subscribeList.sort(new Comparator<Subscribe>() {
            @Override
            public int compare(Subscribe o1, Subscribe o2) {
                return Integer.compare(o1.getDate().compareTo(o2.getDate()), 0);
            }
        });
        return subscribeList;
    }

    @Override
    public List<Subscribe> getListInCreditByIdUser(long idUser) throws ServiceException {
        List<Subscribe> subscribeList;
        try {
            subscribeList = subscribeDao.getListForUserByStatus(idUser, "credit");
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return subscribeList;
    }

    @Override
    public void deleteSubscribeCreditByIdUser(long idUser) throws ServiceException {
        try {
            subscribeDao.deleteSubscribeForUserByStatus(idUser, "credit");
            userDao.updateStatusByIdUser(idUser, "good");
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
