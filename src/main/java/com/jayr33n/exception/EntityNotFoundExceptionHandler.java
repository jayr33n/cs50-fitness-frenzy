package com.jayr33n.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Produces
@Singleton
@Requires(classes = {EntityNotFoundException.class, ExceptionHandler.class})
public class EntityNotFoundExceptionHandler implements
        ExceptionHandler<EntityNotFoundException, HttpResponse<?>> {
    private final ErrorResponseProcessor<?> processor;


    @Override
    public HttpResponse<?> handle(HttpRequest request, EntityNotFoundException exception) {
        return processor.processResponse(ErrorContext.builder(request)
                .cause(exception)
                .errorMessage(exception.getMessage())
                .build(), HttpResponse.badRequest());
    }
}
