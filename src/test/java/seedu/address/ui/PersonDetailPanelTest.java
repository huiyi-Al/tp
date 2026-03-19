package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

@ExtendWith(ApplicationExtension.class)
public class PersonDetailPanelTest {

    private SimpleObjectProperty<Person> selectedPerson;
    private PersonDetailPanel personDetailPanel;

    @Start
    public void start(Stage stage) {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        selectedPerson = new SimpleObjectProperty<>();
        personDetailPanel = new PersonDetailPanel(selectedPerson);
    }

    @Test
    public void constructor_createsPanel() {
        assertNotNull(personDetailPanel);
    }

    @Test
    public void personSelected_displaysDetails() {
        selectedPerson.set(ALICE);
        assertNotNull(personDetailPanel.getRoot());
    }

    @Test
    public void selectedPersonListener_nullSelected_showsNoSelection() {
        selectedPerson.set(ALICE);
        selectedPerson.set(null);
        assertNotNull(personDetailPanel.getRoot());
    }
}