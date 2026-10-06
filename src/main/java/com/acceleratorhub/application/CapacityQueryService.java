package com.acceleratorhub.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.acceleratorhub.domain.AcceleratorUnit;
import com.acceleratorhub.domain.InMemoryCapacityInventory;

@Service
public class CapacityQueryService {
    // This will have a inventory class. Spring already makes the bean for this

    // The purpose of this class is to take the current inventory and produce capacity views

    private final InMemoryCapacityInventory inventory;

    public CapacityQueryService(InMemoryCapacityInventory inventory) {
        this.inventory = inventory;
    }

    public List<CapacityView> getCapacity() {

        List<CapacityView> result = new ArrayList<>();

        for (AcceleratorUnit unit : inventory.units()) {
            // Check if the unit is allocated
            boolean allocated = inventory.isAllocated(unit);

            result.add(new CapacityView(unit.id(), unit.acceleratorClass(), allocated));
        }
        return List.copyOf(result); // We want to return a Live SNAPSHOT. NOT a Live mutable list
    }


}