package com.epam.jwd.web_app.bean;

import java.util.Date;
import java.util.Objects;

public class Subscribe {
    private long id;
    private User user;
    private Edition edition;
    private String status;
    private Date date;

    public Subscribe() {
        user = new User();
        edition = new Edition();
        status = "";
        date = new Date();
    }

    public Subscribe(long id, User user, Edition edition, String status, Date date) {
        this.id = id;
        this.user = user;
        this.edition = edition;
        this.status = status;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscribe subscribe = (Subscribe) o;
        return id == subscribe.id &&
                Objects.equals(user, subscribe.user) &&
                Objects.equals(edition, subscribe.edition) &&
                Objects.equals(status, subscribe.status) &&
                Objects.equals(date, subscribe.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, edition, status, date);
    }
}
