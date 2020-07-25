package com.zharker.database.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RawJsonDeserializer extends JsonDeserializer<Serializable> {

    @Override
    public Serializable deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        LinkedList<Map<String, Object>> list = Lists.newLinkedList();
        LinkedHashMap<String, Object> map = Maps.newLinkedHashMap();
        JsonNode node = p.readValueAsTree();
        if (node.isArray()) {
            jsonNodeToArray(node, list);
            return list;
        } else if (node.isObject()) {
            jsonNodeToMap(node, map);
            return map;
        }
        return null;
    }

    private void jsonNodeToMap(JsonNode node, Map<String, Object> map) {
        node.fields().forEachRemaining(e -> {
            JsonNode jsonNodeValue = e.getValue();
            Object value = null;
            switch (jsonNodeValue.getNodeType()) {
                case NUMBER:
                    value = jsonNodeValue.numberValue();
                    break;
                case STRING:
                    value = jsonNodeValue.textValue();
                    break;
                case BOOLEAN:
                    value = jsonNodeValue.booleanValue();
                    break;
                case BINARY:
                    try {
                        value = jsonNodeValue.binaryValue();
                    } catch (IOException ex) {
                        ex.printStackTrace(); //todo
                    }
                    break;
                case OBJECT:
                    Map<String, Object> subMap = Maps.newHashMap();
                    value = subMap;
                    jsonNodeToMap(jsonNodeValue, subMap);
                    break;
                case ARRAY:
                    List<Map<String, Object>> subList = Lists.newArrayList();
                    value = subList;
                    jsonNodeToArray(jsonNodeValue, subList);
                    break;
                default:
                    value = null;
            }
            map.put(e.getKey(), value);
        });
    }

    private void jsonNodeToArray(JsonNode jsonNodeValue, List<Map<String, Object>> list) {
        jsonNodeValue.forEach(jsonNode -> {
            Map<String, Object> subMap = Maps.newHashMap();
            jsonNodeToMap(jsonNode, subMap);
            list.add(subMap);
        });
    }
}