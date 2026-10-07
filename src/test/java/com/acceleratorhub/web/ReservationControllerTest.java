package com.acceleratorhub.web;

import com.acceleratorhub.application.ReservationApplicationService;
import com.acceleratorhub.application.ReservationQueryService;
import com.acceleratorhub.application.ReservationView;
import com.acceleratorhub.domain.ReservationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationQueryService queryService;

    @MockitoBean
    private ReservationApplicationService reservationApplicationService;

    // ----- GET /api/v1/reservations -----

    @Test
    void getAll_whenEmpty_returns200AndEmptyWrapper() throws Exception {
        given(queryService.getAll()).willReturn(List.of());

        mockMvc.perform(get("/api/v1/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservations").isArray())
                .andExpect(jsonPath("$.reservations", hasSize(0)));
    }

    @Test
    void getAll_whenReservationsExist_returns200AndListWrapper() throws Exception {
        ReservationView view1 = new ReservationView("rsv-001", "req-001", "research-a", ReservationState.ACTIVE);
        ReservationView view2 = new ReservationView("rsv-002", "req-002", "research-a", ReservationState.ACTIVE);

        given(queryService.getAll()).willReturn(List.of(view1, view2));

        mockMvc.perform(get("/api/v1/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservations", hasSize(2)))
                .andExpect(jsonPath("$.reservations[*].reservationId", containsInAnyOrder("rsv-001", "rsv-002")))
                .andExpect(jsonPath("$.reservations[0].tenantId").value("research-a"));
    }

    // ----- POST /api/v1/reservations (validation) -----

    @Test
    void rejectsZeroQuantity() throws Exception {
        mockMvc.perform(
                post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "acceleratorClass": "A100",
                              "quantity": 0
                            }
                            """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
    }

    @Test
    void rejectsUnknownAcceleratorClass() throws Exception {
        mockMvc.perform(
                post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "acceleratorClass": "RTX5090",
                              "quantity": 1
                            }
                            """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
    }
}