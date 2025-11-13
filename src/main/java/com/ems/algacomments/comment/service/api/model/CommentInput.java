package com.ems.algacomments.comment.service.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentInput {

    @NotBlank(message = "text field is mandatory")
    private String text;
    @NotBlank(message = "author field is mandatory")
    private String author;

}
