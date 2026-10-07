import { useState } from 'react'
import './App.css'
import { CapacitySection } from './CapacitySection'
import { ReservationSection } from './ReservationSection'
import { NewReservationForm } from './NewReservationForm'



function App() {
  //Add refresh version to allow App (parent) to know when to refresh its children (components)
  const [refreshVersion, setRefreshVersion] = useState(0)

  // Add a callback function
  function handleReservationCreated() {
    setRefreshVersion(current => current + 1)
  }
  return (
    <main>
      <header>
        <h1>AcceleratorHub</h1>
        <p>AI Infrastructure Control Plane</p>
      </header>
        <CapacitySection refreshVersion={refreshVersion}></CapacitySection> {/*use React props to pass down Data & Functions */}
        <ReservationSection refreshVersion={refreshVersion}></ReservationSection>
        <NewReservationForm onCreated={handleReservationCreated}></NewReservationForm>
      
    </main>
  )
}

export default App