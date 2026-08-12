import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";
import "../styles/seats.css";

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

  /*
   * Group seats by row.
   *
   * Example:
   * A1, A2, A3...
   * B1, B2, B3...
   */
  const groupedSeats = seats.reduce((groups, seat) => {
    const row = seat.seat.charAt(0);

    if (!groups[row]) {
      groups[row] = [];
    }

    groups[row].push(seat);

    return groups;
  }, {});

  /*
   * Sort seats numerically inside every row.
   */
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

  /*
   * Loading state
   */
  if (loading) {
    return (
      <main className="page seat-selection-page">
        <div className="loading">
          Loading seats...
        </div>
      </main>
    );
  }

  return (
    <main className="page seat-selection-page">

      {/* =========================================
          PAGE HEADER
      ========================================= */}

      <div className="seat-selection-header">

        <Link
          to="/movies"
          className="seat-back-link"
        >
          ← Back to movies
        </Link>

        <span className="section-eyebrow">
          SEAT SELECTION
        </span>

        <h1>Select your seats</h1>

        <p>
          Select the seats you'd like to book.
        </p>

      </div>


      {/* =========================================
          ERROR MESSAGE
      ========================================= */}

      {error && (
        <div className="auth-error booking-error">
          {error}
        </div>
      )}


      {/* =========================================
          SEAT AREA + SUMMARY
      ========================================= */}

      <div className="seat-layout">

        {/* =========================================
            SEAT MAP
        ========================================= */}

        <section className="seat-map-container">

          {/* Screen */}

          <div className="seat-screen-label">
            SCREEN
          </div>

          <div className="cinema-screen"></div>


          {/* Seats */}

          <div className="seat-map">

            {Object.entries(groupedSeats).map(
              ([row, rowSeats]) => (
                <div
                  className="seat-row"
                  key={row}
                >

                  {/* Row label */}

                  <div className="seat-row-label">
                    {row}
                  </div>


                  {/* Seats in row */}

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
                        className={`seat-button ${
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
              )
            )}

          </div>


          {/* =========================================
              LEGEND
          ========================================= */}

          <div className="seat-legend">

            <div className="seat-legend-item">

              <span className="legend-seat"></span>

              <span>
                Available
              </span>

            </div>


            <div className="seat-legend-item">

              <span className="legend-seat selected"></span>

              <span>
                Selected
              </span>

            </div>


            <div className="seat-legend-item">

              <span className="legend-seat booked"></span>

              <span>
                Booked
              </span>

            </div>

          </div>

        </section>


        {/* =========================================
            BOOKING SUMMARY
        ========================================= */}

        <aside className="seat-summary">

          <span className="section-eyebrow">
            YOUR SELECTION
          </span>


          {/* Selected seats */}

          {selectedSeats.length === 0 ? (

            <h2>
              No seats selected
            </h2>

          ) : (

            <div className="selected-seat-list">

              {selectedSeats
                .slice()
                .sort((a, b) =>
                  a.seat.localeCompare(
                    b.seat,
                    undefined,
                    {
                      numeric: true,
                    }
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


          {/* Summary */}

          <div className="seat-summary-top">

            <div>

              <div className="seat-summary-label">
                SEATS
              </div>

              <div className="seat-summary-count">
                {selectedSeats.length}
              </div>

            </div>


            <div className="seat-summary-price">
              ₹{totalAmount.toFixed(2)}
            </div>

          </div>


          {/* Confirm */}

          <button
            type="button"
            className="confirm-seats-button"
            disabled={
              selectedSeats.length === 0 ||
              booking
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