package com.acceleratorhub.application;

import com.acceleratorhub.domain.ReservationState;

public record ReservationView(String reservationId, String requestId, String tenantId, ReservationState state) {
    
}