package com.acceleratorhub.web;

import com.acceleratorhub.domain.ReservationState;

public record ReservationResponse(String reservationId, String requestId, String tenantId, ReservationState state) {

    
}
