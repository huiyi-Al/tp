package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private PersonDetailPanel personDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane personDetailsPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(),
                                              logic.getSelectedPerson());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personDetailPanel = new PersonDetailPanel(logic.getSelectedPerson());
        personDetailsPlaceholder.getChildren().add(personDetailPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        Rectangle2D visualBounds = resolveVisualBounds(guiSettings);
        applySafeMinimumSize(visualBounds);

        double launchWidth = clamp(guiSettings.getWindowWidth(), primaryStage.getMinWidth(), visualBounds.getWidth());
        double launchHeight = clamp(guiSettings.getWindowHeight(), primaryStage.getMinHeight(),
                visualBounds.getHeight());
        primaryStage.setWidth(launchWidth);
        primaryStage.setHeight(launchHeight);

        if (guiSettings.getWindowCoordinates() == null) {
            centerInBounds(visualBounds, launchWidth, launchHeight);
            return;
        }

        double launchX = clamp(guiSettings.getWindowCoordinates().getX(),
                visualBounds.getMinX(), visualBounds.getMaxX() - launchWidth);
        double launchY = clamp(guiSettings.getWindowCoordinates().getY(),
                visualBounds.getMinY(), visualBounds.getMaxY() - launchHeight);
        primaryStage.setX(launchX);
        primaryStage.setY(launchY);
    }

    /**
     * Resolves the best visual bounds to use for launch sizing and positioning.
     */
    private Rectangle2D resolveVisualBounds(GuiSettings guiSettings) {
        if (guiSettings.getWindowCoordinates() == null) {
            return Screen.getPrimary().getVisualBounds();
        }

        double x = guiSettings.getWindowCoordinates().getX();
        double y = guiSettings.getWindowCoordinates().getY();
        double width = Math.max(1, guiSettings.getWindowWidth());
        double height = Math.max(1, guiSettings.getWindowHeight());
        return Screen.getScreensForRectangle(x, y, width, height).stream()
                .findFirst()
                .orElse(Screen.getPrimary())
                .getVisualBounds();
    }

    /**
     * Caps stage minimum size so it never exceeds the usable screen area.
     */
    private void applySafeMinimumSize(Rectangle2D visualBounds) {
        primaryStage.setMinWidth(Math.min(primaryStage.getMinWidth(), visualBounds.getWidth()));
        primaryStage.setMinHeight(Math.min(primaryStage.getMinHeight(), visualBounds.getHeight()));
    }

    private void centerInBounds(Rectangle2D visualBounds, double width, double height) {
        double centeredX = visualBounds.getMinX() + (visualBounds.getWidth() - width) / 2;
        double centeredY = visualBounds.getMinY() + (visualBounds.getHeight() - height) / 2;
        primaryStage.setX(centeredX);
        primaryStage.setY(centeredY);
    }

    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
