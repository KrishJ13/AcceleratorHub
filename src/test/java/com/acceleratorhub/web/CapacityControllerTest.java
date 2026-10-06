package com.acceleratorhub.web;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.acceleratorhub.application.CapacityQueryService;
import com.acceleratorhub.application.CapacityView;
import com.acceleratorhub.domain.AcceleratorClass;

/*
In this test, we want to test the Web-Layer CapacityController. The goal of this test is to check
when a user calls GET api/v1/capacity, the application responds with a HTTP 200 OK.

*/

@WebMvcTest(CapacityController.class) // Tell Spring to only start up the web infrastructure, for this specific controller. No need to load database or rest of applicatio
public class CapacityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Create a mock instance of the class and inject it into Spring's application context
    private CapacityQueryService queryService;

    @Test 
    void getCapacity_Returns200AndFormattedJson() throws Exception {
        // The fake service is programmed to return a specific snapshot
        List<CapacityView> mockViews = List.of(
                new CapacityView("accel-001", AcceleratorClass.A100, true),
                new CapacityView("accel-002", AcceleratorClass.L40S, false)
        );

        // When the controller calls getCapacity(), return mockViews instead of running real logic
        when(queryService.getCapacity()).thenReturn(mockViews);

        mockMvc.perform(get("/api/v1/capacity"))
                .andExpect(status().isOk()) // Asserts HTTP 200
                
                // Assert the outer wrapper 'units' exists and is an array of size 2
                .andExpect(jsonPath("$.units").isArray())
                .andExpect(jsonPath("$.units.length()").value(2))
                
                // Assert the fields of the first element in the JSON array
                .andExpect(jsonPath("$.units[0].id").value("accel-001"))
                .andExpect(jsonPath("$.units[0].acceleratorClass").value("A100"))
                .andExpect(jsonPath("$.units[0].allocated").value(true))
                
                // Assert the fields of the second element
                .andExpect(jsonPath("$.units[1].id").value("accel-002"))
                .andExpect(jsonPath("$.units[1].acceleratorClass").value("L40S"))
                .andExpect(jsonPath("$.units[1].allocated").value(false));
    }
    
    
}
