package com.ems.algacomments.comment.service.domain.repository;

import com.ems.algacomments.comment.service.domain.model.Comment;
import com.ems.algacomments.comment.service.domain.model.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
