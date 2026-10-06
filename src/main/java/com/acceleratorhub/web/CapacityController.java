package com.acceleratorhub.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acceleratorhub.application.CapacityQueryService;

@RestController
@RequestMapping("/api/v1/capacity")
public class CapacityController {
    public final CapacityQueryService service;

    public CapacityController(CapacityQueryService service) {
        this.service = service;
    }

    @GetMapping
    public CapacityResponse getCapacity() {
        List<CapacityUnitResponse> unitResponses = this.service.getCapacity().stream().map(view -> new CapacityUnitResponse(view.id(), view.acceleratorClass(), view.allocated())).toList();
        return new CapacityResponse(unitResponses);
    }
}
