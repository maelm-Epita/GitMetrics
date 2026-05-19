import { DiGithubBadge } from "react-icons/di";

export default function GithubLoginButton() {
  const handleLogin = () => {
    window.location.href =
      "http://localhost:8080/oauth2/authorization/github"
  }

  return (
  <button
  onClick={handleLogin}
  className="
  flex items-center justify-center gap-3

  px-6 py-3
  rounded-2xl

  bg-white/10
  backdrop-blur-2xl
  border border-white/10

  shadow-xl shadow-black/40

  transition-all duration-300
  hover:bg-white/15
  hover:border-white/20
  hover:shadow-black/60
  hover:scale-[1.03]

  active:scale-95
  "
  >
  <DiGithubBadge size={22} />
  <span className="text-sm sm:text-base md:text-lg font-medium">
    Sign in with GitHub
  </span>
</button>
  )
}
