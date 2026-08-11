import { useEffect, useState } from "react";
import MovieCard from "../components/MovieCard";
import api from "../api/axios";

function Movies() {

    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchMovies = async () => {

            try {

                const response =
                    await api.get("/api/v1/movies");

                setMovies(response.data.data);

            } catch (error) {

                console.error(error);

                setError(
                    "Unable to load movies. Please try again."
                );

            } finally {

                setLoading(false);
            }
        };

        fetchMovies();

    }, []);

    return (
        <main className="page movies-page">

            <div className="page-heading">

                <span className="section-eyebrow">
                    CINEMA
                </span>

                <h1>Movies</h1>

                <p>
                    Find something worth watching.
                </p>

            </div>

            {loading && (
                <div className="loading">
                    Loading movies...
                </div>
            )}

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            {!loading && !error && movies.length === 0 && (
                <div className="empty-state">
                    No movies available right now.
                </div>
            )}

            {!loading && !error && movies.length > 0 && (

                <div className="movie-grid">

                    {movies.map(movie => (
                        <MovieCard
                            key={movie.id}
                            movie={movie}
                        />
                    ))}

                </div>

            )}

        </main>
    );
}

export default Movies;