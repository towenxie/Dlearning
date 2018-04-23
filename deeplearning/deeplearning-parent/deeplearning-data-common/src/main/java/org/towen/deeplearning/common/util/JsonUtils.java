/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public static final String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        OutputStream out = new ByteArrayOutputStream();
        try {
            JsonGenerator generator = objectMapper.getFactory().createGenerator(out, JsonEncoding.UTF8);
            objectMapper.writeValue(generator, obj);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert object " + obj + "to json", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close OutputStream", e);
            }
        }
    }

    public static final String toJson(Object obj, Set<String> ignoreFieldNames) {
        if (obj == null) {
            return null;
        }

        OutputStream out = new ByteArrayOutputStream();

        try {
            JsonGenerator generator = objectMapper.getFactory().createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            Class<?> classType = obj.getClass();
            Map<String, Field> fieldMap = getDeclaredFields(classType, null);

            for (Field field : fieldMap.values()) {
                String fieldName = field.getName();

                if (fieldName.equals("serialVersionUID")) {
                    continue;
                }

                if (CollectionUtils.isEmpty(ignoreFieldNames) || !ignoreFieldNames.contains(fieldName)) {
                    String methodName =
                            "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
                    Method method = classType.getMethod(methodName);

                    if (method == null) {
                        methodName = fieldName;
                        method = classType.getMethod(methodName);
                    }

                    if (method != null) {
                        Object fieldValue = method.invoke(obj);
                        generator.writeObjectField(fieldName, fieldValue);
                    }
                }
            }

            generator.writeEndObject();
            generator.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object " + obj + "to json", e);
        }

        return out.toString();
    }

    public static final <T> T toObject(String json, Class<T> targetType) {
        if (StringUtils.isBlank(json) || targetType == null) {
            return null;
        }

        try {
            JsonParser jp =
                    objectMapper.getFactory()
                            .createParser(new String(json.getBytes(), JsonEncoding.UTF8.getJavaName()));
            return objectMapper.readValue(jp, targetType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert json to object", e);
        }
    }

    @SuppressWarnings("deprecation")
    public static final <T> T toObjectCollection(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        if (StringUtils.isBlank(json) || collectionClass == null || elementClasses == null) {
            return null;
        }

        try {
            JavaType resultType =
                    objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

            return objectMapper.readValue(json, resultType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert json to object", e);
        }
    }

    private static Map<String, Field> getDeclaredFields(Class<?> classType, Map<String, Field> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            fieldMap = new HashMap<String, Field>();
        }

        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldTypeName = field.getType().getName();
            String genericTypeName = field.getGenericType().getTypeName();
            String fieldKey = fieldTypeName + "#" + genericTypeName + "#" + fieldName;

            if (!fieldMap.containsKey(fieldKey)) {
                fieldMap.put(fieldKey, field);
            }
        }

        Class<?> superClass = classType.getSuperclass();
        if (superClass != null) {
            getDeclaredFields(superClass, fieldMap);
        } else {
            return fieldMap;
        }

        return fieldMap;
    }
}
