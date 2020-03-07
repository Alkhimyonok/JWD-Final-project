package com.epam.jwd.web_app.service.impl;

import com.epam.jwd.web_app.bean.Edition;
import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.dao.DaoFactory;
import com.epam.jwd.web_app.dao.EditionDao;
import com.epam.jwd.web_app.dao.PostDao;
import com.epam.jwd.web_app.dao.exception.DaoException;
import com.epam.jwd.web_app.service.PostService;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.util.*;

public class PostServiceImpl implements PostService {
    private PostDao postDao = DaoFactory.getInstance().getPostDao();
    private EditionDao editionDao = DaoFactory.getInstance().getEditionDao();

    private void sortByDate(List<Post> posts) {
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                if (o1.getDate().compareTo(o2.getDate()) > 0) {
                    return 1;
                }
                if (o1.getDate().compareTo(o2.getDate()) == 0) {
                    return 0;
                } else return -1;
            }
        });
    }

    /**
     * @return List Post size 4 sort by date
     * @throws ServiceException if error Dao
     */
    @Override
    public List<Post> getNovelty() throws ServiceException {
        try {
            return postDao.getNovelty();
        } catch (DaoException e) {
            throw new ServiceException();
        }

    }

    @Override
    public List<Post> getNews(long idUser) throws ServiceException {
        List<Post> postList = new ArrayList<>();
        try {
            postList.addAll(postDao.getListForUserByStatus(idUser, "done"));
            postList.addAll(postDao.getListForUserByStatus(idUser, "credit"));
        } catch (DaoException e) {
            throw new ServiceException();
        }
        sortByDate(postList);
        return postList;
    }

    @Override
    public List<Post> getPostsByIdEdition(long idEdition) throws ServiceException {
        List<Post> posts;
        try {
            posts = postDao.getPostsByEdition(idEdition);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return posts;
    }

    /**
     * @param post String
     * @throws ServiceException if error Dao
     * @see com.epam.jwd.web_app.controller.command.impl.DeletePostCommand
     */
    @Override
    public void deletePostByPost(String post) throws ServiceException {
        try {
            postDao.deletePostByPost(post);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    /**
     * @param type    String typeEdition(magazine, newspaper, comics)
     * @param title   String
     * @param pathImg String
     * @param post    String
     * @throws ServiceException if no full info or error Jdbc
     * @see com.epam.jwd.web_app.controller.command.impl.CreatePostCommand
     */
    @Override
    public void createPost(String type, String title, String pathImg, String post) throws ServiceException {
        if (type.equals("") || title.equals("") || pathImg.equals("") || post.equals("")) {
            throw new ServiceException("locale.err.noFull");
        }
        try {
            Edition edition = editionDao.getEditionByTypeTitle(type, title);
            Post newPost = new Post(edition, post, pathImg);
            postDao.createPost(newPost);
        } catch (DaoException e) {
            if (e.getMessage() != null) {
                throw new ServiceException(e.getMessage());
            } else throw new ServiceException();
        }
    }
}
