package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.BookDTO;
import com.proyecto1t.bibliotaca_api.exceptions.DuplicateException;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Author;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Category;
import com.proyecto1t.bibliotaca_api.repository.AuthorRepository;
import com.proyecto1t.bibliotaca_api.repository.BookRepository;
import com.proyecto1t.bibliotaca_api.repository.CategoryRepository;
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
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book not found");
        }

        Book book = bookRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return mapper.toBookDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        if (bookDTO == null) {
            throw new NotFoundException("Book not found");
        }

        Author authorId = authorRepository.findByName(bookDTO.getAuthorId().getName())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Category categoryId = categoryRepository.findByName(bookDTO.getCategoryId().getName())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if(bookRepository.findByIsbn(bookDTO.getIsbn()).isPresent()) {
            throw new DuplicateException("Isbn already exists");
        }

        Book book = mapper.toBookEntity(bookDTO, authorId, categoryId);
        book = bookRepository.save(book);
        return mapper.toBookDTO(book);
    }

    public BookDTO updateBook(String id, BookDTO bookDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book ID cannot be null or empty");
        }

        Author author = authorRepository.findByName(bookDTO.getAuthorId().getName())
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Category category = categoryRepository.findByName(bookDTO.getCategoryId().getName())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Book existingBook = bookRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new DuplicateException("Book not found"));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setAuthor(author);
        existingBook.setCategory(category);

        Book savedBook = bookRepository.save(existingBook);
        return mapper.toBookDTO(savedBook);
    }

    public void deleteBook(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Book not found");
        }

        Book book = bookRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Book not found"));

        bookRepository.deleteById(book.getId());
    }
}
