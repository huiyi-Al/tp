package seedu.address.model.person.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LogEntryTest {
    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2026, 3, 22, 14, 5, 31);
    private static final LocalDateTime OTHER_TIMESTAMP = LocalDateTime.of(2026, 3, 21, 10, 18, 2);
    private static final LogMessage MESSAGE = new LogMessage("Observed leakage below sink cabinet.");
    private static final LogMessage OTHER_MESSAGE = new LogMessage("Client requested follow-up call next week.");

    @Test
    public void constructor_nullTimestamp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogEntry(null, MESSAGE));
    }

    @Test
    public void constructor_nullMessage_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogEntry(TIMESTAMP, null));
    }

    @Test
    public void getters() {
        LogEntry logEntry = new LogEntry(TIMESTAMP, MESSAGE);
        assertEquals(TIMESTAMP, logEntry.getTimestamp());
        assertEquals(MESSAGE, logEntry.getMessage());
    }

    @Test
    public void equals() {
        LogEntry logEntry = new LogEntry(TIMESTAMP, MESSAGE);
        LogEntry sameLogEntry = new LogEntry(TIMESTAMP, new LogMessage(MESSAGE.value));

        // same values -> returns true
        assertTrue(logEntry.equals(sameLogEntry));
        assertEquals(logEntry.hashCode(), sameLogEntry.hashCode());

        // same object -> returns true
        assertTrue(logEntry.equals(logEntry));

        // null -> returns false
        assertFalse(logEntry.equals(null));

        // different type -> returns false
        assertFalse(logEntry.equals(5));

        // different timestamp -> returns false
        assertFalse(logEntry.equals(new LogEntry(OTHER_TIMESTAMP, MESSAGE)));

        // different message -> returns false
        assertFalse(logEntry.equals(new LogEntry(TIMESTAMP, OTHER_MESSAGE)));
    }
}
