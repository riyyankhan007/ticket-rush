import { Link } from "react-router-dom";

function MovieCard({ movie }) {
    return (
        <Link to={`/movies/${movie.id}`} className="movie-card">

            <div className="movie-poster">
                <span>{movie.title.charAt(0)}</span>
            </div>

            <div className="movie-info">

                <h3>{movie.title}</h3>

                <div className="movie-meta">
                    <span>{movie.language}</span>
                    <span>•</span>
                    <span>{movie.genre}</span>
                    <span>•</span>
                    <span>{movie.duration} min</span>
                </div>

            </div>

        </Link>
    );
}

export default MovieCard;