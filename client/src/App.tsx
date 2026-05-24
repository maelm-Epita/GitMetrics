import { BrowserRouter, Routes, Route } from "react-router-dom"

import HomePage from "./pages/HomePage"
import RepoDashboard from "./pages/RepoDashboard"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/:owner/:repo" element={<RepoDashboard />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
