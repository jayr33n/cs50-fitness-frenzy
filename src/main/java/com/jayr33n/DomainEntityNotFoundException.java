package com.jayr33n;

public class DomainEntityNotFoundException extends RuntimeException {
    public DomainEntityNotFoundException(Long id, Class<?> type) {
        super("entity of type: " + type.getSimpleName() + " with id: " + id + " was not found");
    }
}
