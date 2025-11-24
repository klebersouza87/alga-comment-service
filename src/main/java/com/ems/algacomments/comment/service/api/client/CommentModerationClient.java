package com.ems.algacomments.comment.service.api.client;

import com.ems.algacomments.comment.service.api.model.ModerationInput;
import com.ems.algacomments.comment.service.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface CommentModerationClient {

    @PostExchange
    ModerationOutput moderateComment(@RequestBody ModerationInput input);

}
