package com.ems.algacomments.comment.service.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CommentId implements Serializable {

    private TSID id;

    public CommentId(TSID id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public CommentId(Long id) {
        Objects.requireNonNull(id);
        this.id = TSID.from(id);
    }

    public CommentId(String id) {
        Objects.requireNonNull(id);
        this.id = TSID.from(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
