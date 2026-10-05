package com.acceleratorhub.web;

import com.acceleratorhub.domain.AcceleratorClass;

/*
We introduce a DTO (Data Transfer Object) here - who's job is to just carry data across the HTTP and Application boundary
*/
public record CreateReservationRequest(AcceleratorClass acceleratorClass, int quantity) {
    /*
    Excluding validation, Spring by nature automatically handles Deserialisation
    */
}
