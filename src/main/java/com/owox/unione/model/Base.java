package com.owox.unione.model;

import com.owox.unione.utils.MapperUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Base {

    protected static ObjectMapper MAPPER = MapperUtils.getMapper();


    public String toJson() {

        String json = null;
        try {
            json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Json serialisation error", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    @Override
    public String toString() {
        return toJson();
    }

}
