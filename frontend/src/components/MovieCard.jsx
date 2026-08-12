import { Link } from "react-router-dom";

function MovieCard({ movie }) {
  return (
    <Link
      to={`/movies/${movie.id}`}
      className="movie-card"
    >
      <div className="movie-poster">

        {movie.imageUrl ? (
          <img
            src={movie.imageUrl}
            alt={`${movie.title} poster`}
          />
        ) : (
          <span>
            {movie.title?.charAt(0) || "M"}
          </span>
        )}

      </div>

      <div className="movie-info">

        <h3>
          {movie.title}
        </h3>

        <div className="movie-meta">

          <span>
            {movie.genre || "Unknown"}
          </span>

          <span>•</span>

          <span>
            {movie.durationMinutes
              ? `${movie.durationMinutes} min`
              : "N/A"}
          </span>

        </div>

      </div>
    </Link>
  );
}

export default MovieCard;