package com.proyecto1t.bibliotaca_api.service;

import com.proyecto1t.bibliotaca_api.dto.CommentDTO;
import com.proyecto1t.bibliotaca_api.exceptions.NotFoundException;
import com.proyecto1t.bibliotaca_api.model.Book;
import com.proyecto1t.bibliotaca_api.model.Comment;
import com.proyecto1t.bibliotaca_api.model.User;
import com.proyecto1t.bibliotaca_api.repository.BookRepository;
import com.proyecto1t.bibliotaca_api.repository.CommentRepository;
import com.proyecto1t.bibliotaca_api.repository.UserRepository;
import com.proyecto1t.bibliotaca_api.utils.Mapper;
import com.proyecto1t.bibliotaca_api.utils.StringToLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private StringToLong stringToLong;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) {
            throw new NotFoundException("Comments not found");
        }

        List<CommentDTO> commentDTOs = new ArrayList<>();
        comments.forEach(comment -> commentDTOs.add(mapper.toCommentDTO(comment)));
        return commentDTOs;
    }

    public CommentDTO findById(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Comment not found");
        }
        Comment comment = commentRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        return mapper.toCommentDTO(comment);
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        if (commentDTO == null) {
            throw new NotFoundException("Comment not found");
        }

        User userId = userRepository.findById(commentDTO.getUserId().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book bookId = bookRepository.findById(commentDTO.getBookId().getId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Comment comment = mapper.toCommentEntity(commentDTO, userId, bookId);
        comment = commentRepository.save(comment);
        return mapper.toCommentDTO(comment);
    }

    public CommentDTO updateComment(String id, CommentDTO commentDTO) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Comment not found");
        }

        User user = userRepository.findById(commentDTO.getUserId().getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findById(commentDTO.getBookId().getId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Comment existingComment = commentRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        existingComment.setContent(commentDTO.getContent());
        existingComment.setUser(user);
        existingComment.setBook(book);

        Comment comment = commentRepository.save(existingComment);
        return mapper.toCommentDTO(comment);
    }

    public void deleteComment(String id) {
        if (id.isEmpty() || id.isBlank()) {
            throw new NotFoundException("Comment not found");
        }

        Comment comment = commentRepository.findById(stringToLong.method(id))
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        commentRepository.deleteById(comment.getId());
    }
}
