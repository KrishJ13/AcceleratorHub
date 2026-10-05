package com.acceleratorhub.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acceleratorhub.application.ReservationApplicationService;
import com.acceleratorhub.application.ReservationCreationResult;

@RestController // Spring-managed class that handles web requests and where returned values should be written into HTTP responses
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    
    private final ReservationApplicationService service;

    public ReservationController(ReservationApplicationService service) {
        this.service = service; // Spring injects ReservationApplicationService
    }


    /*
    With this structure, we keep the controller logic abstracted and simple. The controller does not directly know about:
    - Inventory scanning
    - Allocation creation
    - Workload Startup delay
    - Reservation Transitions
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // On Successful return 201 Created HTTP status
    public CreateReservationResponse create(@RequestBody CreateReservationRequest request) { //@RequestBody tells Spring to Deserialise (HTTP -> Java Object) the HTTP into the CreateReservationRequest body
        ReservationCreationResult result = service.create(request.acceleratorClass(), request.quantity());
        
        return new CreateReservationResponse(result.reservationId(), result.reservationState(), result.acceleratorId(), result.workloadId(), result.workloadState());

    }


}
