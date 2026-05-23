import { useState, useEffect, useRef } from "react"

export default function SearchBar() {
  const [query, setQuery] = useState("")
  const [results, setResults] = useState<any[]>([])

  useEffect(() => {
    console.log(query);
    if (query.length < 2) {
      setResults([])
      return
    }

    const timeout = setTimeout(() => { runSearch(query)}, 100);
    return () => clearTimeout(timeout);
  }, [query]);

  async function runSearch(value: string) {
    const res = await fetch(`http://localhost:8080/api/repos/search?q=${value}`)
    if (!res.ok) {
      console.warn("Github API error:", res.status);
      setResults([]);
      return;
    }
    console.log(res);
    const data = await res.json();
    const items = data.items || [];

    setResults(items);
  }

  return (
    <div className="relative w-full">
      <input
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Search a GitHub repository..."
        className="
          w-full px-5 py-3
          rounded-2xl
          bg-white/5
          border border-white/10
          backdrop-blur-xl
          text-white
          outline-none
        "
      />

      {/* dropdown */}
      {results.length > 0 && (
        <div className="
          absolute top-full mt-2 w-full
          bg-black/80 backdrop-blur-xl
          border border-white/10
          rounded-xl
          overflow-hidden
        ">
          {results.map((repo) => (
            <div
              key={repo.id}
              className="px-4 py-3 hover:bg-white/10 cursor-pointer"
            >
              <p className="text-white text-sm font-medium">
                {repo.full_name}
              </p>
              <p className="text-white/40 text-xs">
                ⭐ {repo.stargazers_count}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
