package com.acceleratorhub;

import com.acceleratorhub.domain.*;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcceleratorHubApplication {
	/*
	Updated functionality of main:
	1. Build the inventory of units and no initial allocations
	2. Build the allocator, pointed at the created inventory
	3. Make a request
	4. Make a reservation for that request
	5. Call allocator.allocate() which returns the allocation or Null
	6. Print & Observe
	*/

	public static void main(String[] args) {

		// // Make the existing inventory of 4 logical Accelerator Units
		// List<AcceleratorUnit> units = List.of(
		// 	new AcceleratorUnit("accel-001", AcceleratorClass.A100),
		// 	new AcceleratorUnit("accel-002", AcceleratorClass.A100),
		// 	new AcceleratorUnit("accel-003", AcceleratorClass.L40S),
		// 	new AcceleratorUnit("accel-004", AcceleratorClass.L40S)
		// );

		// InMemoryCapacityInventory inventory = new InMemoryCapacityInventory(units);

		// // Build the allocator
		// ReservationAllocator allocator = new ReservationAllocator(inventory);

		// // Create a new ReservationRequest
		// ReservationRequest request = new ReservationRequest("req-001", "research-a", AcceleratorClass.A100, 1);

		// // Create the Reservation
		// Reservation reservation = new Reservation("rsv-001", "research-a", request.id());
		
		// // Make a new allocation. Allocator handles this internally
		// Allocation allocation = allocator.allocate(request, reservation, "alloc-001");

		// System.out.println("--- ALLOCATION 1 ---");
		// System.out.println("Allocation created: " + allocation);
		// System.out.println("Reservation state: " + reservation.state()); 
		// System.out.println("---------------------------");


		// // Testing making 2 more allocations of the same accelerator type. First one should get the next free accelerator, and third one should get no allocation
		// ReservationRequest request_2 = new ReservationRequest("req-002", "research-b", AcceleratorClass.A100, 1);
		// Reservation reservation_2 = new Reservation("rsv-002", "research-b", request_2.id());
		// Allocation allocation_2 = allocator.allocate(request_2, reservation_2, "alloc-002");
		// System.out.println("--- ALLOCATION 2 ---");
		// System.out.println("Allocation created: " + allocation_2);
		// System.out.println("Reservation state: " + reservation_2.state());
		// System.out.println("---------------------------");



		/*
		SpringApplication.run(...) tells Spring Boot to:
			- Start the application environment
			- Create the Spring IOC Container
			- Discover/Configure Beans
			- Start the embedded web application 
		*/
		SpringApplication.run(AcceleratorHubApplication.class, args);
	}

}
