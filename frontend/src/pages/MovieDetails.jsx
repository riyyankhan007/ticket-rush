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

  /* =========================================
     FETCH MOVIE
  ========================================= */

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const response = await api.get(
          `/api/v1/movies/${movieId}`
        );

        setMovie(response.data.data);
      } catch (error) {
        console.error("Failed to load movie:", error);

        setError("Unable to load movie details.");
      } finally {
        setLoading(false);
      }
    };

    fetchMovie();
  }, [movieId]);

  /* =========================================
     FETCH SHOWS
  ========================================= */

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
        console.error("Failed to load shows:", error);

        setShowsError("Unable to load showtimes.");
      } finally {
        setShowsLoading(false);
      }
    };

    fetchShows();
  }, [movieId]);

  /* =========================================
     SELECT SHOW
  ========================================= */

  const handleSelectShow = (showId) => {
    navigate(`/shows/${showId}/seats`);
  };

  /* =========================================
     DATE / TIME FORMATTERS
  ========================================= */

  const formatDate = (dateTime) => {
    return new Date(dateTime).toLocaleDateString(
      "en-IN",
      {
        weekday: "short",
        day: "numeric",
        month: "short",
      }
    );
  };

  const formatTime = (dateTime) => {
    return new Date(dateTime).toLocaleTimeString(
      "en-IN",
      {
        hour: "numeric",
        minute: "2-digit",
        hour12: true,
      }
    );
  };

  /* =========================================
     LOADING
  ========================================= */

  if (loading) {
    return (
      <main className="page movie-details-page">
        <div className="loading">
          Loading movie...
        </div>
      </main>
    );
  }

  /* =========================================
     ERROR
  ========================================= */

  if (error) {
    return (
      <main className="page movie-details-page">
        <div className="error-message">
          {error}
        </div>

        <Link
          to="/movies"
          className="movie-details-back"
        >
          ← Back to movies
        </Link>
      </main>
    );
  }

  if (!movie) {
    return null;
  }

  /* =========================================
     MOVIE DETAILS
  ========================================= */

  return (
    <main className="page movie-details-page">

      <Link
        to="/movies"
        className="movie-details-back"
      >
        ← Back to movies
      </Link>

      <section className="movie-details">

        {/* ================================
            POSTER
        ================================= */}

        <div className="movie-details-poster">

          {movie.imageUrl ||
          movie.posterUrl ||
          movie.image ? (
            <img
              src={
                movie.imageUrl ||
                movie.posterUrl ||
                movie.image
              }
              alt={`${movie.title} poster`}
            />
          ) : (
            <span>
              {movie.title?.charAt(0)}
            </span>
          )}

        </div>

        {/* ================================
            MOVIE INFORMATION
        ================================= */}

        <div className="movie-details-content">

          <span className="section-eyebrow">
            MOVIE
          </span>

          <h1>{movie.title}</h1>

          <div className="movie-details-meta">

            <span>
              {movie.language || "English"}
            </span>

            <span>•</span>

            <span>
              {movie.genre || "Unknown"}
            </span>

            <span>•</span>

            <span>
              {movie.duration
                ? `${movie.duration} min`
                : "N/A"}
            </span>

          </div>

          <p className="movie-details-description">
            Experience {movie.title} on the big screen.
            Choose a showtime and book your seats.
          </p>

          {/* ================================
              SHOWTIMES
          ================================= */}

          <section className="showtimes-section">

            <span className="section-eyebrow">
              SHOWTIMES
            </span>

            <h2>Select a show</h2>

            {/* Loading */}

            {showsLoading && (
              <div className="showtime-list">
                <div className="showtime-card">
                  <span>
                    Loading showtimes...
                  </span>
                </div>
              </div>
            )}

            {/* Error */}

            {!showsLoading && showsError && (
              <div className="showtime-list">
                <div className="showtime-card">
                  <span>
                    {showsError}
                  </span>
                </div>
              </div>
            )}

            {/* Empty */}

            {!showsLoading &&
              !showsError &&
              shows.length === 0 && (
                <div className="showtime-list">
                  <div className="showtime-card">
                    <span>
                      No shows are currently available
                      for this movie.
                    </span>
                  </div>
                </div>
              )}

            {/* Shows */}

            {!showsLoading &&
              !showsError &&
              shows.length > 0 && (
                <div className="showtime-list">

                  {shows.map((show) => (

                    <div
                      key={show.id}
                      className="showtime-card"
                    >

                      <div className="showtime-date">
                        {formatDate(
                          show.startTime
                        )}
                      </div>

                      <div className="showtime-time">
                        {formatTime(
                          show.startTime
                        )}
                      </div>

                      <div className="showtime-theatre">
                        {show.screen}
                      </div>

                      <div className="showtime-price">
                        ₹{show.price}
                      </div>

                      <button
                        type="button"
                        className="select-seats-button"
                        onClick={() =>
                          handleSelectShow(
                            show.id
                          )
                        }
                      >
                        Select Seats
                      </button>

                    </div>

                  ))}

                </div>
              )}

          </section>

        </div>

      </section>

    </main>
  );
}

export default MovieDetails;