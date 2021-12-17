package com.jayr33n.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, Class<?> type) {
        super("Entity of type {" + type.getSimpleName() + "} with id {" + id + "} was not found");
    }
}
