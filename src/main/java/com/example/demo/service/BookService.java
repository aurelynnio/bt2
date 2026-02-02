package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Book;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public BookService() {
        // Thêm một số sách mẫu
        books.add(new Book(1, "Java Programming", "John Doe"));
        books.add(new Book(2, "Spring Boot Guide", "Jane Smith"));
        books.add(new Book(3, "REST API Design", "Bob Johnson"));
    }

    // 1. Lấy danh sách tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // 2. Lấy thông tin sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 3. Thêm sách mới
    public void addBook(Book book) {
        if (book.getId() == 0) {
            int maxId = books.stream().mapToInt(Book::getId).max().orElse(0);
            book.setId(maxId + 1);
        }
        books.add(book);
    }

    // 4. Cập nhật thông tin sách
    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    // 5. Xóa sách theo ID
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}
