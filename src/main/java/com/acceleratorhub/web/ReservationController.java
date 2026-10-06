package com.acceleratorhub.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acceleratorhub.application.ReservationApplicationService;
import com.acceleratorhub.application.ReservationCreationResult;
import com.acceleratorhub.application.ReservationQueryService;
import com.acceleratorhub.application.ReservationView;

import jakarta.validation.Valid;

@RestController // Spring-managed class that handles web requests and where returned values should be written into HTTP responses
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    
    private final ReservationApplicationService applicationService;
    private final ReservationQueryService queryService;

    public ReservationController(ReservationApplicationService applicationService, ReservationQueryService queryService) {
        this.applicationService = applicationService; // Spring injects ReservationApplicationService
        this.queryService = queryService;
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
    // @Valid calls the Validator Engine
    public CreateReservationResponse create(@Valid @RequestBody CreateReservationRequest request) { //@RequestBody tells Spring to Deserialise (HTTP -> Java Object) the HTTP into the CreateReservationRequest body
        ReservationCreationResult result = applicationService.create(request.acceleratorClass(), request.quantity());
        
        return new CreateReservationResponse(result.reservationId(), result.reservationState(), result.acceleratorId(), result.workloadId(), result.workloadState());

    }

    @GetMapping("/{reservationId}")
    public ReservationResponse getById(@PathVariable String reservationId) {
        ReservationView result = queryService.getById(reservationId);
        return new ReservationResponse(result.reservationId(), result.requestId(), result.tenantId(), result.state());
    }


}
