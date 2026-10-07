package com.acceleratorhub.application;

import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationState;
import com.acceleratorhub.domain.InMemoryReservationStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationQueryServiceTest {

    private InMemoryReservationStore store;
    private ReservationQueryService queryService;

    @BeforeEach
    void setUp() {
        store = new InMemoryReservationStore();
        queryService = new ReservationQueryService(store);
    }

    @Test
    void getAll_whenEmpty_returnsEmptyList() {
        List<ReservationView> views = queryService.getAll();

        assertThat(views).isEmpty();
    }

    @Test
    void getAll_whenReservationsExist_returnsAllViews() {
        Reservation rsv1 = new Reservation("rsv-001", "research-a", "req-001");
        Reservation rsv2 = new Reservation("rsv-002", "research-a", "req-002");

        store.save(rsv1);
        store.save(rsv2);

        List<ReservationView> views = queryService.getAll();

        assertThat(views)
                .hasSize(2)
                .extracting(ReservationView::reservationId)
                .containsExactlyInAnyOrder("rsv-001", "rsv-002");

        assertThat(views)
                .extracting(ReservationView::tenantId)
                .containsOnly("research-a");
    }
}