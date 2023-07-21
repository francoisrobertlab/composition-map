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
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import java.io.File;
import java.nio.file.Path;
import javafx.geometry.Rectangle2D;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

@TestFxTestAnnotations
public class JavafxUtilsTest {
  private Stage stage;

  @Start
  public void beforeEach(Stage stage) throws Throwable {
    this.stage = stage;
  }

  @Stop
  public void afterEach() {
    stage.hide();
  }

  @Test
  public void setMaxSizeForScreen(FxRobot robot) {
    JavafxUtils.setMaxSizeForScreen(stage);
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    assertEquals(visualBounds.getHeight(), stage.getMaxHeight());
    assertEquals(visualBounds.getWidth(), stage.getMaxWidth());
  }

  @Test
  public void setValidInitialDirectory_FileChooser_Exists(FxRobot robot, @TempDir Path folder) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(folder.toFile());
    JavafxUtils.setValidInitialDirectory(fileChooser);
    assertEquals(folder.toFile(), fileChooser.getInitialDirectory());
  }

  @Test
  public void setValidInitialDirectory_FileChooser_NotExistsParentExists(FxRobot robot,
      @TempDir Path folder) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(folder.resolve("notexists").toFile());
    JavafxUtils.setValidInitialDirectory(fileChooser);
    assertEquals(folder.toFile(), fileChooser.getInitialDirectory());
  }

  @Test
  public void setValidInitialDirectory_FileChooser_NotExistsNoParent(FxRobot robot) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("notexists2127r4q4983204032eiqjfojeojcija"));
    JavafxUtils.setValidInitialDirectory(fileChooser);
    assertNull(fileChooser.getInitialDirectory());
  }

  @Test
  public void setValidInitialDirectory_DirectoryChooser_Exists(FxRobot robot,
      @TempDir Path folder) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(folder.toFile());
    JavafxUtils.setValidInitialDirectory(directoryChooser);
    assertEquals(folder.toFile(), directoryChooser.getInitialDirectory());
  }

  @Test
  public void setValidInitialDirectory_DirectoryChooser_NotExistsParentExists(FxRobot robot,
      @TempDir Path folder) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(folder.resolve("notexists").toFile());
    JavafxUtils.setValidInitialDirectory(directoryChooser);
    assertEquals(folder.toFile(), directoryChooser.getInitialDirectory());
  }

  @Test
  public void setValidInitialDirectory_DirectoryChooser_NotExistsNoParent(FxRobot robot) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File("notexists2127r4q4983204032eiqjfojeojcija"));
    JavafxUtils.setValidInitialDirectory(directoryChooser);
    assertNull(directoryChooser.getInitialDirectory());
  }
}
