package com.acceleratorhub.execution;

import java.time.Duration;

import com.acceleratorhub.domain.Workload;

public class FakeWorkloadExecutor {
    /* 
    The purpose of FakeWorkloadExecutor is to simulate an execution substrate taking a workload from PENDING to RUNNING,
    while simulating some start-up delay
    */

    private final Duration startupDelay;

    public FakeWorkloadExecutor(Duration startupDelay) {
        // Reject null starupDelay
        if (startupDelay == null) {
            throw new IllegalArgumentException("FakeWorkloadExecutor startupDelay must not be null");
        }
        if (startupDelay.isNegative()) {
            throw new IllegalArgumentException("FakeWorkloadExecutor startupDelay must be greater than or equal to zero");
        }
        this.startupDelay = startupDelay;
    }

    public void start(Workload workload) {
        try {
            Thread.sleep(startupDelay.toMillis());
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt(); // Restore the thread's interrupted status

            throw new IllegalStateException("Fake workload startup was interrupted", exception);
        }

        // Only after sleep completes, start the workload
        workload.start();
    }


    

    
}
