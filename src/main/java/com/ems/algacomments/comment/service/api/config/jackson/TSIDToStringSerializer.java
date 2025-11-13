package com.ems.algacomments.comment.service.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.hypersistence.tsid.TSID;
import lombok.Builder;

import java.io.IOException;

@Builder
public class TSIDToStringSerializer extends JsonSerializer<TSID> {

    @Override
    public void serialize(TSID tsid, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(tsid.toString());
    }

}
