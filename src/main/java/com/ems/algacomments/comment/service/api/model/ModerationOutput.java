package com.ems.algacomments.comment.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerationOutput {

    private boolean approved;
    private String reason;

    public boolean isNotApproved() {
        return !approved;
    }

}
