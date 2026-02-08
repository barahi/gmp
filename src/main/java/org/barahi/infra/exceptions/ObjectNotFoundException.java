package org.barahi.infra.exceptions;


public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(Class<?> clazz, Object id) {
        super(clazz.getSimpleName() + " with id " + id + " not found");
    }
}
