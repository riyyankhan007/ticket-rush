import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";

function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;

    setForm((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError("");

    if (!form.email.trim() || !form.password) {
      setError("Please enter your email and password.");
      return;
    }

    setLoading(true);

    try {
      const response = await api.post("/auth/login", {
        email: form.email.trim(),
        password: form.password,
      });

      const { accessToken } = response.data;

      if (!accessToken) {
        throw new Error("Authentication token was not returned.");
      }

      localStorage.setItem("accessToken", accessToken);

      navigate("/movies");
    } catch (error) {
      console.error("Login failed:", error);

      if (error.response?.status === 401) {
        setError("Invalid email or password.");
      } else if (error.response?.data?.message) {
        setError(error.response.data.message);
      } else {
        setError("Unable to login. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-card">
        <span className="section-eyebrow">
          WELCOME BACK
        </span>

        <h1>Login</h1>

        <p className="auth-subtitle">
          Sign in to continue your TicketRush experience.
        </p>

        {error && (
          <div className="auth-error">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">
              Email
            </label>

            <input
              id="email"
              name="email"
              type="email"
              placeholder="you@example.com"
              value={form.email}
              onChange={handleChange}
              autoComplete="email"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">
              Password
            </label>

            <input
              id="password"
              name="password"
              type="password"
              placeholder="Enter your password"
              value={form.password}
              onChange={handleChange}
              autoComplete="current-password"
              required
            />
          </div>

          <button
            type="submit"
            className="auth-button"
            disabled={loading}
          >
            {loading ? "Signing in..." : "Login"}
          </button>
        </form>

        <p className="auth-footer">
          Don't have an account?{" "}
          <Link to="/register">
            Sign up
          </Link>
        </p>
      </div>
    </main>
  );
}

export default Login;