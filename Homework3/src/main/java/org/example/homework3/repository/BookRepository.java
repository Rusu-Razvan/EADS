package org.example.homework3.repository;

import org.springframework.stereotype.Repository;

import org.example.homework3.domainModel.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {

    private Map<Integer, Book> books = new HashMap<>();

    public Collection<Book> getBooks(){
        return books.values();
    }

    public void addBook(Book book){
        books.put(book.getId(), book);
    }

    public void removeBook(int id){
        books.remove(id);
    }

    public Book findBookById(int id){
        return books.get(id);
    }

}
