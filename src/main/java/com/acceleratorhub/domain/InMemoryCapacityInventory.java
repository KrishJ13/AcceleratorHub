package com.acceleratorhub.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCapacityInventory {
    // Track the units
    private final List<AcceleratorUnit> units; 
    // Track the allocations
    private final List<Allocation> allocations;

    public InMemoryCapacityInventory(List<AcceleratorUnit> units) {
        this.units = List.copyOf(units);
        this.allocations = new ArrayList<>(); // Initialise allocations as a dynamic list
    }

    // Define a function to check if an AcceleratorUnit is currently allocated
    private boolean isAllocated(AcceleratorUnit unit) {
        for (Allocation alloc : allocations) {
            if (alloc.acceleratorId().equals(unit.id())) {
                return true;
            }
        }
        return false;
        
    }

    // Optional<AcceleratorUnit> here because the function may also return Null if no free accelerators
    public Optional<AcceleratorUnit> findFirstFree(AcceleratorClass acceleratorClass) {
        for (AcceleratorUnit unit : this.units) {
            // Skip wrong class
            if (!unit.acceleratorClass().equals(acceleratorClass)) {
                continue;
            }
            // Skip already-allocated units
            if (isAllocated(unit)) {
                continue;
            }
            // Right class AND free → first match wins
            return Optional.of(unit);
        }
        // Loop finished with no match → no capacity
        return Optional.empty();
    }

    public void addAllocation(Allocation allocation) {
        allocations.add(allocation);
    }
}
