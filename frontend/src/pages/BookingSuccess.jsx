import { Link, useLocation } from "react-router-dom";
import "../styles/booking-success.css";

function BookingSuccess() {
  const location = useLocation();

  const booking = location.state?.booking;

  if (!booking) {
    return (
      <main className="page">
        <div className="page-heading">
          <span className="section-eyebrow">
            TICKETRUSH
          </span>

          <h1>Booking not found</h1>

          <p>
            We couldn't find the booking details.
          </p>

          <Link
            to="/movies"
            className="primary-button"
          >
            Browse Movies
          </Link>
        </div>
      </main>
    );
  }

  return (
    <main className="page booking-success-page">
      <div className="booking-success-card">
        <div className="success-icon">
          ✓
        </div>

        <span className="section-eyebrow">
          BOOKING CONFIRMED
        </span>

        <h1>Your tickets are booked.</h1>

        <p className="success-message">
          Your seats have been successfully reserved.
        </p>

        <div className="booking-details">
          <div className="booking-detail-row">
            <span>Booking ID</span>
            <strong>
              #{booking.bookingId}
            </strong>
          </div>

          <div className="booking-detail-row">
            <span>Status</span>
            <strong>
              {booking.status}
            </strong>
          </div>

          <div className="booking-detail-row">
            <span>Seats</span>
            <strong>
              {booking.seats.join(", ")}
            </strong>
          </div>

          <div className="booking-detail-row total">
            <span>Total paid</span>
            <strong>
              ₹{Number(
                booking.totalAmount
              ).toFixed(2)}
            </strong>
          </div>
        </div>

        <div className="booking-success-actions">
          <Link
            to="/movies"
            className="primary-button"
          >
            Book another movie
          </Link>

          <Link
            to="/"
            className="secondary-button"
          >
            Back to home
          </Link>
        </div>
      </div>
    </main>
  );
}

export default BookingSuccess;