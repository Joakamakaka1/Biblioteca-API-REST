package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.AuthorDTO;
import com.proyecto1t.bibliotaca_api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/") // -> http://localhost:8080/authors/
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}") // -> http://localhost:8080/authors/1
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable String id) {
        AuthorDTO author = authorService.findById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/") // -> http://localhost:8080/authors/
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO author) {
        AuthorDTO newAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // -> http://localhost:8080/authors/1
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable String id, @RequestBody AuthorDTO author) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // -> http://localhost:8080/authors/1
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
