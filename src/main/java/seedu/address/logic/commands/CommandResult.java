package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.pending.PendingAction;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private final boolean showHelp;
    private final boolean exit;
    private final PendingAction pendingAction;
    private final boolean shouldSaveAddressBook;

    /**
     * Constructs a {@code CommandResult} for normal commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, false);
    }

    /**
     * Constructs a {@code CommandResult} that requires confirmation.
     */
    public CommandResult(String feedbackToUser, PendingAction pendingAction) {
        this(feedbackToUser, false, false, pendingAction, false);
    }

    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                          PendingAction pendingAction, boolean shouldSaveAddressBook) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
        this.pendingAction = pendingAction;
        this.shouldSaveAddressBook = shouldSaveAddressBook;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean shouldSaveAddressBook() {
        return shouldSaveAddressBook;
    }

    /**
     * Returns a {@code CommandResult} identical to this result, but marked to
     * trigger an address book save after successful command execution.
     */
    public CommandResult withSaveRequired() {
        if (shouldSaveAddressBook) {
            return this;
        }
        return new CommandResult(feedbackToUser, showHelp, exit, pendingAction, true);
    }

    public boolean hasPendingAction() {
        return pendingAction != null;
    }

    public Optional<PendingAction> getPendingAction() {
        return Optional.ofNullable(pendingAction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && shouldSaveAddressBook == otherCommandResult.shouldSaveAddressBook
                && Objects.equals(pendingAction, otherCommandResult.pendingAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, pendingAction, shouldSaveAddressBook);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("shouldSaveAddressBook", shouldSaveAddressBook)
                .add("pendingAction", getPendingAction())
                .toString();
    }
}
