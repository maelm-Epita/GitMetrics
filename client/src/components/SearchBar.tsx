import { useState, useEffect, useRef } from "react"
import { Link } from 'react-router-dom';

export default function SearchBar() {
  const [query, setQuery] = useState("")
  const [results, setResults] = useState<any[]>([])
  const [isFocused, setIsFocused] = useState(false)

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
    const data = await res.json();
    console.log(data);

    const items = data.items || [];

    setResults(items);
  }

  return (
    <div className="relative w-full">
      <input
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setTimeout(() => setIsFocused(false), 150)}
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
      {isFocused && results.length > 0 && (
        <div className="
          z-1 absolute top-full mt-2 w-full
          bg-black/80 backdrop-blur-xl
          border border-white/10
          rounded-xl
          overflow-hidden
        ">
          {results.map((repo) => (
            <Link 
              to={`/${repo.full_name}`}
              key={repo.id}>
              <div
                className="px-4 py-3 hover:bg-white/10 cursor-pointer"
              >
                <p className="text-white text-sm font-medium">
                  {repo.full_name}
                </p>
                <p className="text-white/40 text-xs">
                  ⭐ {repo.stargazers_count}
                </p>
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  )
}
