package com.ApiRestMiBus.exception;

public class EntityException extends RuntimeException{
    private final String entityName;

    public EntityException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
