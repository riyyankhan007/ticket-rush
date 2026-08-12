import {
  BrowserRouter,
  Link,
  Route,
  Routes,
  useNavigate,
} from "react-router-dom";

import "./App.css";

import Movies from "./pages/Movies";
import MovieDetails from "./pages/MovieDetails";
import SeatSelection from "./pages/SeatSelection";
import BookingSuccess from "./pages/BookingSuccess";
import Login from "./pages/Login";
import MyBookings from "./pages/MyBookings";
import ProtectedRoute from "./components/ProtectedRoute";
import Register from "./pages/Register";
import MovieCard from "./components/MovieCard";

import { useAuth } from "./context/AuthContext";

function Home() {
  return (
    <main className="home">
      <section className="hero">
        <div className="hero-content">
          <span className="eyebrow">
            YOUR NEXT MOVIE NIGHT
          </span>

          <h1>
            Movies.
            <br />
            Moments.
            <br />
            <span>Memories.</span>
          </h1>

          <p>
            Discover movies, find the perfect show, pick your seats,
            and book your experience in seconds.
          </p>

          <Link
            to="/movies"
            className="primary-button"
          >
            Explore Movies
          </Link>
        </div>
      </section>

      <section className="section">
        <div className="section-header">
          <div>
            <span className="section-eyebrow">
              DISCOVER
            </span>

            <h2>What's playing</h2>
          </div>

          <Link
            to="/movies"
            className="text-link"
          >
            View all →
          </Link>
        </div>

        <div className="movie-grid">

          <MovieCard
            movie={{
              id: 1,
              title: "Interstellar",
              genre: "Sci-Fi",
              durationMinutes: 169,
              imageUrl:
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            }}
          />

          <MovieCard
            movie={{
              id: 2,
              title: "Inception",
              genre: "Sci-Fi",
              durationMinutes: 148,
              imageUrl:
                "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
            }}
          />

        </div>
      </section>
    </main>
  );
}

function Navbar() {
  const { isLoggedIn, logout } = useAuth();

  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">

      <Link
        to="/"
        className="brand"
      >
        TICKET<span>RUSH</span>
      </Link>

      <div className="nav-links">

        <Link to="/">
          Home
        </Link>

        <Link to="/movies">
          Movies
        </Link>

        <Link to="/theatres">
          Theatres
        </Link>

        {isLoggedIn && (
          <Link to="/bookings">
            My Bookings
          </Link>
        )}

      </div>

      <div className="nav-actions">

        {isLoggedIn ? (
          <>
            <span className="account-label">
              My Account
            </span>

            <button
              type="button"
              className="logout-button"
              onClick={handleLogout}
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link
              to="/login"
              className="login-link"
            >
              Login
            </Link>

            <Link
              to="/register"
              className="signup-button"
            >
              Sign up
            </Link>
          </>
        )}

      </div>
    </nav>
  );
}

function Theatres() {
  return (
    <main className="page">

      <div className="page-heading">

        <span className="section-eyebrow">
          LOCATIONS
        </span>

        <h1>
          Theatres
        </h1>

        <p>
          Find a theatre near you.
        </p>

      </div>

    </main>
  );
}

function App() {
  return (
    <BrowserRouter>

      <div className="app">

        <Navbar />

        <Routes>

          <Route
            path="/"
            element={<Home />}
          />

          <Route
            path="/movies"
            element={<Movies />}
          />

          <Route
            path="/movies/:movieId"
            element={<MovieDetails />}
          />

          <Route
            path="/shows/:showId/seats"
            element={<SeatSelection />}
          />

          <Route element={<ProtectedRoute />}>

            <Route
              path="/booking/success"
              element={<BookingSuccess />}
            />

            <Route
              path="/bookings"
              element={<MyBookings />}
            />

          </Route>

          <Route
            path="/login"
            element={<Login />}
          />

          <Route
            path="/register"
            element={<Register />}
          />

          <Route
            path="/theatres"
            element={<Theatres />}
          />

        </Routes>

      </div>

    </BrowserRouter>
  );
}

export default App;