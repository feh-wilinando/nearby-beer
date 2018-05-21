package com.github.fehwilinando.nearbybeer.graphql.exceptions;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class ExceptionWrapper implements GraphQLError {


    private final GraphQLError error;

    public ExceptionWrapper(GraphQLError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {


        boolean whileDataFetching = error instanceof ExceptionWhileDataFetching;

        if (whileDataFetching){
            ExceptionWhileDataFetching exceptionWhileDataFetching = (ExceptionWhileDataFetching) error;
            return exceptionWhileDataFetching.getException().getMessage();
        }

        return error.getMessage();

    }

    @Override
    public List<SourceLocation> getLocations() {
        return error.getLocations();
    }

    @Override
    public ErrorType getErrorType() {
        return error.getErrorType();
    }

    @Override
    public List<Object> getPath() {
        return error.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return error.toSpecification();
    }

    @Override
    public Map<String, Object> getExtensions() {
        return error.getExtensions();
    }
}
