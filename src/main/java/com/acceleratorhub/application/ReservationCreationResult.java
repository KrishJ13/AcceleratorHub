package com.acceleratorhub.application;

import com.acceleratorhub.domain.ReservationState;
import com.acceleratorhub.domain.WorkloadState;

/*
Once the application service finishes orchestrating the creation, it needs to pass the success details back to the web layer
*/
public record ReservationCreationResult(
    String reservationId,
    ReservationState reservationState,
    String acceleratorId, 
    String workloadId,
    WorkloadState workloadState
) {
    
}
