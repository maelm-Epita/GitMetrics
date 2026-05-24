import "../styles/globals.css"
import { useParams } from "react-router-dom"
import { useState, useEffect } from "react"
import NotFoundHeader from "../components/NotFoundHeader"
import LoadingHeader from "../components/LoadingHeader"

function RepoDashboard() {
  const {owner, repo} = useParams()
  const [repoMetrics, setRepoMetrics] = useState(undefined);
  const [got404, setGot404] = useState(false);

  async function getMetrics(owner: string, repo: string) {
    const res = await fetch(`http://localhost:8080/api/repos/${owner}/${repo}`)
    if (!res.ok) {
      console.warn("Github API error:", res.status);
      if (res.status === 404)
        setGot404(true);
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
        got404 === true ? (
          <div className="flex justify-center items-center min-h-screen">
          <NotFoundHeader />
          </div>
        ) : (
          <div className="flex justify-center items-center min-h-screen">
          <LoadingHeader />
          </div>
      )) : (
        <h1 className=""> 
          {owner}/{repo} 
        </h1> )}
    </div>
  )
}

export default RepoDashboard;
