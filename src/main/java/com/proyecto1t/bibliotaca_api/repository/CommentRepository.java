package com.proyecto1t.bibliotaca_api.repository;

import com.proyecto1t.bibliotaca_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
