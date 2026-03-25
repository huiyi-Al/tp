package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.log.LogEntry;

/**
 * Custom list cell that displays a log entry timestamp and message.
 */
public class LogEntryListCell extends ListCell<LogEntry> {
    private static final DateTimeFormatter LOG_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu, HH:mm");

    private final Label titleLabel = new Label();
    private final Label timestampLabel = new Label();
    private final Label messageLabel = new Label();
    private final Region headerSpacer = new Region();
    private final HBox headerRow = new HBox(8, titleLabel, headerSpacer, timestampLabel);
    private final VBox container = new VBox(6, headerRow, messageLabel);

    /**
     * Creates a log entry list cell with title, timestamp and message sections.
     */
    public LogEntryListCell() {
        titleLabel.getStyleClass().add("log-entry-title");
        timestampLabel.getStyleClass().add("log-entry-timestamp");
        messageLabel.getStyleClass().add("log-entry-message");
        container.getStyleClass().add("log-entry-container");
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(Double.MAX_VALUE);
        container.prefWidthProperty().bind(widthProperty().subtract(20));
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(LogEntry logEntry, boolean empty) {
        super.updateItem(logEntry, empty);

        if (empty || logEntry == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        int displayIndex = getIndex() + 1;
        titleLabel.setText("Log " + displayIndex);
        timestampLabel.setText(logEntry.getTimestamp().format(LOG_TIMESTAMP_FORMATTER));
        messageLabel.setText(logEntry.getMessage().value);
        setText(null);
        setGraphic(container);
    }
}
