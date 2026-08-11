ALTER TABLE show_seats
ADD COLUMN booking_id BIGINT;

ALTER TABLE show_seats
ADD CONSTRAINT fk_showseat_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id);

CREATE INDEX idx_show_seats_booking_id
    ON show_seats(booking_id);