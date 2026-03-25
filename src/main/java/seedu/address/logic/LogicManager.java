package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ConfirmableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";
    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    private ConfirmableCommand pendingCommand = null;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     *
     * @param model The model component that holds the address book data in memory.
     * @param storage The storage component that handles reading from and writing to disk.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        // Check if there's a pending command waiting for confirmation
        if (pendingCommand != null) {
            Command command = addressBookParser.parseCommand(commandText);

            if (pendingCommand.matches(command)) {
                // User confirmed the pending command
                CommandResult result = pendingCommand.executeConfirmed(model);
                pendingCommand = null;
                saveAddressBook();
                return result;
            } else {
                // User cancelled by typing another command
                pendingCommand = null;
                // Continue to execute the new command
            }
        }

        // Parse and execute the command normally
        Command command = addressBookParser.parseCommand(commandText);

        // If it's a confirmable command, prepare and store it as pending
        if (command instanceof ConfirmableCommand) {
            ConfirmableCommand confirmable = (ConfirmableCommand) command;
            confirmable.prepare(model);  // Prepare (e.g., fetch person to delete)
            pendingCommand = confirmable;
            return new CommandResult(confirmable.getConfirmationMessage());
        }

        // Normal command execution
        CommandResult result = command.execute(model);
        saveAddressBook();
        return result;
    }

    private void saveAddressBook() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableValue<Person> getSelectedPerson() {
        return model.getSelectedPerson();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
