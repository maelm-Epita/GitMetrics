import { useEffect, useState } from "react"
import axios from "axios"

function App() {
  const [message, setMessage] = useState("Loading...")

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/hello")
      .then((response) => {
        setMessage(response.data)
      })
  }, [])

  return (
    <div className="min-h-screen bg-black text-white flex items-center justify-center">
      <h1 className="text-4xl font-bold">
        {message}
      </h1>
    </div>
  )
}

export default App
