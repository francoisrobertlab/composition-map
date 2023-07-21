/*
 * Copyright (c) 2021 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.compositionmap.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

@TestFxTestAnnotations
public class SplashScreenTest {
  private SplashScreen splash;
  private Stage stage;

  /**
   * Before each tests.
   *
   * @param unused
   *          unused stage
   * @throws Throwable
   *           should not throw exception
   */
  @Start
  public void beforeEach(Stage unused) throws Throwable {
    this.stage = FxToolkit.registerStage(() -> new Stage());
    splash = new SplashScreen(stage);
    splash.show();
  }

  @Stop
  public void afterEach() {
    stage.hide();
  }

  @Test
  public void init(FxRobot robot) {
    assertEquals(StageStyle.UNDECORATED, stage.getStyle());
    FxAssert.verifyThat(robot.lookup(".splash"), view -> view.getCursor() == Cursor.WAIT);
  }

  @Test
  public void show(FxRobot robot) {
    robot.interact(() -> stage.hide());
    robot.interact(() -> splash.show());
    assertTrue(stage.isShowing());
  }

  @Test
  public void hide(FxRobot robot) {
    robot.interact(() -> stage.show());
    robot.interact(() -> splash.hide());
    assertFalse(stage.isShowing());
  }
}
