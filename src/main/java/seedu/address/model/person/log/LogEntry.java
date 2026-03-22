package seedu.address.model.person.log;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an immutable timestamped log entry.
 */
public class LogEntry {
    private final LocalDateTime timestamp;
    private final LogMessage message;

    /**
     * Constructs a {@code LogEntry}.
     *
     * @param timestamp Timestamp of the log entry.
     * @param message Message of the log entry.
     */
    public LogEntry(LocalDateTime timestamp, LogMessage message) {
        requireAllNonNull(timestamp, message);
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LogMessage getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogEntry)) {
            return false;
        }

        LogEntry otherLogEntry = (LogEntry) other;
        return timestamp.equals(otherLogEntry.timestamp)
                && message.equals(otherLogEntry.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("timestamp", timestamp)
                .add("message", message)
                .toString();
    }
}
