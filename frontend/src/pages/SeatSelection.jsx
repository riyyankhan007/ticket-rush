import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function SeatSelection() {
  const { showId } = useParams();
  const navigate = useNavigate();

  const [seats, setSeats] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState([]);

  const [loading, setLoading] = useState(true);
  const [booking, setBooking] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const response = await api.get(
          `/api/v1/shows/${showId}/seats`
        );

        setSeats(response.data.data || []);
      } catch (error) {
        console.error(error);
        setError("Unable to load seats.");
      } finally {
        setLoading(false);
      }
    };

    fetchSeats();
  }, [showId]);

  const toggleSeat = (seat) => {
    if (seat.status !== "AVAILABLE" || booking) {
      return;
    }

    setSelectedSeats((current) => {
      const alreadySelected = current.some(
        (selected) => selected.id === seat.id
      );

      if (alreadySelected) {
        return current.filter(
          (selected) => selected.id !== seat.id
        );
      }

      return [...current, seat];
    });
  };

  const totalAmount = selectedSeats.reduce(
    (total, seat) => total + Number(seat.price),
    0
  );

  const handleBooking = async () => {
    if (selectedSeats.length === 0 || booking) {
      return;
    }

    setError("");
    setBooking(true);

    try {
      const request = {
        showId: Number(showId),
        showSeatIds: selectedSeats.map(
          (seat) => seat.id
        ),
      };

      const response = await api.post(
        "/api/v1/bookings",
        request
      );

      const bookingData = response.data.data;

      navigate("/booking/success", {
        state: {
          booking: bookingData,
        },
      });
    } catch (error) {
      console.error("Booking failed:", error);

      if (error.response?.status === 401) {
        setError(
          "Your session has expired. Please login again."
        );
      } else if (error.response?.data?.message) {
        setError(error.response.data.message);
      } else {
        setError(
          "Unable to complete booking. Please try again."
        );
      }
    } finally {
      setBooking(false);
    }
  };

  const groupedSeats = seats.reduce((groups, seat) => {
    const row = seat.seat.charAt(0);

    if (!groups[row]) {
      groups[row] = [];
    }

    groups[row].push(seat);

    return groups;
  }, {});

  Object.values(groupedSeats).forEach((rowSeats) => {
    rowSeats.sort((a, b) => {
      const numberA = parseInt(
        a.seat.substring(1),
        10
      );

      const numberB = parseInt(
        b.seat.substring(1),
        10
      );

      return numberA - numberB;
    });
  });

  if (loading) {
    return (
      <main className="page">
        <div className="loading">
          Loading seats...
        </div>
      </main>
    );
  }

  return (
    <main className="page seat-selection-page">
      <div className="seat-page-header">
        <Link
          to="/movies"
          className="back-link"
        >
          ← Back
        </Link>

        <span className="section-eyebrow">
          SEAT SELECTION
        </span>

        <h1>Select your seats</h1>

        <p>
          Select the seats you'd like to book.
        </p>
      </div>

      {error && (
        <div className="auth-error booking-error">
          {error}
        </div>
      )}

      <div className="seat-layout">
        <section className="seat-map-container">
          <div className="screen">
            <div className="screen-light"></div>
            <span>SCREEN</span>
          </div>

          <div className="seat-map">
            {Object.entries(groupedSeats).map(
              ([row, rowSeats]) => (
                <div
                  className="seat-row"
                  key={row}
                >
                  <div className="row-label">
                    {row}
                  </div>

                  <div className="row-seats">
                    {rowSeats.map((seat) => {
                      const isSelected =
                        selectedSeats.some(
                          (selected) =>
                            selected.id === seat.id
                        );

                      const isBooked =
                        seat.status !== "AVAILABLE";

                      return (
                        <button
                          key={seat.id}
                          type="button"
                          disabled={
                            isBooked || booking
                          }
                          onClick={() =>
                            toggleSeat(seat)
                          }
                          className={`seat ${
                            isBooked
                              ? "booked"
                              : ""
                          } ${
                            isSelected
                              ? "selected"
                              : ""
                          }`}
                        >
                          {seat.seat}
                        </button>
                      );
                    })}
                  </div>
                </div>
              )
            )}
          </div>

          <div className="seat-legend">
            <div>
              <span className="legend-seat available"></span>
              Available
            </div>

            <div>
              <span className="legend-seat selected"></span>
              Selected
            </div>

            <div>
              <span className="legend-seat booked"></span>
              Booked
            </div>
          </div>
        </section>

        <aside className="selection-card">
          <span className="section-eyebrow">
            YOUR SELECTION
          </span>

          {selectedSeats.length === 0 ? (
            <h2>No seats selected</h2>
          ) : (
            <div className="selected-seat-list">
              {selectedSeats
                .slice()
                .sort((a, b) =>
                  a.seat.localeCompare(
                    b.seat,
                    undefined,
                    { numeric: true }
                  )
                )
                .map((seat) => (
                  <span
                    className="selected-seat"
                    key={seat.id}
                  >
                    {seat.seat}
                  </span>
                ))}
            </div>
          )}

          <div className="selection-divider"></div>

          <div className="selection-row">
            <span>Seats</span>
            <span>{selectedSeats.length}</span>
          </div>

          <div className="selection-total">
            <span>Total</span>

            <strong>
              ₹{totalAmount.toFixed(2)}
            </strong>
          </div>

          <button
            type="button"
            className="continue-button"
            disabled={
              selectedSeats.length === 0 || booking
            }
            onClick={handleBooking}
          >
            {booking
              ? "Booking..."
              : "Confirm Booking →"}
          </button>
        </aside>
      </div>
    </main>
  );
}

export default SeatSelection;