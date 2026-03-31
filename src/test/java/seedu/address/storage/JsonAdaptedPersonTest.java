package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogMessage;
import seedu.address.model.tag.Tag;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "a".repeat(101);
    private static final String INVALID_PHONE = "+-651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NOTES = "a".repeat(201);
    private static final String MAX_LENGTH_NOTES = "a".repeat(200);
    private static final String MAX_LENGTH_EMOJI_NOTES = "😀".repeat(200);
    private static final String TOO_LONG_EMOJI_NOTES = "😀".repeat(201);
    private static final String INVALID_TAG = " ";
    private static final String INVALID_LOG_TIMESTAMP = "22-03-2026 14:05:31";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();
    private static final List<JsonAdaptedLogEntry> VALID_LOGS = BENSON.getLogHistory().asUnmodifiableList().stream()
            .map(JsonAdaptedLogEntry::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetailsWithWhitespace_returnsTrimmedPerson() throws Exception {
        List<JsonAdaptedLogEntry> logsWithWhitespace = List.of(
                new JsonAdaptedLogEntry(" 2026-03-22T14:05:31 ",
                        " Observed intermittent leakage below sink cabinet. "));
        List<JsonAdaptedTag> tagsWithWhitespace = List.of(new JsonAdaptedTag(" Plumbing "));

        JsonAdaptedPerson person = new JsonAdaptedPerson(" Benson Meier ", " 98765432 ", " johnd@example.com ",
                " 311, Clementi Ave 2, #02-25 ", " ", logsWithWhitespace, tagsWithWhitespace);
        Person modelPerson = person.toModelType();

        assertEquals(new Name("Benson Meier"), modelPerson.getName());
        assertEquals(new Phone("98765432"), modelPerson.getPhone());
        assertEquals(new Email("johnd@example.com"), modelPerson.getEmail());
        assertEquals(new Address("311, Clementi Ave 2, #02-25"), modelPerson.getAddress());
        assertEquals(new Notes(""), modelPerson.getNotes());
        assertEquals(List.of(new LogEntry(LocalDateTime.parse("2026-03-22T14:05:31"),
                new LogMessage("Observed intermittent leakage below sink cabinet."))),
                modelPerson.getLogHistory().asUnmodifiableList());
        assertEquals(Set.of(new Tag("Plumbing")), modelPerson.getTags());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNotes_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Notes.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_notesAtBoundaryLength_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        MAX_LENGTH_NOTES, VALID_LOGS, VALID_TAGS);
        Person modelPerson = person.toModelType();
        assertEquals(new Notes(MAX_LENGTH_NOTES), modelPerson.getNotes());
    }

    @Test
    public void toModelType_notesAtBoundaryLengthWithWhitespace_returnsTrimmedPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        " " + MAX_LENGTH_NOTES + " ", VALID_LOGS, VALID_TAGS);
        Person modelPerson = person.toModelType();
        assertEquals(new Notes(MAX_LENGTH_NOTES), modelPerson.getNotes());
    }

    @Test
    public void toModelType_notesAtUnicodeBoundaryLength_returnsPerson() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        MAX_LENGTH_EMOJI_NOTES, VALID_LOGS, VALID_TAGS);
        Person modelPerson = person.toModelType();
        assertEquals(new Notes(MAX_LENGTH_EMOJI_NOTES), modelPerson.getNotes());
    }

    @Test
    public void toModelType_notesExceedUnicodeBoundary_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        TOO_LONG_EMOJI_NOTES, VALID_LOGS, VALID_TAGS);
        String expectedMessage = Notes.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNotes_returnsPersonWithDefaultNotes() throws Exception {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        null, VALID_LOGS, VALID_TAGS);

        Person modelPerson = person.toModelType();
        assertEquals(new Notes(Notes.DEFAULT_NOTE), modelPerson.getNotes());
    }

    @Test
    public void toModelType_invalidLogs_throwsIllegalValueException() {
        List<JsonAdaptedLogEntry> invalidLogs = new ArrayList<>(VALID_LOGS);
        invalidLogs.add(new JsonAdaptedLogEntry(INVALID_LOG_TIMESTAMP, "Valid log message"));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, invalidLogs, VALID_TAGS);
        assertThrows(IllegalValueException.class, JsonAdaptedLogEntry.MESSAGE_INVALID_TIMESTAMP, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, VALID_LOGS, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_unsortedLogs_reordersToNewestFirst() throws Exception {
        List<JsonAdaptedLogEntry> unsortedLogs = List.of(
                new JsonAdaptedLogEntry("2026-03-20T09:00:00", "Older log message"),
                new JsonAdaptedLogEntry("2026-03-22T11:00:00", "Newest log message"),
                new JsonAdaptedLogEntry("2026-03-21T10:00:00", "Middle log message"));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_NOTES, unsortedLogs, VALID_TAGS);
        Person modelPerson = person.toModelType();

        List<LocalDateTime> timestamps = modelPerson.getLogHistory().asUnmodifiableList().stream()
                .map(logEntry -> logEntry.getTimestamp())
                .collect(Collectors.toList());
        assertEquals(List.of(
                LocalDateTime.parse("2026-03-22T11:00:00"),
                LocalDateTime.parse("2026-03-21T10:00:00"),
                LocalDateTime.parse("2026-03-20T09:00:00")), timestamps);
    }

}
