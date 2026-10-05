package com.acceleratorhub.web;

import com.acceleratorhub.domain.ReservationState;
import com.acceleratorhub.domain.WorkloadState;

public record CreateReservationResponse(
    String reservationId, 
    ReservationState reservationState,
    String acceleratorId,
    String workloadId,
    WorkloadState workloadState
) {
    
}
