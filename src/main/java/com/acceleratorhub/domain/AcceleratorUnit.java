package com.acceleratorhub.domain;

public record AcceleratorUnit(String id, AcceleratorClass acceleratorClass) {
    public AcceleratorUnit {
        // Reject constructing a unit without an Id
        if (id.isBlank() || id == null) {
            throw new IllegalArgumentException("AcceleratorUnit id must not be blank or null");
        }
        // Reject constructing a unit without specifying a valid class of the unit
        if (acceleratorClass == null) {
            throw new IllegalArgumentException("AcceleratorUnit acceleratorClass must have a value");
        }
    }
}