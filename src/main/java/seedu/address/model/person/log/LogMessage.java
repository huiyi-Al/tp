package seedu.address.model.person.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the message content for a log entry.
 * Guarantees: immutable; is valid as declared in {@link #isValidLogMessage(String)}
 */
public class LogMessage {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 1000;
    public static final String MESSAGE_CONSTRAINTS =
            "Log messages should be between 1 and 1000 characters (measured in Unicode code points).";

    public final String value;

    /**
     * Constructs a {@code LogMessage}.
     *
     * @param message A valid log message.
     */
    public LogMessage(String message) {
        requireNonNull(message);
        checkArgument(isValidLogMessage(message), MESSAGE_CONSTRAINTS);
        value = message;
    }

    /**
     * Returns true if a given string is a valid log message.
     * Length is measured in Unicode code points (not UTF-16 code units).
     */
    public static boolean isValidLogMessage(String test) {
        requireNonNull(test);
        int codePointLength = test.codePointCount(0, test.length());
        return codePointLength >= MIN_LENGTH && codePointLength <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogMessage)) {
            return false;
        }

        LogMessage otherLogMessage = (LogMessage) other;
        return value.equals(otherLogMessage.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
