CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE theatres
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE screens
(
    id BIGSERIAL PRIMARY KEY,
    theatre_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_screen_theatre
        FOREIGN KEY(theatre_id)
        REFERENCES theatres(id)
);

CREATE TABLE seats
(
    id BIGSERIAL PRIMARY KEY,
    screen_id BIGINT NOT NULL,
    row_name VARCHAR(5) NOT NULL,
    seat_number INT NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_seat_screen
        FOREIGN KEY(screen_id)
        REFERENCES screens(id)
);

CREATE TABLE shows
(
    id BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    screen_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_show_movie
        FOREIGN KEY(movie_id)
        REFERENCES movies(id),

    CONSTRAINT fk_show_screen
        FOREIGN KEY(screen_id)
        REFERENCES screens(id)
);

CREATE TABLE show_seats
(
    id BIGSERIAL PRIMARY KEY,
    show_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_showseat_show
        FOREIGN KEY(show_id)
        REFERENCES shows(id),

    CONSTRAINT fk_showseat_seat
        FOREIGN KEY(seat_id)
        REFERENCES seats(id)
);

CREATE TABLE bookings
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    show_id BIGINT NOT NULL,
    booking_status VARCHAR(20) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_booking_user
        FOREIGN KEY(user_id)
        REFERENCES users(id),

    CONSTRAINT fk_booking_show
        FOREIGN KEY(show_id)
        REFERENCES shows(id)
);