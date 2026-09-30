package com.acceleratorhub.domain;

public record Allocation(String id, String reservationId, String acceleratorId) {
    public Allocation{
        //Reject empty or null allocation Id
        if (id.isBlank() || id == null){
            throw new IllegalArgumentException("Allocation id must not be blank or null");
        }

        // Reject empty or null reservationId
        if (reservationId.isBlank() || reservationId == null){
            throw new IllegalArgumentException("Allocation reservationId must not be blank or null");
        }

        // Reject empty or null acceleratorId
        if (acceleratorId.isBlank() || acceleratorId == null){
            throw new IllegalArgumentException("Allocation acceleratorId must not be blank or null");
        }

    }
    
} 