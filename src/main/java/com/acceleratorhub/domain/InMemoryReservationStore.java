package com.acceleratorhub.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

/*
It is important to note that the store keeps a reference to the obejcts. Therefore, for a reservation, if we 
later do reservation.allocate() or reservation.activate(), then it is not an independent frozen copy.
The change in the reservations state will be reflected in the InMemoryReservationStore

However, this itself is a limitation, because the store returns the actual mutable reservation objects (for now)
*/
@Component 
public class InMemoryReservationStore {
    private final Map<String, Reservation> reservations = new HashMap<>();

    public void save(Reservation reservation) {
        if (this.reservations.containsKey(reservation.id())) {
            throw new IllegalStateException("Reservation already exists: " + reservation.id());
        } 
        this.reservations.put(reservation.id(), reservation);
    }

    public Optional<Reservation> findById(String id) {
        return Optional.ofNullable(this.reservations.get(id));
    }

    public List<Reservation> findAll() {
        return List.copyOf(this.reservations.values()); // List.copyOf() protects the list itself, but not the structures inside. Later, use persistance to solve this problem
    }

}
