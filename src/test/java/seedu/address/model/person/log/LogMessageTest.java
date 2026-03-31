package seedu.address.model.person.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LogMessageTest {

    private static final String VALID_MESSAGE = "Observed leakage below sink cabinet.";
    private static final String MIN_LENGTH_MESSAGE = "a";
    private static final String MAX_LENGTH_MESSAGE = "a".repeat(LogMessage.MAX_LENGTH);
    private static final String TOO_LONG_MESSAGE = "a".repeat(LogMessage.MAX_LENGTH + 1);
    private static final String MAX_LENGTH_EMOJI_MESSAGE = "\uD83D\uDE00".repeat(LogMessage.MAX_LENGTH);
    private static final String TOO_LONG_EMOJI_MESSAGE = "\uD83D\uDE00".repeat(LogMessage.MAX_LENGTH + 1);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogMessage(null));
    }

    @Test
    public void constructor_invalidLogMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new LogMessage(""));
        assertThrows(IllegalArgumentException.class, () -> new LogMessage(TOO_LONG_MESSAGE));
        assertThrows(IllegalArgumentException.class, () -> new LogMessage(TOO_LONG_EMOJI_MESSAGE));
    }

    @Test
    public void constructor_boundaryLengthMessages_success() {
        assertEquals(MIN_LENGTH_MESSAGE, new LogMessage(MIN_LENGTH_MESSAGE).value);
        assertEquals(MAX_LENGTH_MESSAGE, new LogMessage(MAX_LENGTH_MESSAGE).value);
    }

    @Test
    public void constructor_validLogMessage_preservesInput() {
        String messageWithOuterWhitespace = "  " + VALID_MESSAGE + "  ";
        LogMessage logMessage = new LogMessage(messageWithOuterWhitespace);
        assertEquals(messageWithOuterWhitespace, logMessage.value);
    }

    @Test
    public void isValidLogMessage() {
        // null message
        assertThrows(NullPointerException.class, () -> LogMessage.isValidLogMessage(null));

        // invalid messages
        assertFalse(LogMessage.isValidLogMessage(""));
        assertFalse(LogMessage.isValidLogMessage(TOO_LONG_MESSAGE));
        assertFalse(LogMessage.isValidLogMessage(TOO_LONG_EMOJI_MESSAGE));

        // valid messages
        assertTrue(LogMessage.isValidLogMessage(MIN_LENGTH_MESSAGE));
        assertTrue(LogMessage.isValidLogMessage(MAX_LENGTH_MESSAGE));
        assertTrue(LogMessage.isValidLogMessage("   "));
        assertTrue(LogMessage.isValidLogMessage(" leading whitespace"));
        assertTrue(LogMessage.isValidLogMessage("Call client at 2pm."));
        assertTrue(LogMessage.isValidLogMessage("Client asked for follow-up next Wednesday!"));
        assertTrue(LogMessage.isValidLogMessage("message with trailing spaces  "));
        assertTrue(LogMessage.isValidLogMessage(MAX_LENGTH_EMOJI_MESSAGE));
    }

    @Test
    public void equals() {
        LogMessage logMessage = new LogMessage(VALID_MESSAGE);
        LogMessage sameLogMessage = new LogMessage(VALID_MESSAGE);

        // same values -> returns true
        assertTrue(logMessage.equals(sameLogMessage));
        assertEquals(logMessage.hashCode(), sameLogMessage.hashCode());

        // same object -> returns true
        assertTrue(logMessage.equals(logMessage));

        // null -> returns false
        assertFalse(logMessage.equals(null));

        // different types -> returns false
        assertFalse(logMessage.equals(5.0f));

        // different values -> returns false
        assertFalse(logMessage.equals(new LogMessage("Different message")));
    }
}
