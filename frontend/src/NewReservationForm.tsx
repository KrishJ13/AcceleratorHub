/*
The purpose of NewReservationForm is to collect the user choice, make a POST reservation, display the result (or error)
and notably notify the parent on success
*/

import { useState, type FormEvent } from "react";
import type { AcceleratorClass , ApiError, CreateReservationRequest, CreateReservationResponse} from "./types";


type NewReservationFormProps = {
    onCreated: () => void
}

export function NewReservationForm({onCreated}: NewReservationFormProps) {
    const [acceleratorClass, setAcceleratorClass] = useState<AcceleratorClass>('A100')
    const [isSubmitting, setIsSubmitting] = useState(false) // Compared to isLoading in read components
    const [error, setError] = useState<string | null>(null)
    const [successMessage, setSuccessMessage] = useState<string | null>(null)

    // The backend already tells use why a response 
    // The following asynchronous function takes in parameter a built-in browswer response and returns a Promise<string>
    async function readErrorMessage(response: Response): Promise<string>{
        try {
            const body = await response.json() as ApiError
            return `${body.code}: ${body.message}`
        }
        catch {
            return `Reservation request failed: ${response.status}`
        }
    }

    async function handleSubmit(event: React.FormEvent){
        event.preventDefault() // Prevent default updated the whole document navigation - only update the current UI

        // Begin the submission state
        setIsSubmitting(true)
        setError(null)
        setSuccessMessage(null)

        const request: CreateReservationRequest = {acceleratorClass, quantity: 1}

        // Make the POST request which needs additional information compared to the GET request: HTTP method, content type and request body
        try {
            const response = await fetch('/api/v1/reservations', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(request)})


            if (!response.ok) {
                throw new Error(
                await readErrorMessage(response)
                )
            }

            const body = await response.json() as CreateReservationResponse

            setSuccessMessage(`Created ${body.reservationId}`)

            onCreated()

            } 
            catch (error) {

                setError(error instanceof Error ? error.message : 'Unable to create reservation')

            } 
            finally {
                // Not the most secure
                setIsSubmitting(false)
            }

    }
    

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Accelerator Class
                <select value={acceleratorClass} onChange={event => setAcceleratorClass(event.target.value as AcceleratorClass)}> 
                    <option value="A100">A100</option>
                    <option value="L40S">L40S</option>
                </select>
            </label>
            <p>Quantity: 1 (v0)</p>
            <button type="submit" disabled={isSubmitting}>
                {isSubmitting ? 'Creating...' : 'Create Reservation'}
            </button>
            {error && <p role="alert">{error}</p>}
            {successMessage && <p role="status">{successMessage}</p>}
        </form>
        
    )
}

