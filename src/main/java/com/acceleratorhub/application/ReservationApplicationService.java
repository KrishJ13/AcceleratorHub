package com.acceleratorhub.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.acceleratorhub.domain.AcceleratorClass;
import com.acceleratorhub.domain.Allocation;
import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationAllocator;
import com.acceleratorhub.domain.ReservationRequest;
import com.acceleratorhub.domain.Workload;
import com.acceleratorhub.execution.WorkloadLauncher;

/*
@Service is a specialised form of the @Component annotation, used to mark a class as a service layer. 
We want this class to be a singleton service managed by Spring

- The service layer should orchestrate (basically just call in the right place) the different business/domain building blocks
*/
@Service 
public class ReservationApplicationService {

    private static final String DEV_TENTANT_ID = "research-a";

    private final ReservationAllocator allocator;
    private final WorkloadLauncher launcher;

    public ReservationApplicationService(ReservationAllocator allocator, WorkloadLauncher launcher) {
        this.allocator = allocator;
        this.launcher = launcher;
    }

    public ReservationCreationResult create(AcceleratorClass acceleratorClass, int quantity) {
        /* For V0, assinging random UIDs is fine. There is no ID abstraction yet */
        String requestId = "req-" + UUID.randomUUID();
        String reservationId = "rsv-" + UUID.randomUUID();
        String allocationId = "alloc-" + UUID.randomUUID();
        String workloadId = "workload-" + UUID.randomUUID();

        // Build the reservation request
        ReservationRequest request = new ReservationRequest(requestId, DEV_TENTANT_ID, acceleratorClass, quantity);

        // Build the reservation itself
        Reservation reservation = new Reservation(reservationId, DEV_TENTANT_ID, request.id());

        // Build the allocation
        Allocation allocation = allocator.allocate(request, reservation, allocationId);

        // Launch the workload
        Workload workload = launcher.launch(reservation, workloadId);   

        // Return the result
        return new ReservationCreationResult(reservation.id(), reservation.state(), allocation.acceleratorId(), workload.id(), workload.state());
        


    }
    
}
