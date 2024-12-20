package com.proyecto1t.bibliotaca_api.controller;

import com.proyecto1t.bibliotaca_api.dto.CommentDTO;
import com.proyecto1t.bibliotaca_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/") // -> http://localhost:8080/comment/
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}") // -> http://localhost:8080/comment/1
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable String id) {
        CommentDTO comment = commentService.findById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("/") // -> http://localhost:8080/comment/
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO comment) {
        CommentDTO createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // -> http://localhost:8080/comment/1
    public ResponseEntity<CommentDTO> updateComment(@PathVariable String id,@Valid @RequestBody CommentDTO comment) {
        CommentDTO updatedComment = commentService.updateComment(id, comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // -> http://localhost:8080/comment/1
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
