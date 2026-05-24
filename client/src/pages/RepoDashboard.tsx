import "../styles/globals.css"
import { useParams } from "react-router-dom"
import { useState, useEffect } from "react"

function RepoDashboard() {
  const {owner, repo} = useParams()
  const [repoMetrics, setRepoMetrics] = useState(undefined);

  async function getMetrics(owner: string, repo: string) {
    const res = await fetch(`http://localhost:8080/api/repos/${owner}/${repo}`)
    if (!res.ok) {
      console.warn("Github API error:", res.status);
      setRepoMetrics(undefined);
      return;
    }
    const data = await res.json();

    setRepoMetrics(data);
  }

  useEffect(() => {
    if (!owner || !repo) return;
    async function fetchMetrics(){
      await getMetrics(owner, repo)
    }
    fetchMetrics();
  }, [owner, repo])

  console.log(repoMetrics);

  return (
    <div>
      { repoMetrics === undefined ? (
      <h1 className="">
        Loading...
      </h1> ) : (
      <h1 className=""> 
        {owner}/{repo} 
      </h1> )}
    </div>
  )
}

export default RepoDashboard;
