package com.example.Revision15.model;

import java.io.Serializable;

public class Books implements Serializable {

    private String title;
    private Author author;
//    private String bookUrl;
    private String id;

    public Books(String title, Author author, String id) {
        this.title = title;
        this.author = author;
        this.id = id;
    }

    public Books(){

    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


//    public String getBookUrl() {
//        return bookUrl;
//    }
//    public void setBookUrl(String bookUrl) {
//        this.bookUrl = bookUrl;
//    }

    public void setTitle(String title) {
        this.title = title;
    }



}
