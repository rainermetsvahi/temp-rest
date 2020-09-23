package rm.tempserver.json;

import java.io.IOException;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;

/**
 * JSON helper class
 */
public final class Json {

    private Json() {
    }

    public static final ObjectMapper JSON = objectMapper();

    /**
     * Serializes the object
     * @param object the object
     * @return the object as bytes
     */
    @SneakyThrows
    public static byte[] serialize(Object object) {
        return JSON.writeValueAsBytes(object);
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Instant.class, new InstantSerializer());
        module.addDeserializer(Instant.class, new InstantDeserializer());
        mapper.registerModule(module);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    static class InstantSerializer extends JsonSerializer<Instant> {
        @Override
        public void serialize(Instant i, JsonGenerator g, SerializerProvider s)
                throws IOException {
            g.writeString(i.toString());
        }
    }

    static class InstantDeserializer extends JsonDeserializer<Instant> {
        @Override
        public Instant deserialize(JsonParser p, DeserializationContext c)
                throws IOException {
            return Instant.parse(p.getText());
        }
    }
}
