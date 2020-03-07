package com.epam.jwd.web_app.service;

import com.epam.jwd.web_app.bean.Post;
import com.epam.jwd.web_app.service.exception.ServiceException;

import java.util.List;

public interface PostService {

    List<Post> getNovelty() throws ServiceException;

    List<Post> getNews(long idUser) throws ServiceException;

    List<Post> getPostsByIdEdition(long idEdition) throws ServiceException;

    void deletePostByPost(String post) throws ServiceException;

    void createPost(String type, String title, String pathImg,String post) throws ServiceException;
}
