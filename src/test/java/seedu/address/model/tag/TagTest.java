package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string (0 characters)
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("a".repeat(51))); // 51 characters (Exceeds 50 limit)

        // valid tag names
        assertTrue(Tag.isValidTagName("a")); // exactly 1 character
        assertTrue(Tag.isValidTagName("abcde12345")); // alphanumeric
        assertTrue(Tag.isValidTagName("12345")); // numeric only
        assertTrue(Tag.isValidTagName("Best-Friend!")); // symbols now allowed
        assertTrue(Tag.isValidTagName("2nd floor")); // spaces now allowed
        assertTrue(Tag.isValidTagName("#urgent")); // leading symbols allowed
        assertTrue(Tag.isValidTagName("a".repeat(50))); // exactly 50 characters
    }

}
