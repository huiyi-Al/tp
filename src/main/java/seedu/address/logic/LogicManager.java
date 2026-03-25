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
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.PendingDeletionResult;
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

    // Pending deletion state
    private PendingDeletionResult pendingDeletion = null;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        // Check for pending deletion confirmation
        if (pendingDeletion != null) {
            String trimmedCommand = commandText.trim();
            String expectedConfirmCommand = DeleteCommand.COMMAND_WORD + " " + pendingDeletion.getTargetIndex().getOneBased();

            if (trimmedCommand.equals(expectedConfirmCommand)) {
                // User confirmed deletion
                Person personToDelete = pendingDeletion.getPersonToDelete();
                model.deletePerson(personToDelete);
                pendingDeletion = null;
                saveAddressBook();
                return new CommandResult(String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                        formatPersonBasic(personToDelete)));
            } else {
                // User cancelled by typing another command
                logger.info("Cancelling pending deletion");
                pendingDeletion = null;
                // Continue to execute the new command
            }
        }

        // Parse and execute the command
        Command command = addressBookParser.parseCommand(commandText);
        CommandResult result = command.execute(model);

        // If this is a pending deletion result, store it and return the confirmation message
        if (result.isPendingDeletion()) {
            pendingDeletion = (PendingDeletionResult) result;
            return result;
        }

        // Normal command result
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

    private String formatPersonBasic(Person person) {
        return person.getName().fullName + "; Phone: " + person.getPhone().value
                + "; Email: " + person.getEmail().value;
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