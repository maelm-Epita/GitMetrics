import "../styles/globals.css"
import SearchBar from "../components/SearchBar"
import GithubLoginButton from "../components/GitHubLogInButton"

function HomePage() {
  return (
  <div className="min-h-screen flex items-center justify-center px-6 bg-gradient-to-b from-zinc-950 to-zinc-750
            animate-fade-in
            ">
    <div className="absolute top-[-100px] left-1/2 w-[400px] h-[400px] bg-purple-500/20 blur-[120px] rounded-full" />
    <div className="absolute bottom-[-100px] right-1/3 w-[300px] h-[300px] bg-blue-500/20 blur-[120px] rounded-full" />
    <div className="w-full flex flex-col max-w-3xl text-center space-y-8">
        <h1 className="text-6xl md:text-7xl font-bold tracking-tight
                    transition-all duration-300
                    hover:scale-[1.03]
                    hover:border-white/30
                    text-transparent
                    bg-clip-text
                    bg-gradient-to-r
                    from-blue-400
                    to-purple-500
                    ">
            GitMetrics
        </h1>

        <h2 className="text-xl md:text-2xl text-gray-400">
            An interactive Web App to view GitHub metrics 🚀
        </h2>

        <SearchBar />

        <div className="flex items-center gap-4">
            <div className="h-px flex-1 bg-zinc-800" />
                <span className="text-zinc-500">or</span>
            <div className="h-px flex-1 bg-zinc-800" />
        </div>

        <div className="
                    mr-auto ml-auto
                    bg-white/5
                    backdrop-blur-xl
                    border border-white/10
                    rounded-2xl
                    shadow-2xl
                    ">
        <GithubLoginButton />
        </div>
          <p className="text-sm text-gray-600">
            Try: facebook/react, vercel/next.js, spring-projects/spring-boot
          </p>
    </div>
  </div>
  )
}

export default HomePage
