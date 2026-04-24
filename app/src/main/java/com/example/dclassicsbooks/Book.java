package com.example.dclassicsbooks;

public class Book {

    private String title;
    private String author;
    private String description;
    private String genre;
    private String isbn;
    private int pages;
    private String releaseDate;
    private int imageRes;

    public Book(String title, String author, String description,
                String genre, String isbn, int pages,
                String releaseDate, int imageRes) {

        this.title = title;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.isbn = isbn;
        this.pages = pages;
        this.releaseDate = releaseDate;
        this.imageRes = imageRes;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getIsbn() { return isbn; }
    public int getPages() { return pages; }
    public String getReleaseDate() { return releaseDate; }
    public int getImageRes() { return imageRes; }
}
