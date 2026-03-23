package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private static final DateTimeFormatter LOG_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu, HH:mm");

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
    private Label notes;
    @FXML
    private Label logsHeader;
    @FXML
    private Label latestLogTimestamp;
    @FXML
    private Label latestLogPreview;
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
        notes.setText("Notes : " + person.getNotes().value);
        initializeLogSummary(person);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void initializeLogSummary(Person person) {
        int logCount = person.getLogHistory().size();
        logsHeader.setText("Logs (" + logCount + ")");

        Optional<LogEntry> latestLog = person.getLogHistory().getLatest();
        if (latestLog.isPresent()) {
            LogEntry logEntry = latestLog.get();
            latestLogTimestamp.setText("Latest: " + logEntry.getTimestamp().format(LOG_TIMESTAMP_FORMATTER));
            latestLogPreview.setText(logEntry.getMessage().value);
            setLabelShown(latestLogTimestamp, true);
            setLabelShown(latestLogPreview, true);
            setLabelShown(noLogsLabel, false);
            return;
        }

        setLabelShown(latestLogTimestamp, false);
        setLabelShown(latestLogPreview, false);
        setLabelShown(noLogsLabel, true);
    }

    private static void setLabelShown(Label label, boolean isShown) {
        label.setVisible(isShown);
        label.setManaged(isShown);
    }
}

