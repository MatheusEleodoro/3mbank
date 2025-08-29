package com.threembank.interfaces.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MaskAgencySerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            String raw = value.toString();
            String body;
            String verifier;

            if (raw.length() > 1) {
                body = raw.substring(0, raw.length() - 1);
                verifier = raw.substring(raw.length() - 1);
            } else {
                body = "0";
                verifier = raw;
            }

            String paddedBody = String.format("%04d", Integer.parseInt(body));
            String masked = paddedBody + "-" + verifier;
            gen.writeString(masked);
        } else {
            gen.writeNull();
        }
    }
}
