package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetailCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label notesPrefix;
    @FXML
    private Label notesValue;
    @FXML
    private Label logsHeader;
    @FXML
    private ListView<LogEntry> logsListView;
    @FXML
    private Label noLogsLabel;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonDetailCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText("Phone number : " + person.getPhone().value);
        address.setText("Address : " + person.getAddress().value);
        email.setText("Email address : " + person.getEmail().value);
        initializeNotes(person);
        initializeLogSummary(person);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void initializeNotes(Person person) {
        notesPrefix.setText("Notes :");
        String notesText = person.getNotes().value;
        if (notesText.isBlank()) {
            notesValue.setText("No notes");
            if (!notesValue.getStyleClass().contains("note-empty-text")) {
                notesValue.getStyleClass().add("note-empty-text");
            }
            return;
        }

        notesValue.setText(notesText);
        notesValue.getStyleClass().remove("note-empty-text");
    }

    private void initializeLogSummary(Person person) {
        List<LogEntry> logEntries = person.getLogHistory().asUnmodifiableList();
        int logCount = logEntries.size();
        logsHeader.setText("Logs (" + logCount + ")");
        logsListView.setItems(FXCollections.observableArrayList(logEntries));
        logsListView.setCellFactory(unused -> new LogEntryListCell());

        if (!logEntries.isEmpty()) {
            setNodeShown(logsListView, true);
            setNodeShown(noLogsLabel, false);
            return;
        }

        setNodeShown(logsListView, false);
        setNodeShown(noLogsLabel, true);
    }

    private static void setNodeShown(Node node, boolean isShown) {
        node.setVisible(isShown);
        node.setManaged(isShown);
    }
}

