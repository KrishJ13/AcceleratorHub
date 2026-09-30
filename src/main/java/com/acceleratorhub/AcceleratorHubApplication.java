package com.acceleratorhub;

// Import classes
import com.acceleratorhub.domain.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcceleratorHubApplication {

	public static void main(String[] args) {

		// Create a new AcceleratorUnit
		AcceleratorUnit unit = new AcceleratorUnit("accel-001", AcceleratorClass.A100);
		// Create a new ReservationRequest
		ReservationRequest request = new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 0);
		// Allocate the unit to the request 
		Allocation allocation = new Allocation("alloc-001", "rsv-001", unit.id());
		
		System.out.println(unit);
		System.out.println(request);
		System.out.println(allocation);




		SpringApplication.run(AcceleratorHubApplication.class, args);
	}

}
