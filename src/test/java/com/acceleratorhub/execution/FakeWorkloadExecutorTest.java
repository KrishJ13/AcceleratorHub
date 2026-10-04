package com.acceleratorhub.execution;

import com.acceleratorhub.domain.Workload;
import com.acceleratorhub.domain.WorkloadState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FakeWorkloadExecutorTest {

    private FakeWorkloadExecutor executor;

    @BeforeEach
    void setUp() {
        executor = new FakeWorkloadExecutor(Duration.ZERO);
    }

    @Test
    void transitionsPendingWorkloadToRunning() {
        Workload workload = new Workload("workload-001", "rsv-001");
        assertEquals(WorkloadState.PENDING, workload.state());

        executor.start(workload);

        assertEquals(WorkloadState.RUNNING, workload.state());
    }

    @Test
    void throwsExceptionWhenStartingAlreadyRunningWorkload() {
        Workload workload = new Workload("workload-001", "rsv-001");
        executor.start(workload);

        assertThrows(IllegalStateException.class, () -> {
            executor.start(workload);
        });
    }

    @Test
    void rejectsNegativeDurationInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FakeWorkloadExecutor(Duration.ofSeconds(-1));
        });
    }
}