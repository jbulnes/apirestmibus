package com.ApiRestMiBus.exception;

public class EntityNotFoundException extends EntityException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName, String.format("%s with ID %d not found", entityName, id));
    }
}
