package com.acceleratorhub.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



import java.util.List;

public class ReservationAllocatorTest {
    
    private InMemoryCapacityInventory inventory;
    private ReservationAllocator allocator;

    @BeforeEach 
    void setUp() {
        // Reset the inventory and allocator before every unit test
        List<AcceleratorUnit> units = List.of(
            new AcceleratorUnit("accel-001", AcceleratorClass.A100),
            new AcceleratorUnit("accel-002", AcceleratorClass.A100),
            new AcceleratorUnit("accel-003", AcceleratorClass.L40S),
            new AcceleratorUnit("accel-004", AcceleratorClass.L40S)
        );

        inventory = new InMemoryCapacityInventory(units);
        allocator = new ReservationAllocator(inventory);
    }

    @Test
    void firstCompatibleUnitWins() {
        ReservationRequest request = new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 1);
        Reservation reservation = new Reservation("rsv-001", "research-a", "req-001");

        Allocation allocation = allocator.allocate(request, reservation, "alloc-001");

        assertEquals("accel-001", allocation.acceleratorId());
        assertEquals(ReservationState.ALLOCATED, reservation.state()); 
    }

    @Test
    void skipsAlreadyAllocatedUnit() {
        // Consume the first A100
        ReservationRequest request1 = new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 1);
        Reservation reservation1 = new Reservation("rsv-001", "research-a", "req-001");
        allocator.allocate(request1, reservation1, "alloc-001");

        // Request a second A100
        ReservationRequest request2 = new ReservationRequest("req-002", "research-a", AcceleratorClass.A100, 1);
        Reservation reservation2 = new Reservation("rsv-002", "research-a", "req-002");
        Allocation allocation2 = allocator.allocate(request2, reservation2, "alloc-002");

        // Assert it skipped accel-001 and took accel-002
        assertEquals("accel-002", allocation2.acceleratorId());
    }

    @Test
    void noCapacityThrowsExceptionAndLeavesStateUnchanged() {
        // Exhaust all A100 capacity
        allocator.allocate(
                new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 1),
                new Reservation("rsv-001", "research-a", "req-001"), 
                "alloc-001"
        );
        allocator.allocate(
                new ReservationRequest("req-002", "research-a", AcceleratorClass.A100, 1),
                new Reservation("rsv-002", "research-a", "req-002"), 
                "alloc-002"
        );

        //   Attempt a third A100 request
        ReservationRequest request3 = new ReservationRequest("req-003", "research-a", AcceleratorClass.A100, 1);
        Reservation reservation3 = new Reservation("rsv-003", "research-a", "req-003");

        // Verify the custom exception is thrown using the lambda function 
        assertThrows(NoCapacityException.class, () -> {allocator.allocate(request3, reservation3, "alloc-003");});

        // Verify that the reservation state of the third request was not improperly changed
        assertEquals(ReservationState.REQUESTED, reservation3.state());
    }
        
}
