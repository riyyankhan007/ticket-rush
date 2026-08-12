import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function MovieDetails() {
  const { movieId } = useParams();
  const navigate = useNavigate();

  const [movie, setMovie] = useState(null);
  const [shows, setShows] = useState([]);

  const [loading, setLoading] = useState(true);
  const [showsLoading, setShowsLoading] = useState(true);

  const [error, setError] = useState("");
  const [showsError, setShowsError] = useState("");

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const response = await api.get(
          `/api/v1/movies/${movieId}`
        );

        setMovie(response.data.data);
      } catch (error) {
        console.error(error);
        setError("Unable to load movie details.");
      } finally {
        setLoading(false);
      }
    };

    fetchMovie();
  }, [movieId]);

  useEffect(() => {
    const fetchShows = async () => {
      try {
        setShowsLoading(true);
        setShowsError("");

        const response = await api.get(
          `/api/v1/shows/movie/${movieId}`
        );

        setShows(response.data.data || []);
      } catch (error) {
        console.error(error);
        setShowsError("Unable to load showtimes.");
      } finally {
        setShowsLoading(false);
      }
    };

    fetchShows();
  }, [movieId]);

  const handleSelectShow = (showId) => {
    navigate(`/shows/${showId}/seats`);
  };

  const formatDate = (dateTime) => {
    return new Date(dateTime).toLocaleDateString("en-IN", {
      weekday: "short",
      day: "numeric",
      month: "short",
    });
  };

  const formatTime = (dateTime) => {
    return new Date(dateTime).toLocaleTimeString("en-IN", {
      hour: "numeric",
      minute: "2-digit",
      hour12: true,
    });
  };

  if (loading) {
    return (
      <main className="page">
        <div className="loading">
          Loading movie...
        </div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="page">
        <div className="error-message">
          {error}
        </div>

        <Link to="/movies" className="back-link">
          ← Back to movies
        </Link>
      </main>
    );
  }

  if (!movie) {
    return null;
  }

  return (
    <main className="page movie-details-page">
      <Link to="/movies" className="back-link">
        ← Back to movies
      </Link>

      <section className="movie-details">
        <div className="movie-details-poster">
          <span>{movie.title.charAt(0)}</span>
        </div>

        <div className="movie-details-content">
          <span className="section-eyebrow">
            MOVIE
          </span>

          <h1>{movie.title}</h1>

          <div className="movie-details-meta">
            <span>{movie.language}</span>
            <span>•</span>
            <span>{movie.genre}</span>
            <span>•</span>
            <span>{movie.duration} min</span>
          </div>

          <p className="movie-description">
            Experience {movie.title} on the big screen.
            Choose a showtime and book your seats.
          </p>

          <div className="show-section">
            <span className="section-eyebrow">
              SHOWTIMES
            </span>

            <h2>Select a show</h2>

            {showsLoading && (
              <div className="show-placeholder">
                <p>Loading showtimes...</p>
              </div>
            )}

            {!showsLoading && showsError && (
              <div className="show-placeholder">
                <p>{showsError}</p>
              </div>
            )}

            {!showsLoading &&
              !showsError &&
              shows.length === 0 && (
                <div className="show-placeholder">
                  <p>
                    No shows are currently available for this movie.
                  </p>
                </div>
              )}

            {!showsLoading &&
              !showsError &&
              shows.length > 0 && (
                <div className="show-list">
                  {shows.map((show) => (
                    <div
                      key={show.id}
                      className="show-card"
                    >
                      <div className="show-card-info">
                        <div className="show-date">
                          {formatDate(show.startTime)}
                        </div>

                        <div className="show-time">
                          {formatTime(show.startTime)}
                        </div>

                        <div className="show-screen">
                          {show.screen}
                        </div>
                      </div>

                      <div className="show-card-price">
                        <span>₹{show.price}</span>

                        <button
                          type="button"
                          className="show-select-button"
                          onClick={() =>
                            handleSelectShow(show.id)
                          }
                        >
                          Select Seats
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
          </div>
        </div>
      </section>
    </main>
  );
}

export default MovieDetails;