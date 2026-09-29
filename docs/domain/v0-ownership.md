# V0 Ownership Model

## Reservation Request
- A Reservation Request expresses a tenant's desire for a specific quantity and type of accelerator capacity. 
- It captures the user's intent prior to any decision or scheduling by the control plane. 
- Because it represents an unfulfilled demand, a request can be queued, accepted, or rejected without claiming or locking any underlying hardware resources.
- A reservation request itself is not proof that a tenant owns an accelerator. A request only represents intent or demand. It has not yet been accepted, scheduled, or assigned capacity by the control plane. Until an allocation binds capacity to an accepted reservation, no physical or logical unit is actually claimed.

## Reservation
- A Reservation represents AcceleratorHub's authoritative control-plane lifecycle for an accepted request. 
- It maintains the status and state transitions of the request as it moves through the system. 
- While it establishes the business context for holding capacity, it does not directly manage individual hardware bindings.

## Allocation
- An Allocation is the explicit mapping between an active Reservation and a specific physical or logical accelerator unit. I
- It functions as the authoritative record of hardware ownership in the system. By isolating ownership to the Allocation object, AcceleratorHub can support multi-unit requests and track resource assignments independently of high-level request metadata.
- Reservation tracks the high-level business lifecycle of a request, while Allocation tracks the specific mapping between that reservation and concrete accelerator units. Keeping them separate allows for a 1-to-many relationship without cluttering the reservation lifecycle state.

## Workload
- A Workload represents the actual execution artifact (such as a Kubernetes Job or Deployment) managed on behalf of a Reservation. 
- It decouples the operational runtime state from the underlying capacity ownership. This separation ensures that execution failures or external API disruptions do not inadvertently corrupt or erase hardware resource allocations.
- A crashed workload might need reconciliation or a restart; until AcceleratorHub explicitly cancels or releases the underlying reservation/allocation, the accelerator remains authoritative capacity held for that reservation.

---

### Example Scenario

**Setup:**
Tenant `research-a` requests 1 × A100.  
Available Units: `accel-001` (A100), `accel-002` (A100).

1. **Request:**  
   `tenant`: "research-a", `acceleratorClass`: "A100", `quantity`: 1, `workload`: "training-job"

2. **Reservation:**  
   `id`: "rsv-123", `tenant`: "research-a", `state`: "ALLOCATED", `requested`: 1 × A100

3. **Allocation (after selecting accel-001):**  
   `id`: "alloc-001", `reservationId`: "rsv-123", `acceleratorUnitId`: "accel-001"

4. **Workload:**  
   `id`: "wkld-001", `reservationId`: "rsv-123", `type`: "Job", `state`: "RUNNING"

5. **Ownership Proof:**  
   The **Allocation** object (`alloc-001`) explicitly proves that `accel-001` is owned because it creates the direct binding between `reservationId: rsv-123` and `acceleratorUnitId: accel-001`.

---

### Key Structural Rules

* **Multi-unit Request Rule:**  
  If one reservation asks for two A100s, you would expect **1 Reservation** and **2 Allocations** (each allocation pointing to a distinct A100 unit, e.g., `alloc-1 → accel-001` and `alloc-2 → accel-002`).
