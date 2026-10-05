package com.acceleratorhub.config;

import java.time.Duration;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acceleratorhub.domain.AcceleratorClass;
import com.acceleratorhub.domain.AcceleratorUnit;
import com.acceleratorhub.domain.InMemoryCapacityInventory;
import com.acceleratorhub.execution.FakeWorkloadExecutor;

@Configuration // Tells Spring that this class contains application object-construction configuration
public class ApplicationConfig {
    // Define a Bean for the FakeWorkloadExecutor configuration values

    @Bean // This method can be thought of as an "object factory". Spring calls it and keeps the returned object in its container
    public FakeWorkloadExecutor fakeWorkloadExecutor() {
        // Return a 'factory-set' fakeworkload executor Singleton for Spring to use
        return new FakeWorkloadExecutor(Duration.ofMillis(250));
    }

    /*
    When the application starts, construct these capacity units, then construct a singleton inventory containing them, then give the inventory to Spring for it to manage
    */
    @Bean
    public InMemoryCapacityInventory capacityInventory() {
        List<AcceleratorUnit> units = List.of(
            new AcceleratorUnit("accel-001", AcceleratorClass.A100),
			new AcceleratorUnit("accel-002", AcceleratorClass.A100),
			new AcceleratorUnit("accel-003", AcceleratorClass.L40S),
			new AcceleratorUnit("accel-004", AcceleratorClass.L40S) 
        );

        return new InMemoryCapacityInventory(units);
    }
}