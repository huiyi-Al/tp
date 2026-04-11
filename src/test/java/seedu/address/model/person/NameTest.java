package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName_allowedPunctuation_returnsTrue() {
        assertTrue(Name.isValidName("John/Jane"));      // slash
        assertTrue(Name.isValidName("John-Doe"));       // hyphen
        assertTrue(Name.isValidName("O'Connor"));       // apostrophe
        assertTrue(Name.isValidName("John_Doe"));       // underscore
        assertTrue(Name.isValidName("Johnson & Sons")); // ampersand
        assertTrue(Name.isValidName("Dr. Smith"));      // period
        assertTrue(Name.isValidName("S/O John"));       // slash
        assertTrue(Name.isValidName("c/o Mary"));       // slash
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("   ")); // multiple spaces only
        assertFalse(Name.isValidName("a".repeat(101))); // more than 100 chars
        assertFalse(Name.isValidName("王".repeat(101))); // more than 100 chars (Unicode)

        // valid name - English
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("David Roger S/O Jackson Ray Jr 2nd")); // contains slash (allowed)

        // valid name - Unicode (non-Latin scripts)
        assertTrue(Name.isValidName("王小明")); // Chinese
        assertTrue(Name.isValidName("김철수")); // Korean
        assertTrue(Name.isValidName("山田太郎")); // Japanese

        // valid name - mixed scripts
        assertTrue(Name.isValidName("John 王小明"));
        assertTrue(Name.isValidName("王小明 Tan"));

        // valid name - single character
        assertTrue(Name.isValidName("a"));
        assertTrue(Name.isValidName("王"));

        // valid name - exactly 100 characters
        assertTrue(Name.isValidName("a".repeat(100)));
        assertTrue(Name.isValidName("王".repeat(100)));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
