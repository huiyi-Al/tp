package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogMessage;

public class JsonAdaptedLogEntryTest {

    private static final String VALID_TIMESTAMP = "2026-03-22T14:05:31";
    private static final String INVALID_TIMESTAMP = "22-03-2026 14:05:31";
    private static final String VALID_MESSAGE = "Observed leakage below sink cabinet.";
    private static final String INVALID_MESSAGE = "   ";

    @Test
    public void toModelType_validLogEntryDetails_returnsLogEntry() throws Exception {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(VALID_TIMESTAMP, VALID_MESSAGE);
        LogEntry expectedLogEntry = new LogEntry(LocalDateTime.parse(VALID_TIMESTAMP), new LogMessage(VALID_MESSAGE));
        assertEquals(expectedLogEntry, logEntry.toModelType());
    }

    @Test
    public void toModelType_validLogEntryDetailsWithWhitespace_returnsTrimmedLogEntry() throws Exception {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(" " + VALID_TIMESTAMP + " ", " " + VALID_MESSAGE + " ");
        LogEntry expectedLogEntry = new LogEntry(LocalDateTime.parse(VALID_TIMESTAMP), new LogMessage(VALID_MESSAGE));
        assertEquals(expectedLogEntry, logEntry.toModelType());
    }

    @Test
    public void toModelType_invalidTimestamp_throwsIllegalValueException() {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(INVALID_TIMESTAMP, VALID_MESSAGE);
        assertThrows(IllegalValueException.class, JsonAdaptedLogEntry.MESSAGE_INVALID_TIMESTAMP, logEntry::toModelType);
    }

    @Test
    public void toModelType_nullTimestamp_throwsIllegalValueException() {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(null, VALID_MESSAGE);
        String expectedMessage = String.format(JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT, "timestamp");
        assertThrows(IllegalValueException.class, expectedMessage, logEntry::toModelType);
    }

    @Test
    public void toModelType_invalidMessage_throwsIllegalValueException() {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(VALID_TIMESTAMP, INVALID_MESSAGE);
        assertThrows(IllegalValueException.class, LogMessage.MESSAGE_CONSTRAINTS, logEntry::toModelType);
    }

    @Test
    public void toModelType_nullMessage_throwsIllegalValueException() {
        JsonAdaptedLogEntry logEntry = new JsonAdaptedLogEntry(VALID_TIMESTAMP, null);
        String expectedMessage = String.format(JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT, "message");
        assertThrows(IllegalValueException.class, expectedMessage, logEntry::toModelType);
    }
}
