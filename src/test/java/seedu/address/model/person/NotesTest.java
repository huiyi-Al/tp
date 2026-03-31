package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NotesTest {
    private static final String MAX_LENGTH_NOTES = "a".repeat(200);
    private static final String TOO_LONG_NOTES = "a".repeat(201);
    private static final String MAX_LENGTH_EMOJI_NOTES = "😀".repeat(200);
    private static final String TOO_LONG_EMOJI_NOTES = "😀".repeat(201);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void constructor_invalidNotes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Notes(TOO_LONG_NOTES));
        assertThrows(IllegalArgumentException.class, () -> new Notes(TOO_LONG_EMOJI_NOTES));
    }

    @Test
    public void isValidNotes() {
        // null notes
        assertThrows(NullPointerException.class, () -> Notes.isValidNotes(null));

        // invalid notes
        assertFalse(Notes.isValidNotes(TOO_LONG_NOTES));
        assertFalse(Notes.isValidNotes(TOO_LONG_EMOJI_NOTES));

        // valid notes
        assertTrue(Notes.isValidNotes("This is a valid note.")); // normal note
        assertTrue(Notes.isValidNotes("")); // empty note
        assertTrue(Notes.isValidNotes(MAX_LENGTH_NOTES)); // 200 characters
        assertTrue(Notes.isValidNotes(MAX_LENGTH_EMOJI_NOTES)); // 200 Emojis
        assertTrue(Notes.isValidNotes("This note\nhas multiple lines.")); // multiline note
        assertTrue(Notes.isValidNotes("  Needs follow up tomorrow.  ")); // note with spaces
    }

    @Test
    public void equals() {
        Notes notes = new Notes("Valid Notes");

        // same values -> returns true
        assertTrue(notes.equals(new Notes("Valid Notes")));

        // same object -> returns true
        assertTrue(notes.equals(notes));

        // null -> returns false
        assertFalse(notes.equals(null));

        // different types -> returns false
        assertFalse(notes.equals(5.0f));

        // different values -> returns false
        assertFalse(notes.equals(new Notes("Other Valid Notes")));
    }
}
