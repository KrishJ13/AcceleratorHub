import { useState, useEffect } from "react"
import type { AcceleratorClass } from "./types"

 // Define the props for Capacity Section outside of the function
type CapacitySectionProps = {
    refreshVersion: number
}
export function CapacitySection({refreshVersion}: CapacitySectionProps) {
    // Define the capacity state and the shape of what the backend returns
    type CapacityUnit = {
        id: string,
        acceleratorClass: AcceleratorClass,
        allocated: boolean
    }

    type CapacityResponse = {
        units: CapacityUnit[] // Capacity Response is a list of Capacity Units
    }

    // Use react hook to tell react that when capacity changes, it needs to render again
    const [capacity, setCapacity] = useState<CapacityUnit[]>([])

    // We need states for loading, success and failure
    const [isLoading, setIsLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    async function loadCapacity() {
        try {
            const response = await fetch("api/v1/capacity")
            // Inspect response
            if (!response.ok) {
                throw new Error(`Capacity request failed: ${response.status}`);
            }

            const body: CapacityResponse = await response.json()

            setCapacity(body.units)
        }
        catch (error) {
            setError(error instanceof Error ? error.message : 'Unable to load capacity')
        }
        finally {
            setIsLoading(false)
        }
    }

    useEffect(() => void loadCapacity(), [refreshVersion]) // Update the dependencies so that now when refreshVersion changes, the render should happen

    // Since we made this a component, we only need to return the Capacity Section js
    return (
        <section>
        <h2>Capacity</h2>
        {/* This is a way of conditional formatting */}
        {isLoading && (<p>Loading Capacity...</p>)}
        {error && (<p role="alert">{error}</p>)}
        {!isLoading && !error && (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Class</th>
                <th>Status</th>
              </tr>
            </thead>
          
            <tbody>
              {capacity.map(unit => (
                <tr key={(unit.id)}>
                  <td>{(unit.id)}</td>
                  <td>{(unit.acceleratorClass)}</td>
                  <td>
                    {unit.allocated ? 'Allocated' : 'Available'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    )

}