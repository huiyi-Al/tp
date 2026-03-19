package seedu.address.ui;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Base class for GUI tests that initializes JavaFX toolkit and provides common functionality.
 */
@ExtendWith(ApplicationExtension.class)
public abstract class GuiUnitTest {

    protected final Logger logger = LogsCenter.getLogger(getClass());

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

}
