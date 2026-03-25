package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.PendingAction;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private final boolean showHelp;
    private final boolean exit;
    private final PendingAction pendingAction;

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null);
    }

    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null);
    }

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

    public boolean requiresConfirmation() {
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

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }
}
