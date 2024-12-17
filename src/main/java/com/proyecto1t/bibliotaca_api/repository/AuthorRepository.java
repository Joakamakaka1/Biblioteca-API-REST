package com.proyecto1t.bibliotaca_api.repository;

import com.proyecto1t.bibliotaca_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
