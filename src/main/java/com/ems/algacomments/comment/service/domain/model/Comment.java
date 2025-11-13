package com.ems.algacomments.comment.service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false, unique = true)
    private CommentId id;
    private String text;
    private String author;
    private OffsetDateTime createdAt;

}
