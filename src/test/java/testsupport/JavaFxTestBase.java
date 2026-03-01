package testsupport;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;

/**
 * Initializes the JavaFX toolkit once for tests that construct JavaFX classes (e.g., Image).
 * This is NOT UI testing; it only ensures JavaFX types can be instantiated safely in unit tests.
 */
public abstract class JavaFxTestBase {

    @BeforeAll
    public static void initJavaFxToolkit() {
        // Initializing JFXPanel initializes the JavaFX runtime.
        new JFXPanel();
    }
}
