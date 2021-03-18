package ca.qc.ircm.compositionmap.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ButtonMatchers;
import org.testfx.matcher.control.LabeledMatchers;

@TestFxTestAnnotations
public class AboutDialogTest {
  private AboutDialog dialog;
  private Stage stage;

  @Start
  public void beforeEach(Stage stage) {
    this.stage = stage;
    dialog = new AboutDialog(stage);
  }

  @Stop
  public void afterEach() {
    dialog.hide();
  }

  private Stage dialogStage(FxRobot robot) {
    return (Stage) robot.lookup(".about-dialog").queryParent().getScene().getWindow();
  }

  @Test
  public void init(FxRobot robot) {
    Stage dialogStage = dialogStage(robot);
    assertEquals(Modality.NONE, dialogStage.getModality());
    assertEquals(stage, dialogStage.getOwner());
    assertTrue(dialogStage.isShowing());
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    assertEquals(visualBounds.getHeight(), dialogStage.getMaxHeight());
    assertEquals(visualBounds.getWidth(), dialogStage.getMaxWidth());
    FxAssert.verifyThat(".ok", ButtonMatchers.isDefaultButton());
    FxAssert.verifyThat(".ok", ButtonMatchers.isCancelButton());
    FxAssert.verifyThat(".ok", NodeMatchers.isFocused());
  }

  @Test
  public void version(FxRobot robot) throws Throwable {
    String version = Files.readString(Paths.get(getClass().getResource("/version.txt").toURI()));
    FxAssert.verifyThat(".message", LabeledMatchers.hasText("Composition Map version " + version));
  }

  @Test
  public void show(FxRobot robot) {
    Stage dialogStage = dialogStage(robot);
    robot.interact(() -> dialog.show());
    assertTrue(dialogStage.isShowing());
  }

  @Test
  public void show_AfterHide(FxRobot robot) throws Throwable {
    Stage dialogStage = dialogStage(robot);
    robot.interact(() -> dialogStage.hide());
    robot.interact(() -> dialog.show());
    assertTrue(dialogStage.isShowing());
  }

  @Test
  public void hide(FxRobot robot) {
    Stage dialogStage = dialogStage(robot);
    robot.interact(() -> dialog.hide());
    assertFalse(dialogStage.isShowing());
  }

  @Test
  public void ok_Clicked(FxRobot robot) throws Throwable {
    Stage dialogStage = dialogStage(robot);
    robot.clickOn(".ok");
    assertFalse(dialogStage.isShowing());
  }
}
