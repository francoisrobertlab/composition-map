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

package ca.qc.ircm.compositionmap;

import ca.qc.ircm.compositionmap.gui.SplashScreen;
import javafx.application.Preloader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaFX application preloader.
 */
public class MainPreloader extends Preloader {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private SplashScreen splash;

  @Override
  public void start(Stage primaryStage) throws Exception {
    splash = new SplashScreen(primaryStage);
    splash.show();
  }

  @Override
  public void handleApplicationNotification(PreloaderNotification info) {
    if (info instanceof ApplicationStarted) {
      splash.hide();
    }
  }

  @Override
  public boolean handleErrorNotification(ErrorNotification info) {
    logger.error("Could not start application", info.getCause());
    com.airhacks.afterburner.injection.Injector.resetInstanceSupplier();
    new Alert(AlertType.ERROR, "Could not start Composition Map, see error in log").showAndWait();
    return true;
  }
}
