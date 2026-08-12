import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import "../styles/bookings.css";

function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await api.get("/api/v1/bookings");

        setBookings(response.data.data || []);
      } catch (error) {
        console.error("Failed to fetch bookings:", error);

        if (error.response?.status === 401) {
          setError(
            "Your session has expired. Please login again."
          );
        } else {
          setError("Unable to load your bookings.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

  const formatDate = (dateTime) => {
    return new Date(dateTime).toLocaleDateString(
      "en-IN",
      {
        day: "numeric",
        month: "short",
        year: "numeric",
      }
    );
  };

  const formatTime = (dateTime) => {
    return new Date(dateTime).toLocaleTimeString(
      "en-IN",
      {
        hour: "numeric",
        minute: "2-digit",
      }
    );
  };

  if (loading) {
    return (
      <main className="page">
        <div className="loading">
          Loading your bookings...
        </div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="page">
        <div className="page-heading">
          <span className="section-eyebrow">
            TICKETRUSH
          </span>

          <h1>My Bookings</h1>

          <div className="auth-error">
            {error}
          </div>

          <Link
            to="/login"
            className="primary-button"
          >
            Login again
          </Link>
        </div>
      </main>
    );
  }

  return (
    <main className="page bookings-page">
      <div className="page-heading">
        <span className="section-eyebrow">
          YOUR TICKETS
        </span>

        <h1>My Bookings</h1>

        <p>
          View your movie tickets and booking history.
        </p>
      </div>

      {bookings.length === 0 ? (
        <section className="empty-bookings">
          <div className="empty-bookings-icon">
            🎟️
          </div>

          <h2>No bookings yet</h2>

          <p>
            Your next movie experience is waiting.
          </p>

          <Link
            to="/movies"
            className="primary-button"
          >
            Explore Movies
          </Link>
        </section>
      ) : (
        <section className="bookings-list">
          {bookings.map((booking) => (
            <article
              className="booking-card"
              key={booking.bookingId}
            >
              <div className="booking-card-main">
                <div className="booking-movie">
                  <div className="booking-poster">
                    <span>
                      {booking.movie.charAt(0)}
                    </span>
                  </div>

                  <div>
                    <span className="section-eyebrow">
                      MOVIE
                    </span>

                    <h2>{booking.movie}</h2>

                    <p>
                      {booking.theatre}
                    </p>

                    <p>
                      {booking.screen}
                    </p>
                  </div>
                </div>

                <div
                  className={`booking-status ${booking.status.toLowerCase()}`}
                >
                  {booking.status}
                </div>
              </div>

              <div className="booking-card-details">
                <div className="booking-info">
                  <span>SHOWTIME</span>

                  <strong>
                    {formatDate(
                      booking.startTime
                    )}
                  </strong>

                  <strong>
                    {formatTime(
                      booking.startTime
                    )}
                  </strong>
                </div>

                <div className="booking-info">
                  <span>SEATS</span>

                  <strong>
                    {booking.seats.join(", ")}
                  </strong>
                </div>

                <div className="booking-info">
                  <span>BOOKING ID</span>

                  <strong>
                    #{booking.bookingId}
                  </strong>
                </div>

                <div className="booking-info booking-price">
                  <span>TOTAL</span>

                  <strong>
                    ₹
                    {Number(
                      booking.totalAmount
                    ).toFixed(2)}
                  </strong>
                </div>
              </div>
            </article>
          ))}
        </section>
      )}
    </main>
  );
}

export default MyBookings;