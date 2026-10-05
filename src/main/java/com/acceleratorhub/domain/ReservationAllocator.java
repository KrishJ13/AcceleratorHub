package com.acceleratorhub.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;

/* 
The purpose of the ReservationAllocator is to take a valid reservation, choose free compatible capacity, record ownership and move the reservation to allocated
*/
@Component 
public class ReservationAllocator {

    private final InMemoryCapacityInventory inventory;

    public ReservationAllocator(InMemoryCapacityInventory inventory) {
        this.inventory = inventory;
    }

    /* An Allocation is never made outside of this function. In other words, an allocation is born in the allocate() method */
    public Allocation allocate(ReservationRequest request, Reservation reservation, String allocationId) {

        // Make sure the request actually belongs to the reservation
        if (!request.id().equals(reservation.requestId())) {
            throw new IllegalArgumentException("Request does not belong to the reservation");
        } 

        if (request.quantity() != 1) {
            throw new IllegalArgumentException("V0 supports quantity=1 only");
        }

        // Find the first free compatible unit
        Optional<AcceleratorUnit> found = inventory.findFirstFree(request.acceleratorClass());

        if (found.isEmpty()) {
            throw new NoCapacityException(request.acceleratorClass());
            }
            AcceleratorUnit freeUnit = found.get(); // Pull the actual Accelerator Unit out of Optional

        // Create the ownership relationship
        Allocation allocation = new Allocation(allocationId, reservation.id(), freeUnit.id());

        // Record ownership BEFORE transitioning state
        inventory.addAllocation(allocation);

        // Transition the reservation to ALLOCATED
        reservation.allocate();

        return allocation;


    }
    
}
