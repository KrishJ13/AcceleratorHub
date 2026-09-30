package com.acceleratorhub.domain;

public record ReservationRequest(String id, String tenantId, AcceleratorClass acceleratorClass, int quantity) {
    public ReservationRequest {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ReservationRequest id must not be blank or null");
        }

        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("ReservationRequest tenantId must not be blank or null");
        }

        if (acceleratorClass == null) {
            throw new IllegalArgumentException("ReservationRequest acceleratorClass must have a value");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("The requested quantity must be greater than 0");
        }
    }
}
