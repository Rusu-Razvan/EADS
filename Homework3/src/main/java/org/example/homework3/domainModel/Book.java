package org.example.homework3.domainModel;

public class Book {

    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;

    private static int idCounter = 1;

    public Book(String title, String author, boolean isBorrowed) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.isBorrowed = isBorrowed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
}
