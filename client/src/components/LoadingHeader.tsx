import { useState, useEffect } from "react"

export default function LoadingHeader() {
    const [dots, setDots] = useState("");

  useEffect(() => {
    const interval = setInterval(() => {
      setDots((prev) => {
        if (prev.length >= 3) return "";
        return prev + ".";
      });
    }, 500);

    return () => clearInterval(interval);
  }, []);
  return (
  <div className="flex items-center justify-center gap-3 flex-col">
      <span className="ml-1 flex
        text-4xl
        font-extrabold
        text-gray-400
        transition-all duration-300
        hover:scale-[1.03]
        animate-fade-in
        ">
        <span className="animate-bounce [animation-delay:-0.3s]">.</span>
        <span className="animate-bounce [animation-delay:-0.15s]">.</span>
        <span className="animate-bounce">.</span>
      </span>
      <h1
        className="
        text-xl
        font-semibold
        text-gray-400
        transition-all duration-300
        hover:scale-[1.03]
        animate-fade-in
        "
      >
        Loading
      </h1>
</div>
  )
}
