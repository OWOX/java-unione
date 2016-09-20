package com.owox.unione.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;


public class MapperUtils {

    public static ObjectMapper getMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

}
