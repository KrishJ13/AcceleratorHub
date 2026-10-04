package com.acceleratorhub.execution;

import com.acceleratorhub.domain.Reservation;
import com.acceleratorhub.domain.ReservationState;
import com.acceleratorhub.domain.Workload;
import com.acceleratorhub.domain.WorkloadState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkloadLauncherTest {

    private FakeWorkloadExecutor executor;
    private WorkloadLauncher launcher;

    @BeforeEach
    void setUp() {
        // Duration.ZERO prevents test suite slowdown
        executor = new FakeWorkloadExecutor(Duration.ZERO);
        launcher = new WorkloadLauncher(executor);
    }

    @Test
    void launchingAllocatedReservationStartsWorkloadAndActivatesReservation() {
        Reservation reservation = new Reservation("rsv-001", "research-a", "req-001");
        reservation.allocate();

        Workload workload = launcher.launch(reservation, "workload-001");

        assertEquals(WorkloadState.RUNNING, workload.state());
        assertEquals(ReservationState.ACTIVE, reservation.state());
        assertEquals("rsv-001", workload.reservationId());
    }

    @Test
    void cannotLaunchRequestedReservation() {
        Reservation reservation = new Reservation("rsv-001", "research-a", "req-001");

        assertThrows(IllegalStateException.class, () -> {
            launcher.launch(reservation, "workload-001");
        });

        assertEquals(ReservationState.REQUESTED, reservation.state());
    }
}