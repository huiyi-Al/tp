package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogMessage;

/**
 * Jackson-friendly version of {@link LogEntry}.
 */
class JsonAdaptedLogEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log entry's %s field is missing!";
    public static final String MESSAGE_INVALID_TIMESTAMP = "Timestamp should be in ISO-8601 format.";

    private final String timestamp;
    private final String message;

    /**
     * Constructs a {@code JsonAdaptedLogEntry} with the given log details.
     */
    @JsonCreator
    public JsonAdaptedLogEntry(@JsonProperty("timestamp") String timestamp, @JsonProperty("message") String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    /**
     * Converts a given {@code LogEntry} into this class for Jackson use.
     */
    public JsonAdaptedLogEntry(LogEntry source) {
        timestamp = source.getTimestamp().toString();
        message = source.getMessage().value;
    }

    /**
     * Converts this Jackson-friendly adapted log entry object into the model's {@code LogEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log entry.
     */
    public LogEntry toModelType() throws IllegalValueException {
        if (timestamp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timestamp"));
        }

        final LocalDateTime modelTimestamp;
        try {
            modelTimestamp = LocalDateTime.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_INVALID_TIMESTAMP);
        }

        if (message == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "message"));
        }
        if (!LogMessage.isValidLogMessage(message)) {
            throw new IllegalValueException(LogMessage.MESSAGE_CONSTRAINTS);
        }
        final LogMessage modelMessage = new LogMessage(message);

        return new LogEntry(modelTimestamp, modelMessage);
    }
}
