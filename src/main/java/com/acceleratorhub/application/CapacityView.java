package com.acceleratorhub.application;

import com.acceleratorhub.domain.AcceleratorClass;

/*
An immutable read-representation when user wants to check capacity
*/
public record CapacityView(String id, AcceleratorClass acceleratorClass,  boolean allocated) {
} 