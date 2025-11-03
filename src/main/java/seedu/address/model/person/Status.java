package seedu.address.model.person;

/**
 * Represents the current status of client.
 * This enum is immutable and covers possible client statuses.
 */
public enum Status {
    PROSPECT,
    POTENTIAL,
    ACTIVE,
    RETURNING,
    COMPLETED,
    INACTIVE;

    public static final String MESSAGE_CONSTRAINTS = "Status must be one of the following: "
        + "PROSPECT, "
        + "POTENTIAL, "
        + "ACTIVE, "
        + "RETURNING, "
        + "COMPLETED, "
        + "INACTIVE, ";

    /**
     * Returns true if a given string is a valid Status.
     */
    public static boolean isValidStatus(String input) {
        try {
            Status.valueOf(input.toUpperCase());
            return true; // matches a valid enum constant
        } catch (IllegalArgumentException e) {
            return false; // not a valid enum name
        }
    }

}
