package com.ems.algacomments.comment.service.api.controller;

import com.ems.algacomments.comment.service.api.model.CommentInput;
import com.ems.algacomments.comment.service.api.model.CommentOutput;
import com.ems.algacomments.comment.service.api.service.CommentService;
import com.ems.algacomments.comment.service.domain.model.Comment;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@Log4j2
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@Valid @RequestBody CommentInput commentInput) {
        log.info("Creating comment: {}", commentInput);
        Comment comment = commentService.moderateComment(commentInput);
        return commentService.create(comment);
    }

    @GetMapping("/{id}")
    public CommentOutput find(@PathVariable TSID id) {
        log.info("Finding comment with id: {}", id);
        return commentService.find(id);
    }

    @GetMapping
    public Page<CommentOutput> findAll(@PageableDefault Pageable pageable) {
        log.info("Finding all comments");
        return commentService.findAll(pageable);
    }

}
