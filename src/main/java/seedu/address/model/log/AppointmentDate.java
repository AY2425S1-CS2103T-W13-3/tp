package seedu.address.model.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents an appointment date in the log.
 */
public class AppointmentDate {
    public static final String MESSAGE_CONSTRAINTS = "Invalid date format! Please use 'dd MMM yyyy'.";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final LocalDate date;

    /**
     * Constructs an {@code AppointmentDate} with the specified {@code LocalDate}.
     *
     * @param date A valid LocalDate.
     */
    public AppointmentDate(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Constructs an {@code AppointmentDate} by parsing a date string.
     *
     * @param dateString A valid date string in the format of "dd MMM yyyy".
     */
    public AppointmentDate(String dateString) {
        try {
            this.date = LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS, e);
        }
    }

    /**
     * Returns the appointment date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the appointment date as a formatted string in 'dd MMM yyyy'.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Returns true if both appointment dates are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentDate)) {
            return false;
        }

        AppointmentDate otherDate = (AppointmentDate) other;
        return date.equals(otherDate.date);
    }

    /**
     * Returns the hashcode of the appointment date.
     */
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}

