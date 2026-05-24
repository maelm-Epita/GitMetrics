import { Link } from 'react-router-dom';

export default function NotFoundHeader() {
  return (
  <div className="flex items-center justify-center gap-3 flex-col">
      <h1
        className="
        text-6xl
        font-extrabold
        bg-clip-text
        bg-gradient-to-r
        from-blue-400
        to-purple-500
        "
      >
        404
      </h1>
      <h2
        className="
        text-2xl
        font-semibold
        "
      >
        We could not find the repository you are looking for.
      </h2>
      <Link to="/">
      <p className="
          text-sm 
          text-gray-600 
          transition-all duration-300
          hover:text-gray-400 
          hover:scale-[1.03]
          ">
        Take me back
      </p>
      </Link>
</div>
  )
}
