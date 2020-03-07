package com.epam.jwd.web_app.bean;

import java.util.Objects;

public class Edition {
    private long id;
    private String type;
    private Author author;
    private String title;
    private String description;
    private String pathImg;
    private long priceMonth;
    private long countSubscribers;
    private String status;


    public Edition() {
        author = new Author();
        title = "";
        description = "";
        pathImg = "";
        status = "";
    }

    public Edition(long id, String type, Author author, String title, String description, String pathImg, long priceMonth) {
        this.id = id;
        this.type = type;
        this.author = author;
        this.title = title;
        this.description = description;
        this.priceMonth = priceMonth;
        this.pathImg = pathImg;
    }

    public Edition(long id, String type, Author author, String title, String description, String pathImg, long priceMonth, long countSubscribers, String status) {
        this.id = id;
        this.type = type;
        this.author = author;
        this.title = title;
        this.description = description;
        this.priceMonth = priceMonth;
        this.countSubscribers = countSubscribers;
        this.pathImg = pathImg;
        this.status = status;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public long getCountSubscribers() {
        return countSubscribers;
    }

    public void setCountSubscribers(long countSubscribers) {
        this.countSubscribers = countSubscribers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPriceMonth() {
        return priceMonth;
    }

    public void setPriceMonth(long priceMonth) {
        this.priceMonth = priceMonth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return id == edition.id &&
                priceMonth == edition.priceMonth &&
                countSubscribers == edition.countSubscribers &&
                Objects.equals(type, edition.type) &&
                Objects.equals(author, edition.author) &&
                Objects.equals(title, edition.title) &&
                Objects.equals(description, edition.description) &&
                Objects.equals(pathImg, edition.pathImg) &&
                Objects.equals(status, edition.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, author, title, description, pathImg, priceMonth, countSubscribers, status);
    }
}
