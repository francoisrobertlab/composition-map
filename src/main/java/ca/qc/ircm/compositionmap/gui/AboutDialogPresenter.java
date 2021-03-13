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

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * About dialog presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AboutDialogPresenter {
  @FXML
  private Button ok;

  @FXML
  private void initialize() {
    ok.requestFocus();
  }

  @FXML
  private void close(Event event) {
    ok.getScene().getWindow().hide();
  }

  void focusOnDefault() {
    ok.requestFocus();
  }
}
