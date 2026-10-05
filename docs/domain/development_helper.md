## The Big Picture
- The Web Layer (web/): Talks to the internet. It takes raw JSON and turns it into simple Java records (DTOs).
- The Application Layer (application/): The manager. It doesn't do the heavy lifting, but it orchestrates the steps: "Create IDs, tell the Domain to allocate, tell the Executor to launch, and pack up the result."
- The Domain Layer (domain/): Your core logic. This is the ReservationAllocator you already built. It knows nothing about HTTP or JSON; it only enforces business rules.
- The Infrastructure Layer (execution/): Your fake executors and in-memory databases.


## Tracing One Request
- Suppose the client sends:
    - JSON containing request for "A100" x1 accelerator unit
- Spring deserialises HTTP JSON into CreateReservationRequest
- The Controller (ReservationController on the Web Layer) calls the Service (ReservationApplicationService on the Application layer)
- The Service (ReservationApplicationService) orchestrates the Domain-Level methods to create
    - A Reservation Request and the Reservation itself
    - Spring uses the Allocator (ReservationAllocator) Bean to allocate the reservation to an Accelerator. (Spring handles dependency injection of the Inventory into the Allocator )
    - Allocator checks if any free units exists and allocates an available accelerator unit to the reservation
    - Once reservation is successfully allocated, Launcher creates and starts up a Workload (Fake Executor)
- Service.create() returns ReservationCreationResult (In the application layer) to confirm the following actions above. 
- CreateReservationResponse acts as DTO (Data Transfer Object) intermediate layer for now
- Spring handles serialising (TO HTTP JSON) CreateReservationResponse and sending the response to the user