package seedu.address.model.person.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the message content for a log entry.
 * Guarantees: immutable; is valid as declared in {@link #isValidLogMessage(String)}
 */
public class LogMessage {

    public static final int MAX_LENGTH = 1000;
    public static final String VALIDATION_REGEX = "^\\S.{0,999}$";
    public static final String MESSAGE_CONSTRAINTS =
            "Log messages should start with a non-whitespace character and be between 1 and 1000 characters.";

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
     */
    public static boolean isValidLogMessage(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
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
