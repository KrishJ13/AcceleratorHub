package com.acceleratorhub.web;

import com.acceleratorhub.domain.AcceleratorClass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
We introduce a DTO (Data Transfer Object) here - who's job is to just carry data across the HTTP and Application boundary
*/

// Added Jakarta Bean Validation to differentiate between invalid requests and impossible requests


public record CreateReservationRequest(@NotNull AcceleratorClass acceleratorClass, @Min(1) int quantity) {
    /*
    Excluding validation, Spring by nature automatically handles Deserialisation
    */

}
