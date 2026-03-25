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

    /**
     * Constructs a {@code CommandResult} for normal commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
        this.pendingAction = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null);
    }

    /**
     * Constructs a {@code CommandResult} that requires confirmation.
     */
    public CommandResult(String feedbackToUser, PendingAction pendingAction) {
        this(feedbackToUser, false, false, pendingAction);
    }

    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, PendingAction pendingAction) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
        this.pendingAction = pendingAction;
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
                && Objects.equals(pendingAction, otherCommandResult.pendingAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, pendingAction);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("pendingAction", getPendingAction())
                .toString();
    }
}
