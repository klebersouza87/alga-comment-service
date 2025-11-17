package com.ems.algacomments.comment.service.api.controller;

import com.ems.algacomments.comment.service.api.model.CommentInput;
import com.ems.algacomments.comment.service.api.model.CommentOutput;
import com.ems.algacomments.comment.service.api.service.CommentService;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Log4j2
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@Valid @RequestBody CommentInput comment) {
        log.info("Creating comment: {}", comment);
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
