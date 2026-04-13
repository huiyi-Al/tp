package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private StackPane detailsPlaceholder;

    @FXML
    private Label noselectionLabel;

    private PersonDetailCard currentDetailCard;

    /**
     * Creates a {@code PersonDetailPanel} with the given {@code ObservableValue}.
     */
    public PersonDetailPanel(ObservableValue<Person> selectedPerson) {
        super(FXML);

        showNoSelection();

        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayPersonDetails(newValue);
            } else {
                showNoSelection();
            }
        });
    }

    /**
     * Shows the "no person selected" message.
     */
    private void showNoSelection() {
        detailsPlaceholder.getChildren().clear();
        currentDetailCard = null;

        noselectionLabel.setVisible(true);
        noselectionLabel.setManaged(true);
        noselectionLabel.setText("Please use the 'view' command to view client details");
    }

    /**
     * Displays the details of the given person.
     */
    private void displayPersonDetails(Person person) {
        logger.info("Displaying client details");

        noselectionLabel.setVisible(false);
        noselectionLabel.setManaged(false);

        currentDetailCard = new PersonDetailCard(person);
        detailsPlaceholder.getChildren().setAll(currentDetailCard.getRoot());
    }
}
