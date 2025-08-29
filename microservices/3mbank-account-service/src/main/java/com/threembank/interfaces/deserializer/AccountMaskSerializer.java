package com.threembank.interfaces.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountMaskSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        String raw = value.toString();
        String body = raw.length() > 1 ? raw.substring(0, raw.length() - 1) : "0"; //NOSONAR
        String verifier = raw.substring(raw.length() - 1);
        String paddedBody = String.format("%07d", Integer.parseInt(body));
        String masked = paddedBody + "-" + verifier;
        gen.writeString(masked);
    }
}
