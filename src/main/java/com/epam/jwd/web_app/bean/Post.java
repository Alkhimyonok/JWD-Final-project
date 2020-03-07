package com.epam.jwd.web_app.bean;

import java.util.Date;

public class Post {
    private Edition edition;
    private String post;
    private String pathImg;
    private Date date;

    public Post() {
        edition = new Edition();
        post = "";
        pathImg = "";
        date = new Date();
    }

    public Post(Edition edition, String post, String pathImg) {
        this.edition = edition;
        this.post = post;
        this.pathImg = pathImg;
    }

    public Post(Edition edition, String post, String pathImg, Date date) {
        this.edition = edition;
        this.post = post;
        this.pathImg = pathImg;
        this.date = date;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
