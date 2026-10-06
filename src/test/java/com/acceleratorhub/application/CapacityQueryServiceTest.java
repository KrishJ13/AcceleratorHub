package com.acceleratorhub.application;

import com.acceleratorhub.domain.AcceleratorClass;
import com.acceleratorhub.domain.AcceleratorUnit;
import com.acceleratorhub.domain.InMemoryCapacityInventory;
import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationAllocator;
import com.acceleratorhub.domain.ReservationRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
For this test, we do not care about HTTP or JSON. The purpose of this test is to prove that
if InMemoryCapacityInventory holds a specific state, the query service translates it into the 
correct list of CapacityView records
*/
class CapacityQueryServiceTest {

    @Test
    void shouldProjectAllocatedAndFreeUnitsCorrectly() {
        // Set up the authoritative domain state
        List<AcceleratorUnit> units = List.of(
                new AcceleratorUnit("accel-001", AcceleratorClass.A100),
                new AcceleratorUnit("accel-002", AcceleratorClass.A100)
        );
        InMemoryCapacityInventory inventory = new InMemoryCapacityInventory(units);
        ReservationAllocator allocator = new ReservationAllocator(inventory);
        ReservationRequest request = new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 1);
        Reservation reservation = new Reservation("rsv-001", "research-a", "req-001");
        allocator.allocate(request, reservation, "alloc-001");

        // Pass the real inventory into our service
        CapacityQueryService queryService = new CapacityQueryService(inventory);

        // Request the capacity projection
        List<CapacityView> result = queryService.getCapacity();

        // We verify the read model matches reality
        assertThat(result).hasSize(2);

        // Extract the specific views to check their derived state
        CapacityView accel1 = result.stream()
                .filter(v -> v.id().equals("accel-001"))
                .findFirst().orElseThrow();
        
        CapacityView accel2 = result.stream()
                .filter(v -> v.id().equals("accel-002"))
                .findFirst().orElseThrow();

        // accel-001 should be allocated, accel-002 should be free
        assertThat(accel1.allocated()).isTrue();
        assertThat(accel2.allocated()).isFalse();
        
        // Ensure standard fields mapped correctly
        assertThat(accel1.acceleratorClass()).isEqualTo(AcceleratorClass.A100);
    }
}