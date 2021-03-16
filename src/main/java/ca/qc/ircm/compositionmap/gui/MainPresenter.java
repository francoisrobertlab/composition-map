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

import static ca.qc.ircm.compositionmap.sequence.SequenceType.DNA;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.PROTEIN;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.RNA;

import ca.qc.ircm.compositionmap.sequence.SequenceService;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javax.inject.Inject;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Main view presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainPresenter {
  private StringProperty sequenceProperty = new SimpleStringProperty();
  private StringProperty symbolsProperty = new SimpleStringProperty();
  @FXML
  private BorderPane layout;
  @FXML
  private MenuBar menu;
  @FXML
  private TextArea sequence;
  @FXML
  private TextField symbols;
  @Inject
  private SequenceService service;

  @FXML
  private void initialize() {
    if (SystemUtils.IS_OS_MAC_OSX) {
      menu.setUseSystemMenuBar(true);
    }

    sequenceProperty.bindBidirectional(sequence.textProperty());
    symbolsProperty.bindBidirectional(symbols.textProperty());
  }

  @FXML
  private void proteinSymbols() {
    symbolsProperty.set(mergeSymbols(service.symbolsOrder(PROTEIN), sequenceSymbols()));
  }

  @FXML
  private void dnaSymbols() {
    symbolsProperty.set(mergeSymbols(service.symbolsOrder(DNA), sequenceSymbols()));
  }

  @FXML
  private void rnaSymbols() {
    symbolsProperty.set(mergeSymbols(service.symbolsOrder(RNA), sequenceSymbols()));
  }

  @FXML
  private void otherSymbols() {
    symbolsProperty.set(sequenceSymbols());
  }

  private String mergeSymbols(String symbols1, String symbols2) {
    StringBuilder merge = new StringBuilder();
    (symbols1 + symbols2).chars().distinct().forEach(c -> merge.append((char) c));
    return merge.toString();
  }

  private String sequenceSymbols() {
    return sequenceProperty.getValueSafe().chars().distinct()
        .mapToObj(c -> String.valueOf((char) c)).sorted().collect(Collectors.joining());
  }

  @FXML
  private void about() {
    new AboutDialog(layout.getScene().getWindow());
  }

  @FXML
  private void save() {
    new Alert(AlertType.WARNING, "Not programmed yet").showAndWait();
  }
}
