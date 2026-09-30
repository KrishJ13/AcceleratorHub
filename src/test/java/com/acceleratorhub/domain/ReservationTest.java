package com.acceleratorhub.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ReservationTest {
    @Test
    void requestedReservationCanBeAllocated() {
        // Make a new reservation
        Reservation reservation = new Reservation("rsv-001", "research-a", "req-001");
        // Allocate it - Changes its state
        reservation.allocate();
        assertEquals(reservation.state(), ReservationState.ALLOCATED);
    }

    @Test
    void requestedReservationCannotBecomeActiveDirectly() {
        // Make a new reservation
        Reservation reservation = new Reservation("rsv-002", "research-a", "req-002");
        
        // To compare the expected exception thrown, we need to use a lamba function to give JUnit the control of the method, instead of running it immediately
        assertThrows(IllegalStateException.class, () -> reservation.activate());

    }
}
