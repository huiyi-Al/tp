package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class PersonDetailCardTest {

    private VBox root;

    @Start
    private void start(Stage stage) {
        root = new VBox();
        stage.setScene(new Scene(root, 800, 700));
        stage.show();
    }

    @Test
    public void constructor_personWithoutLogs_showsEmptyLogsState(FxRobot robot) {
        PersonDetailCard personDetailCard = new PersonDetailCard(ALICE);
        robot.interact(() -> root.getChildren().setAll(personDetailCard.getRoot()));

        Label logsHeader = robot.lookup("#logsHeader").queryAs(Label.class);
        Label logsMetaLabel = robot.lookup("#logsMetaLabel").queryAs(Label.class);
        ListView<?> logsListView = (ListView<?>) robot.lookup("#logsListView").query();
        Label noLogsLabel = robot.lookup("#noLogsLabel").queryAs(Label.class);

        assertEquals("Logs (0)", logsHeader.getText());
        assertFalse(logsMetaLabel.isVisible());
        assertFalse(logsListView.isVisible());
        assertTrue(noLogsLabel.isVisible());
        assertEquals("No entries", noLogsLabel.getText());
    }

    @Test
    public void constructor_personWithLogs_showsListAndSortedMetadata(FxRobot robot) {
        PersonDetailCard personDetailCard = new PersonDetailCard(BENSON);
        robot.interact(() -> root.getChildren().setAll(personDetailCard.getRoot()));

        Label logsHeader = robot.lookup("#logsHeader").queryAs(Label.class);
        Label logsMetaLabel = robot.lookup("#logsMetaLabel").queryAs(Label.class);
        ListView<?> logsListView = (ListView<?>) robot.lookup("#logsListView").query();
        Label noLogsLabel = robot.lookup("#noLogsLabel").queryAs(Label.class);

        assertEquals("Logs (2)", logsHeader.getText());
        assertTrue(logsMetaLabel.isVisible());
        assertEquals("Sorted: Latest", logsMetaLabel.getText());
        assertTrue(logsListView.isVisible());
        assertEquals(2, logsListView.getItems().size());
        assertFalse(noLogsLabel.isVisible());
    }
}
