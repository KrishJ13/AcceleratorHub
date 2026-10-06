package com.acceleratorhub.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.acceleratorhub.domain.InMemoryReservationStore;
import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationNotFoundException;

/* Using this class avoids the ReservationController communicating directly with the store */
@Service
public class ReservationQueryService {
    private final InMemoryReservationStore store;

    public ReservationQueryService(InMemoryReservationStore store) {
        this.store = store;
    }

    public ReservationView getById(String id) {
        Reservation reservation = this.store.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        return new ReservationView(reservation.id(), reservation.requestId(), reservation.tenantId(), reservation.state());
      
    }
    
}
