package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail(" @example.com")); // leading space (appears blank)
        assertFalse(Email.isValidEmail("  @example.com")); // multiple spaces
        assertFalse(Email.isValidEmail("+@example.com")); // local part is just a special character
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Email.isValidEmail("+peter@example.com")); // local part starts with +
        assertFalse(Email.isValidEmail("_peter@example.com")); // local part starts with _
        assertFalse(Email.isValidEmail("peter_@example.com")); // local part ends with _
        assertFalse(Email.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(Email.isValidEmail("peter+_jack@example.com")); // local part has consecutive specials
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example-.com")); // internal domain name ends with hyphen
        assertFalse(Email.isValidEmail("peterjack@sub.-domain.com")); // internal domain name starts with hyphen
        assertFalse(Email.isValidEmail("peterjack@example.c")); // top level domain has less than two chars
        assertFalse(Email.isValidEmail("a".repeat(65) + "@example.com"));
        assertFalse(Email.isValidEmail("peterjack@" + "a".repeat(64) + ".com"));
        assertFalse(Email.isValidEmail("a@" + "d".repeat(63) + "." + "d".repeat(63) + "."
                + "d".repeat(63) + "." + "d".repeat(59) + ".comm")); // 256 chars domain
        assertFalse(Email.isValidEmail("a".repeat(64) + "@" + "d".repeat(252) + ".com"));

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com")); // _ in local part
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com")); // . in local part
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com")); // + in local part
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com")); // - in local part
        assertTrue(Email.isValidEmail("peter=jack@example.com")); // = in local part
        assertTrue(Email.isValidEmail("peter!jack@example.com")); // ! in local part
        assertTrue(Email.isValidEmail("peter#jack@example.com")); // # in local part
        assertTrue(Email.isValidEmail("peter$jack@example.com")); // $ in local part
        assertTrue(Email.isValidEmail("peter%jack@example.com")); // % in local part
        assertTrue(Email.isValidEmail("peter&jack@example.com")); // & in local part
        assertTrue(Email.isValidEmail("peter'jack@example.com")); // ' in local part
        assertTrue(Email.isValidEmail("peter*jack@example.com")); // * in local part
        assertTrue(Email.isValidEmail("peter/jack@example.com")); // / in local part
        assertTrue(Email.isValidEmail("peter?jack@example.com")); // ? in local part
        assertTrue(Email.isValidEmail("peter^jack@example.com")); // ^ in local part
        assertTrue(Email.isValidEmail("peter`jack@example.com")); // ` in local part
        assertTrue(Email.isValidEmail("peter{jack@example.com")); // { in local part
        assertTrue(Email.isValidEmail("peter|jack@example.com")); // | in local part
        assertTrue(Email.isValidEmail("peter}jack@example.com")); // } in local part
        assertTrue(Email.isValidEmail("peter~jack@example.com")); // ~ in local part
        assertTrue(Email.isValidEmail("a@bc")); // minimal
        assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("p.e.t.e.r!j#a$c%k@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
        assertTrue(Email.isValidEmail("a".repeat(64) + "@example.com"));
        assertTrue(Email.isValidEmail("peterjack@" + "a".repeat(63) + ".com"));
        assertTrue(Email.isValidEmail("a@" + "d".repeat(63) + "." + "d".repeat(63) + "."
                + "d".repeat(63) + "." + "d".repeat(59) + ".com")); // 254 chars domain
        assertTrue(Email.isValidEmail("a".repeat(64) + "@" + "d".repeat(63) + "."
                + "d".repeat(63) + "." + "d".repeat(63) + "." + "d".repeat(59) + ".com"));
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
