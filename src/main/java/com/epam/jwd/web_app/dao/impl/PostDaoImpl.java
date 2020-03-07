package com.epam.jwd.web_app.dao.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.bean.Subscribe;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.PostDao;
import com.epam.jwd.web_app.dao.SubscribeDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.dao.pool.ConnectionPool;
import com.epam.jwd.web_app.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PostDaoImpl implements PostDao {
    private static final Logger logger = LogManager.getLogger(PostDaoImpl.class);

    private SubscribeDao subscribeDao = DaoFactory.getInstance().getSubscribeDao();
    private EditionDao editionDao = DaoFactory.getInstance().getEditionDao();

    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private final static String SELECT_ORDER_DATE_LIMIT = "SELECT * FROM posts ORDER BY date DESC LIMIT 4;";
    private final static String SELECT_BY_EDITION = "SELECT * FROM posts WHERE idEdition=?;";
    private final static String DELETE_BY_POST = "DELETE from posts where post=?;";
    private final static String INSERT = "INSERT INTO posts(idEdition, post, pathImg, date) VALUES (?, ?, ?, NOW());";

    private final static int ID_COLUMN = 1;
    private final static int POST_COLUMN = 2;
    private final static int PATHIMG_COLUMN = 3;
    private final static int DATE_COLUMN = 4;

    private Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public PostDaoImpl() {
        try (Connection connection = POOL.takeConnection()) {
            setPreparedStatement(connection, SELECT_ORDER_DATE_LIMIT);
            setPreparedStatement(connection, SELECT_BY_EDITION);
            setPreparedStatement(connection, DELETE_BY_POST);
            setPreparedStatement(connection, INSERT);
        } catch (ConnectionPoolException | SQLException | DaoException e) {
            logger.error(e.getMessage());
        }
    }

    private Post createPost(ResultSet resultSet) throws SQLException, DaoException {
        EditionDao editionDao = DaoFactory.getInstance().getEditionDao();
        long idEdition = resultSet.getLong(ID_COLUMN);
        Edition edition = editionDao.getEditionById(idEdition);
        String post = resultSet.getString(POST_COLUMN);
        String pathImg = resultSet.getString(PATHIMG_COLUMN);
        Date date = resultSet.getDate(DATE_COLUMN);
        return new Post(edition, post, pathImg, date);
    }

    private void setPreparedStatement(Connection connection, String sql) throws DaoException {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException(e);

            }
        }
    }

    /**
     * @see com.epam.jwd.web_app.controller.command.impl.IndexCommand
     * @return List Post size 4 sort by date
     * @throws DaoException if error JDBC
     */
    @Override
    public List<Post> getNovelty() throws DaoException {
        List<Post> novelty = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_ORDER_DATE_LIMIT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                novelty.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return novelty;
    }

    /**
     * @param idUser long
     * @param status String status(good, block)
     * @return List Post
     * @throws DaoException if error JDBC
     */
    @Override
    public List<Post> getListForUserByStatus(long idUser, String status) throws DaoException {
        List<Subscribe> subscribeList = subscribeDao.getListForUserByStatus(idUser, status);
        List<Post> postList = new ArrayList<>();
        for (Subscribe subscribe : subscribeList) {
            try {
                PreparedStatement preparedStatement = preparedStatementMap.get(SELECT_BY_EDITION);
                preparedStatement.setLong(1, subscribe.getEdition().getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    postList.add(createPost(resultSet));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException();
            }
        }
        return postList;
    }

    /**
     * @param idEdition long
     * @return List Post
     * @throws DaoException if error JDBC
     */
    @Override
    public List<Post> getPostsByEdition(long idEdition) throws DaoException {
        List<Post> posts = new ArrayList<>();
        try {
            PreparedStatement statement = preparedStatementMap.get(SELECT_BY_EDITION);
            statement.setLong(1, idEdition);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                posts.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
        return posts;
    }

    /** @see com.epam.jwd.web_app.controller.command.impl.DeletePostCommand
     *
     * @param post String
     * @throws DaoException if error Jdbc
     */
    @Override
    public void deletePostByPost(String post) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(DELETE_BY_POST);
            statement.setString(1, post);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }

    /**
     * @param newPost Post
     * @throws DaoException if error JDBC
     */
    @Override
    public void createPost(Post newPost) throws DaoException {
        try {
            PreparedStatement statement = preparedStatementMap.get(INSERT);
            statement.setLong(1, newPost.getEdition().getId());
            statement.setString(2, newPost.getPost());
            statement.setString(3, newPost.getPathImg());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException();
        }
    }
}

