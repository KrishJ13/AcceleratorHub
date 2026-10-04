package com.acceleratorhub.domain;

public class NoCapacityException extends RuntimeException {
    public NoCapacityException(AcceleratorClass acceleratorClass) {
        super("No free capacity for " + acceleratorClass);
    }
}