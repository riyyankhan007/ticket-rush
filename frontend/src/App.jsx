import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
import "./App.css";

import Movies from "./pages/Movies";
import MovieDetails from "./pages/MovieDetails";
import SeatSelection from "./pages/SeatSelection";
import BookingSuccess from "./pages/BookingSuccess";
import Login from "./pages/Login";

function Home() {
  return (
    <main className="home">
      <section className="hero">
        <div className="hero-content">
          <span className="eyebrow">YOUR NEXT MOVIE NIGHT</span>

          <h1>
            Movies.
            <br />
            Moments.
            <br />
            <span>Memories.</span>
          </h1>

          <p>
            Discover movies, find the perfect show, pick your seats, and book
            your experience in seconds.
          </p>

          <Link to="/movies" className="primary-button">
            Explore Movies
          </Link>
        </div>
      </section>

      <section className="section">
        <div className="section-header">
          <div>
            <span className="section-eyebrow">DISCOVER</span>
            <h2>What's playing</h2>
          </div>

          <Link to="/movies" className="text-link">
            View all →
          </Link>
        </div>

        <div className="movie-grid">
          <MovieCard
            title="Interstellar"
            genre="Sci-Fi"
            duration="169 min"
          />

          <MovieCard
            title="Inception"
            genre="Sci-Fi"
            duration="148 min"
          />

          <MovieCard
            title="Coming Soon"
            genre="Experience"
            duration="Coming soon"
          />
        </div>
      </section>
    </main>
  );
}

function MovieCard({ title, genre, duration }) {
  return (
    <Link to="/movies" className="movie-card">
      <div className="movie-poster">
        <span>{title.charAt(0)}</span>
      </div>

      <div className="movie-info">
        <h3>{title}</h3>

        <div className="movie-meta">
          <span>{genre}</span>
          <span>•</span>
          <span>{duration}</span>
        </div>
      </div>
    </Link>
  );
}

function Register() {
  return (
    <main className="page">
      <div className="page-heading">
        <span className="section-eyebrow">TICKETRUSH</span>
        <h1>Create account</h1>
        <p>Registration comes next.</p>
      </div>
    </main>
  );
}

function Theatres() {
  return (
    <main className="page">
      <div className="page-heading">
        <span className="section-eyebrow">LOCATIONS</span>
        <h1>Theatres</h1>
        <p>Find a theatre near you.</p>
      </div>
    </main>
  );
}

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <nav className="navbar">
          <Link to="/" className="brand">
            TICKET<span>RUSH</span>
          </Link>

          <div className="nav-links">
            <Link to="/">Home</Link>
            <Link to="/movies">Movies</Link>
            <Link to="/theatres">Theatres</Link>
          </div>

          <div className="nav-actions">
            <Link to="/login" className="login-link">
              Login
            </Link>

            <Link to="/register" className="signup-button">
              Sign up
            </Link>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />

          <Route path="/movies" element={<Movies />} />

          <Route
            path="/movies/:movieId"
            element={<MovieDetails />}
          />

          <Route
            path="/shows/:showId/seats"
            element={<SeatSelection />}
          />

          <Route
            path="/booking/success"
            element={<BookingSuccess />}
          />

          <Route path="/login" element={<Login />} />

          <Route path="/register" element={<Register />} />

          <Route path="/theatres" element={<Theatres />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;