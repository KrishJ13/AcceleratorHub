

    // Add TypeScript types for expected objects

    import { useEffect, useState } from "react"

    type ReservationState = 'REQUESTED' | 'ALLOCATED' | 'ACTIVE' | 'RELEASED' 

    type Reservation = {
        reservationId: string,
        requestId: string,
        tenantId: string,
        state: ReservationState
    }

    type ReservationListResponse = {
        reservations: Reservation[]
    }

    // Define the props
    type ReservationSectionProps = {
        refreshVersion: number
    }

    export function ReservationSection({refreshVersion} : ReservationSectionProps) {

        // Define the states that will change. This will be the list of reservations. isLoading and error
        const [reservations, setReservations] = useState<Reservation[]>([])
        const [isLoading, setIsLoading] = useState(true)
        const [error, setError] = useState<string | null>(null)

        useEffect(() => {
            async function loadReservations() {
                setIsLoading(true)
                setError(null)

                try {
                    const response = await fetch("/api/v1/reservations")
                    if (!response.ok) {
                        throw new Error(`Reservations request failed: ${response.status}`)
                    }
                    const body: ReservationListResponse = await response.json()
                    setReservations(body.reservations)
                }
                catch (error) {
                    setError(error instanceof Error ? error.message : "Unable to load reservations")
                }
                finally {
                    setIsLoading(false)
                }

            }
            loadReservations()
        }, [refreshVersion]) // Update to refresh when parent refreshVersion changes
    
        return (
        <section>
            <h2>New Reservation</h2>
            {isLoading && <p>Loading reservations…</p>}

            {error && <p role="alert">{error}</p>}

            {!isLoading && !error && reservations.length === 0 && (<p>No reservations yet.</p>)}
            {!isLoading && !error && reservations.length > 0 && (
            <table>
                <thead>
                <tr>
                    <th>Reservation ID</th>
                    <th>Tenant</th>
                    <th>State</th>
                </tr>
                </thead>
            
                <tbody>
                {reservations.map(reservation => (
                    <tr key={(reservation.reservationId)}>
                    <td>{(reservation.reservationId)}</td>
                    <td>{(reservation.tenantId)}</td>
                    <td>{(reservation.state)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            )}
        </section>
        )


    }
