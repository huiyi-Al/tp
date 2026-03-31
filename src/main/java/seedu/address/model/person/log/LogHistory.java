package seedu.address.model.person.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;

/**
 * Represents an immutable, newest-first history of log entries for one person.
 */
public class LogHistory {
    private static final Comparator<LogEntry> NEWEST_FIRST_COMPARATOR =
            Comparator.comparing(LogEntry::getTimestamp).reversed();

    private final List<LogEntry> entries = new ArrayList<>();

    /**
     * Creates an empty {@code LogHistory}.
     */
    public LogHistory() {}

    /**
     * Creates a {@code LogHistory} from the provided entries.
     * Entries are normalized into newest-first display order.
     */
    public LogHistory(List<LogEntry> entries) {
        requireAllNonNull(entries);
        this.entries.addAll(entries);
        this.entries.sort(NEWEST_FIRST_COMPARATOR);
    }

    /**
     * Returns true if the history has no log entries.
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Returns the number of log entries in this history.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Returns the latest log entry, if present.
     */
    public Optional<LogEntry> getLatest() {
        if (entries.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(entries.get(0));
    }

    /**
     * Returns an immutable view of entries in newest-first order.
     */
    public List<LogEntry> asUnmodifiableList() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Returns a new {@code LogHistory} with {@code entry} inserted as the newest log.
     */
    public LogHistory add(LogEntry entry) {
        requireNonNull(entry);
        List<LogEntry> updatedEntries = new ArrayList<>(entries);
        updatedEntries.add(entry);
        return new LogHistory(updatedEntries);
    }

    /**
     * Returns a new {@code LogHistory} with the log at {@code logIndex} removed.
     */
    public LogHistory delete(Index logIndex) {
        requireNonNull(logIndex);
        int zeroBasedIndex = logIndex.getZeroBased();
        if (zeroBasedIndex >= entries.size()) {
            throw new IndexOutOfBoundsException();
        }

        List<LogEntry> updatedEntries = new ArrayList<>(entries);
        updatedEntries.remove(zeroBasedIndex);
        return new LogHistory(updatedEntries);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LogHistory)) {
            return false;
        }

        LogHistory otherLogHistory = (LogHistory) other;
        return entries.equals(otherLogHistory.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries);
    }
}
