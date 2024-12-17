package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.BookDTO;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Author;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Category;
import com.proyecto1t.bibliotaca_api.repository.BookRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;

    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NotFoundException("Books not found");
        }

        List<BookDTO> bookDTOs = new ArrayList<>();
        books.forEach(book -> bookDTOs.add(mapper.toBookDTO(book)));
        return bookDTOs;
    }

    public BookDTO findById(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book not found");
        }
        Book book = bookRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return mapper.toBookDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO, Author authorId, Category categoryId) {
        if (bookDTO == null) {
            throw new NotFoundException("Book not found");
        }

        if(authorId == null) {
            throw new NotFoundException("Author not found");
        }

        if(categoryId == null) {
            throw new NotFoundException("Category not found");
        }

        Book book = mapper.toBookEntity(bookDTO, authorId, categoryId);
        book = bookRepository.save(book);
        return mapper.toBookDTO(book);
    }

    public BookDTO updateBook(String id, BookDTO bookDTO, Author authorId, Category categoryId) {
        if (bookDTO == null) {
            throw new NotFoundException("Book not found");
        }

        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book not found");
        }

        if(authorId == null) {
            throw new NotFoundException("Author not found");
        }

        if(categoryId == null) {
            throw new NotFoundException("Category not found");
        }

        Book existingBook = bookRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Book book = mapper.toBookEntity(bookDTO, authorId, categoryId);
        book.setId(existingBook.getId());
        book = bookRepository.save(book);
        return mapper.toBookDTO(book);
    }

    public void deleteBook(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book not found");
        }
        Book book = bookRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Book not found"));

        bookRepository.deleteById(book.getId());
    }
}
