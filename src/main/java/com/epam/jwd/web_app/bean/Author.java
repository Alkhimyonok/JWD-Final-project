package com.epam.jwd.web_app.bean;

import java.util.Objects;

public class Author {
    private long id;
    private String name;
    private String description;

    public Author() {
        name = "";
        description = "";
    }

    public Author(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(name, author.name) &&
                Objects.equals(description, author.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
