package com.acceleratorhub.web;

import com.acceleratorhub.domain.AcceleratorClass;

public record CapacityUnitResponse(String id, AcceleratorClass acceleratorClass, boolean allocated) {

}