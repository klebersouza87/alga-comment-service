package com.ems.algacomments.comment.service.api.service;

import com.ems.algacomments.comment.service.api.client.CommentModerationClient;
import com.ems.algacomments.comment.service.api.common.IdGenerator;
import com.ems.algacomments.comment.service.api.model.CommentInput;
import com.ems.algacomments.comment.service.api.model.CommentOutput;
import com.ems.algacomments.comment.service.api.model.ModerationInput;
import com.ems.algacomments.comment.service.api.model.ModerationOutput;
import com.ems.algacomments.comment.service.domain.model.Comment;
import com.ems.algacomments.comment.service.domain.model.CommentId;
import com.ems.algacomments.comment.service.domain.repository.CommentRepository;
import io.hypersistence.tsid.TSID;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Log4j2
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentModerationClient commentModerationClient;

    public CommentService(CommentRepository commentRepository, CommentModerationClient commentModerationClient) {
        this.commentRepository = commentRepository;
        this.commentModerationClient = commentModerationClient;
    }

    public Comment moderateComment(CommentInput commentInput) {
        Comment comment = Comment.builder()
                .id(CommentId.builder().id(IdGenerator.generateTSID()).build())
                .text(commentInput.getText())
                .author(commentInput.getAuthor())
                .createdAt(OffsetDateTime.now())
                .build();

        ModerationOutput moderationOutput = commentModerationClient.moderateComment(
                ModerationInput.builder()
                        .commentId(comment.getId().toString())
                        .text(comment.getText())
                        .build());

        if (moderationOutput.isNotApproved()) {
            log.error("Comment with id: {} not approved. Reason: {}", comment.getId(), moderationOutput.getReason());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Comment not approved: " + moderationOutput.getReason());
        }

        log.info("Comment with id: {} moderated successfully with reason: {}", comment.getId(), moderationOutput.getReason());
        return comment;
    }

    public CommentOutput create(Comment comment) {
        comment = commentRepository.saveAndFlush(comment);
        log.info("Comment created with id: {}", comment.getId());
        return convertToCommentOutput(comment);
    }

    public CommentOutput find(TSID id) {
        Comment comment = commentRepository.findById(CommentId.builder().id(id).build())
                .orElseThrow(() -> {
                    log.error("Comment not found with id: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with id: " + id);
                });

        log.info("Comment found with id: {}", comment.getId());
        return convertToCommentOutput(comment);
    }

    public Page<CommentOutput> findAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        log.info("Found {} comments", comments.getTotalElements());

        return comments.map(CommentService::convertToCommentOutput);
    }

    private static CommentOutput convertToCommentOutput(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().getId())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
