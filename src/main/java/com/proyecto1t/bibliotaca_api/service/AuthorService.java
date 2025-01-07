package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.AuthorDTO;
import com.proyecto1t.bibliotaca_api.exceptions.DuplicateException;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Author;
import com.proyecto1t.bibliotaca_api.repository.AuthorRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;

    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        if(authors.isEmpty()) {
            throw new NotFoundException("Authors not found");
        }

        List<AuthorDTO> authorDTOs = new ArrayList<>();
        authors.forEach(author -> authorDTOs.add(mapper.toAuthorDTO(author)));
        return authorDTOs;
    }

    public AuthorDTO findById(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Author not found");
        }

        Author author = authorRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Author not found"));

        return mapper.toAuthorDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            throw new NotFoundException("Author not found");
        }

        Author author = mapper.toAuthorEntity(authorDTO);
        author = authorRepository.save(author);
        return mapper.toAuthorDTO(author);
    }

    public AuthorDTO updateAuthor(String id, AuthorDTO authorDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Author not found");
        }

        Author existingAuthor = authorRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new DuplicateException("Author not found"));

        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setNationality(authorDTO.getNationality());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return mapper.toAuthorDTO(updatedAuthor);
    }

    public void deleteAuthor(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Author not found");
        }

        Author author = authorRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Author not found"));

        authorRepository.deleteById(author.getId());
    }
}
