package com.wushiyii.template.springboottemplatebase.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

public class JacksonUtils {

    private final static ObjectMapper om = new ObjectMapper();

    static {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static String toJson(Object obj) {
        if (obj == null) {
            obj = Maps.newHashMap();
        }
        try {
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String content, Class<T> valueType) {
        try {
            return om.readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String content, TypeReference typeReference) {
        try {
            return om.readValue(content, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromObject(Object obj, Class<T> valueType) {
        try {
            return om.convertValue(obj, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromObject(Object obj, TypeReference typeReference) {
        try {
            return om.convertValue(obj, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
