package com.ems.algacomments.comment.service.api.service;

import com.ems.algacomments.comment.service.api.common.IdGenerator;
import com.ems.algacomments.comment.service.api.model.CommentInput;
import com.ems.algacomments.comment.service.api.model.CommentOutput;
import com.ems.algacomments.comment.service.domain.model.Comment;
import com.ems.algacomments.comment.service.domain.model.CommentId;
import com.ems.algacomments.comment.service.domain.repository.CommentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Log4j2
@Service
public class CommentService {

    private static final List<String> WORDS_NOT_ALLOWED = List.of("spam", "advertisement", "offensive", "drug");
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentOutput create(CommentInput commentInput) {
        if (WORDS_NOT_ALLOWED.stream().anyMatch(word -> commentInput.getText().toLowerCase().contains(word))) {
            log.error("Comment contains not allowed words: {}", commentInput.getText());
            throw new IllegalArgumentException("Comment contains not allowed words.");
        }

        Comment comment = Comment.builder()
                .id(CommentId.builder().id(IdGenerator.generateTSID()).build())
                .text(commentInput.getText())
                .author(commentInput.getAuthor())
                .createdAt(OffsetDateTime.now())
                .build();

        comment = commentRepository.saveAndFlush(comment);

        log.info("Comment created with id: {}", comment.getId());
        return CommentOutput.builder()
                .id(comment.getId().getId())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
