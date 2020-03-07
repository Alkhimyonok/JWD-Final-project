package com.epam.jwd.web_app.dao;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.dao.exception.DaoException;
import java.util.List;

public interface PostDao {
    List<Post> getNovelty() throws DaoException;
    List<Post> getListForUserByStatus(long idUser, String status) throws DaoException;
    List<Post> getPostsByEdition(long idEdition) throws DaoException;
    void deletePostByPost(String post) throws DaoException;
    void createPost(Post newPost) throws DaoException;
}