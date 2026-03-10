package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NotesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void constructor_invalidNotes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Notes("This is a note\nwith a newline."));
    }

    @Test
    public void isValidNotes() {
        // null notes
        assertThrows(NullPointerException.class, () -> Notes.isValidNotes(null));

        // invalid notes
        assertFalse(Notes.isValidNotes("This is a note\nwith a newline."));
        assertFalse(Notes.isValidNotes("This is a note\rwith a carriage return."));

        // valid notes
        assertTrue(Notes.isValidNotes("This is a valid note.")); // normal note
        assertTrue(Notes.isValidNotes("")); // empty note
        assertTrue(Notes.isValidNotes("  Needs follow up tomorrow.  ")); // note with spaces
    }
}
