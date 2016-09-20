package com.owox.unione.resources;

import com.owox.unione.model.Client;
import com.owox.unione.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

@AllArgsConstructor
public abstract class AbstractResource {

    private static final ObjectMapper MAPPER = MapperUtils.getMapper();

    protected Client client;

    protected String mixClientFieldsToJsonForObject(final Object obj) {

        ObjectNode clientNode = MAPPER.convertValue(client, ObjectNode.class);
        ObjectNode objectNode = MAPPER.convertValue(obj, ObjectNode.class);

        Iterator<Map.Entry<String, JsonNode>> objectFieldsIterator =  objectNode.getFields();
        while (objectFieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = objectFieldsIterator.next();
            clientNode.put(field.getKey(), field.getValue());
        }

        return clientNode.toString();
    }
}
