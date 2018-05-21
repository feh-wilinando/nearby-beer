package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class JsonReader {


    private final ObjectMapper mapper;

    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public JsonNode readFile(String file){
        FileSystemResource resource = new FileSystemResource(file);
        return read(resource);
    }

    public JsonNode readResource(Resource resource){
        return read(resource);
    }

    private JsonNode read(Resource resource) {
        try {
            return mapper.readTree(resource.getFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
