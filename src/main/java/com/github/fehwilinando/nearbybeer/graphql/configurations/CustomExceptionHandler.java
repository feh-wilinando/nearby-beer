package com.github.fehwilinando.nearbybeer.graphql.configurations;

import com.github.fehwilinando.nearbybeer.graphql.exceptions.ExceptionWrapper;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CustomExceptionHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {

        return errors.stream()
                    .map(error -> isServerError(error)? new ExceptionWrapper(error) : error )
                            .collect(Collectors.toList());

    }

    private boolean isServerError(GraphQLError error) {
        return error instanceof ExceptionWhileDataFetching || error instanceof Throwable;
    }

}
