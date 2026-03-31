package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetailCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailCard.fxml";
    private static final int NOTES_VISIBLE_ROWS = 3;
    private static final double NOTES_VIEWPORT_VERTICAL_CHROME = 14;
    private static final double NOTES_DESCENDER_SAFETY_PADDING = 3;

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
    private Label notesInlineValue;
    @FXML
    private Label notesValue;
    @FXML
    private ScrollPane notesScrollPane;
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
        notesPrefix.setMinWidth(Region.USE_PREF_SIZE);
        notesValue.setMinWidth(0);
        notesValue.setMaxWidth(Double.MAX_VALUE);
        notesScrollPane.setFitToWidth(true);
        configureNotesViewportHeight();

        String notesText = normalizeNotesForDisplay(person.getNotes().value);
        if (notesText.isBlank()) {
            notesInlineValue.setText("No notes");
            setNodeShown(notesInlineValue, true);
            setNodeShown(notesScrollPane, false);
            return;
        }

        setNodeShown(notesInlineValue, false);
        setNodeShown(notesScrollPane, true);
        notesValue.setText(notesText);
    }

    private void configureNotesViewportHeight() {
        Text measurementText = new Text("Ag");
        measurementText.setFont(notesValue.getFont());
        double lineHeight = measurementText.getLayoutBounds().getHeight();
        double minHeight = lineHeight + NOTES_VIEWPORT_VERTICAL_CHROME + NOTES_DESCENDER_SAFETY_PADDING;
        double preferredHeight = (lineHeight * NOTES_VISIBLE_ROWS)
                + NOTES_VIEWPORT_VERTICAL_CHROME + NOTES_DESCENDER_SAFETY_PADDING;
        notesScrollPane.setMinHeight(minHeight);
        notesScrollPane.setPrefHeight(preferredHeight);
    }

    private static String normalizeNotesForDisplay(String rawNotes) {
        return rawNotes.replace("\r\n", "\n")
                .replace('\r', '\n');
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

