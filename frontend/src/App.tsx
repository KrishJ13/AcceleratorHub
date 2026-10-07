import { useState, useEffect } from 'react'
import './App.css'

// Describe the shape of what the backend returns

type AcceleratorClass = 'A100' | 'L40S'

type CapacityUnit = {
  id: string,
  acceleratorClass: AcceleratorClass,
  allocated: boolean
}

type CapacityResponse = {
  units: CapacityUnit[] // Capacity Response is a list of Capacity Units
}


function App() {
  // Use react hook to tell react that when capacity changes, it needs to render again
  const [capacity, setCapacity] = useState<CapacityUnit[]>([])

  // We need states for loading, success and failure
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)


  async function loadCapacity() { // Tells JS to perform asynchronous work
    setIsLoading(true)
    setError(null)

    try {
      const response = await fetch("api/v1/capacity") // await means suspend the function until the promise settles, without blocking the browser

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

  // Now use useEffect to run once
  useEffect(() => void loadCapacity(), [])

  return (
    <main>
      <header>
        <h1>AcceleratorHub</h1>
        <p>AI Infrastructure Control Plane</p>
      </header>

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

      <section>
        <h2>Reservations</h2>
        <p>Reservations will appear here.</p>
      </section>

      <section>
        <h2>New Reservation</h2>
        <p>Reservation form will appear here.</p>
      </section>
    </main>
  )
}

export default App