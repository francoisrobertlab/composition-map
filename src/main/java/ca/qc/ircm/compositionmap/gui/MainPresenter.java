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

import ca.qc.ircm.compositionmap.SequenceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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
  private ObjectProperty<SequenceType> sequenceTypeProperty = new SimpleObjectProperty<>();
  @FXML
  private BorderPane layout;
  @FXML
  private MenuBar menu;
  @FXML
  private TextArea sequence;
  @FXML
  private ToggleGroup sequenceType;
  @FXML
  private RadioButton protein;
  @FXML
  private RadioButton dna;
  @FXML
  private RadioButton rna;
  @FXML
  private RadioButton other;
  @FXML
  private TextField addChars;
  @FXML
  private TextField removeChars;

  @FXML
  private void initialize() {
    if (SystemUtils.IS_OS_MAC_OSX) {
      menu.setUseSystemMenuBar(true);
    }

    sequenceProperty.bindBidirectional(sequence.textProperty());
    protein.setUserData(SequenceType.PROTEIN);
    dna.setUserData(SequenceType.DNA);
    rna.setUserData(SequenceType.RNA);
    sequenceType.selectedToggleProperty().addListener(
        (ob, ov, nv) -> sequenceTypeProperty.setValue((SequenceType) nv.getUserData()));
    sequenceTypeProperty.addListener((ob, ov, nv) -> sequenceType.getToggles().stream()
        .filter(toggle -> toggle.getUserData() == nv).findFirst()
        .ifPresent(toggle -> toggle.setSelected(true)));

    // Default values.
    other.setSelected(true);
  }

  @FXML
  private void about() {
    new AboutDialog(layout.getScene().getWindow());
  }

  List<String> getAddChars() {
    String value = addChars.textProperty().get();
    return stringToList(value);
  }

  List<String> getRemoveChars() {
    String value = removeChars.textProperty().get();
    return stringToList(value);
  }

  private ObservableList<String> stringToList(String value) {
    return FXCollections.observableArrayList(
        value.isEmpty() ? new ArrayList<>() : Arrays.asList(value.split("[, ]+", -1)));
  }

  private String listToString(List<? extends String> value) {
    return value.stream().collect(Collectors.joining(", "));
  }
}
