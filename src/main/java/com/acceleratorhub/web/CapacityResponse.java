package com.acceleratorhub.web;

import java.util.List;

// Wrapper web DTO (Data Transfer Object). 
public record CapacityResponse(List<CapacityUnitResponse> units) {
    
}
