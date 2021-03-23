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

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application GUI.
 */
public class MainGui {
  private Stage stage;
  MainPresenter presenter;

  /**
   * Creates application window.
   *
   * @param stage
   *          stage
   */
  public MainGui(Stage stage) {
    this.stage = stage;
    MainView view = new MainView();
    presenter = (MainPresenter) view.getPresenter();
    stage.setTitle("Composition Map");
    Scene scene = new Scene(view.getView());
    scene.getStylesheets().add("error.css");
    stage.setScene(scene);
    JavafxUtils.setMaxSizeForScreen(stage);
  }

  public void show() {
    stage.show();
  }

  public void hide() {
    stage.hide();
  }
}
