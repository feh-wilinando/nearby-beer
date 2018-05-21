package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class JsonReader {


    private final ObjectMapper mapper;

    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private FileSystemResource getFileSystemResource(String file) {
        return new FileSystemResource(file);
    }

    public JsonNode readFile(String file){
        FileSystemResource resource = getFileSystemResource(file);

        try {
            return mapper.readTree(resource.getFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public <T> T readFile(String file, Class<T> clazz){
        FileSystemResource resource = getFileSystemResource(file);

        try {
            return mapper.readValue(resource.getFile(), clazz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
