package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The total email address must not exceed 320 characters.\n"
            + "2. The local-part must be between 1 and 64 characters.\n"
            + "3. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ").\n"
            + "4. The local part may not start or end with special characters and special characters cannot appear "
            + "consecutively.\n"
            + "5. This is followed by a '@' and then a domain name.\n"
            + "6. The domain name is made up of domain labels separated by periods and "
            + "must not exceed 255 characters.\n"
            + "7. The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.\n"
            + "8. Each domain label must not exceed 63 characters.";


    private static final String ALPHANUMERIC = "a-zA-Z0-9";
    // Total length limit of 254
    private static final String LENGTH_CHECK = "(?=.{1,320}$)";
    // Local part length 1-64
    private static final String LOCAL_PART_LENGTH_CHECK = "(?=[^@]{1,64}@)";
    // Local part: Alphanumeric, can have (+_.-) but not at start/end or consecutively
    private static final String LOCAL_PART_REGEX =
            "[" + ALPHANUMERIC + "]+([" + SPECIAL_CHARACTERS + "][" + ALPHANUMERIC + "]+)*";
    // Domain length limit 1-255
    private static final String DOMAIN_LENGTH_CHECK = "(?=[^@]*@.{1,255}$)";
    // Domain label: Alphanumeric, can have hyphens but not at start/end, max 63 chars
    private static final String DOMAIN_LABEL =
            "[" + ALPHANUMERIC + "]([" + ALPHANUMERIC + "-]{0,61}[" + ALPHANUMERIC + "])?";
    // Domain: Labels separated by dots, last label at least 2 chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_LABEL + "\\.)*" + "[" + ALPHANUMERIC + "]{2,}";
    public static final String VALIDATION_REGEX =
            "^" + LENGTH_CHECK + LOCAL_PART_LENGTH_CHECK + DOMAIN_LENGTH_CHECK
                    + LOCAL_PART_REGEX + "@" + DOMAIN_REGEX + "$";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
