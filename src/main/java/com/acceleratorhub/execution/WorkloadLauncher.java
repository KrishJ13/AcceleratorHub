package com.acceleratorhub.execution;

import org.springframework.stereotype.Component;

import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationState;
import com.acceleratorhub.domain.Workload;

/*
For this class, we now don't need to create a separate @bean method for its configuration, because we already defined FakeWorkLoad executor as a Component

Spring sees:
- Need to construct  WorkloadLauncher
- Constructor needs FakeWorkloadExecutor
- Spring Container already has FakeWorkloadExecutor bean
- Pass it into the constructor
*/
@Component 
public class WorkloadLauncher {
    // The purpose of WorkloadLauncher is to launch one workload for an already-allocated reservation
    
    private final FakeWorkloadExecutor executor;

    public WorkloadLauncher(FakeWorkloadExecutor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("FakeWorkloadExecutor cannot be null");
        }
        this.executor = executor;
    }

    public Workload launch(Reservation reservation, String workloadId) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (workloadId == null || workloadId.isBlank()) {
            throw new IllegalArgumentException("Workload ID cannot be null or blank");
        }

        // Only ALLOCATED reservations are permitted to launch workloads
        if (reservation.state() != ReservationState.ALLOCATED) {
            throw new IllegalStateException("Only allocated reservations can launch workloads");
        }

        // Construct an initial PENDING workload
        Workload workload = new Workload(workloadId, reservation.id());

        // Simulate startup of the workload
        executor.start(workload);

        // Set the reservation status to ACTIVE only after the workload startup
        reservation.activate();

        return workload;


    }
}
