package com.acceleratorhub.domain;

public class Reservation {
    private final String id;
    private final String tenantId;    
    private final String requestId; //These fields are final because we want them to be immutable. Changes in these should be a new request

    private ReservationState state; // Encapsulation: Let Reservation manage its own state. Not final because state can change during a Reservation's lifecycle

    public Reservation(String id, String tendantId, String requestId) {
        // Reject null or empty constructor arguements
        if (id.isBlank() || id == null) {
            throw new IllegalArgumentException("Reservation id must not be blank or null");
        }
        if (tendantId.isBlank() || tendantId == null) {
            throw new IllegalArgumentException("Reservation tenantId must not be blank or null");
        }
        if (requestId.isBlank() || requestId == null) {
            throw new IllegalArgumentException("Reservation requestId must not be blank or null");
        }

        this.id = id;
        this.tenantId = tendantId;
        this.requestId = requestId;

        // On initialisation, the state should be set to Requested
        this.state = ReservationState.REQUESTED;
    }

    // allocate() defines the transistion from REQUESTED -> ALLOCATED
    public void allocate() {
        if (this.state != ReservationState.REQUESTED) {
            throw new IllegalStateException("Reservation must be REQUESTED before allocation");
        }
        this.state = ReservationState.ALLOCATED;
    }

    // activate() defines the transition from ALLOCATED -> ACTIVE
    public void activate() {
        if (this.state != ReservationState.ALLOCATED) {
            throw new IllegalStateException("Reservation must be ALLOCATED before activating");
        }
        this.state = ReservationState.ACTIVE;
    }

    // release() defines the transition from ACTIVE -> RELEASED
    public void release() {
        if (this.state != ReservationState.ACTIVE) {
            throw new IllegalStateException("Reservation must be ACTIVE before releasing");
        }
        this.state = ReservationState.RELEASED;
    }

    // Define access methods for attributes of the class. Since no setters, the convention is to call the attribute directly
    public String id() {
        return this.id;
    }

    public ReservationState state() {
        return this.state;
    }

    public String tenantId() {
        return this.tenantId;
    }

    public String requestId() {
        return this.requestId;
    }
}


