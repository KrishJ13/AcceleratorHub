package com.acceleratorhub.domain;

public class Workload {
    private final String id;
    private final String reservationId;
    
    private WorkloadState state; 

    public Workload(String id, String reservationId) {
        // Basic validation
        if (id.isBlank() || id == null) {
            throw new IllegalArgumentException("Workload id must not be blank or null");
        }
        if (reservationId.isBlank() || reservationId == null) {
            throw new IllegalArgumentException("Reservation reservationId must not be blank or null");
        }

        this.id = id;
        this.reservationId = reservationId;
        this.state = WorkloadState.PENDING;
    }

    // Workload.start() only represents the domain transition. It itself does not simulate the execution system 
    public void start() {
        if (this.state != WorkloadState.PENDING) {
            throw new IllegalStateException("Workload must be PENDING before starting");
        }
        this.state = WorkloadState.RUNNING;
    }

    // Expose attributes
    public String id() {
        return this.id;
    }

    public String reservationId() {
        return this.reservationId;
    }

    public WorkloadState state() {
        return this.state;
    }

    
}
