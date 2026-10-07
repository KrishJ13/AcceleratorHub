package com.acceleratorhub.application;

import java.util.ArrayList;
import java.util.List;
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

    private ReservationView toView(Reservation reservation) {
        return new ReservationView(reservation.id(), reservation.requestId(), reservation.tenantId(), reservation.state());
    }

    public ReservationView getById(String id) {
        Reservation reservation = this.store.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        return toView(reservation);
      
    }

    public List<ReservationView> getAll() {
        List<ReservationView> views = new ArrayList<>();

        for (Reservation reservation : this.store.findAll()) {
            views.add(toView(reservation));
        }

        return List.copyOf(views);

        /*
        ALTERNATIVELY
        return store.findAll().stream().map(reservation -> toView(reservation)).toList();
        */
    }


    
}
