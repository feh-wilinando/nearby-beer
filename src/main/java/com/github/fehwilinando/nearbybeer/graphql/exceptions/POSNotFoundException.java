package com.github.fehwilinando.nearbybeer.graphql.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POSNotFoundException extends RuntimeException implements GraphQLError {


    private Map<String, Object> extensions = new HashMap<>();


    public POSNotFoundException(String message, Integer id ) {
        super(message);
        extensions.put("invalid-POS-id", id);
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
