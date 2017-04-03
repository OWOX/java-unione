package com.owox.unione.model.deserializers;

import com.owox.unione.model.Webhook;
import com.owox.unione.utils.MapperUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;

public class WebhookDeserializer extends JsonDeserializer<Webhook> {

    private static final ObjectMapper MAPPER = MapperUtils.getMapper();

    private static final String SOURCE_EVENT_FORMAT_FIELD = "eventFormat";
    private static final String SOURCE_MAX_PARALLEL_FIELD = "maxParallel";
    private static final String SOURCE_SINGLE_EVENT_FIELD = "useSingleEvent";

    private static final String TARGET_EVENT_FORMAT_FIELD = "event_format";
    private static final String TARGET_MAX_PARALLEL_FIELD = "max_parallel";
    private static final String TARGET_SINGLE_EVENT_FIELD = "single_event";


    @Override
    public Webhook deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectNode objectNode = (ObjectNode) MAPPER.readTree(jp);

        if (objectNode.get(TARGET_EVENT_FORMAT_FIELD) == null) {

            String val = objectNode.get(SOURCE_EVENT_FORMAT_FIELD).asText();
            objectNode.put(TARGET_EVENT_FORMAT_FIELD, val);
            objectNode.remove(SOURCE_EVENT_FORMAT_FIELD);
        }

        if (objectNode.get(TARGET_MAX_PARALLEL_FIELD) == null) {

            int val = objectNode.get(SOURCE_MAX_PARALLEL_FIELD).asInt();
            objectNode.put(TARGET_MAX_PARALLEL_FIELD, val);
            objectNode.remove(SOURCE_MAX_PARALLEL_FIELD);
        }

        if (objectNode.get(TARGET_SINGLE_EVENT_FIELD) == null) {

            int val = objectNode.get(SOURCE_SINGLE_EVENT_FIELD).asInt();
            objectNode.put(TARGET_SINGLE_EVENT_FIELD, val);
            objectNode.remove(SOURCE_SINGLE_EVENT_FIELD);
        }

        return MAPPER.readValue(objectNode, Webhook.class);
    }
}
