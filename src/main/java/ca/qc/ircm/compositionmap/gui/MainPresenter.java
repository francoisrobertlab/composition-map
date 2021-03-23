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

import ca.qc.ircm.compositionmap.sequence.SaveCompositionMapTaskFactory;
import ca.qc.ircm.compositionmap.sequence.SequenceService;
import java.io.File;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
  @Inject
  private SaveCompositionMapTaskFactory taskFactory;
  private FileChooser outputChooser = new FileChooser();

  @FXML
  private void initialize() {
    menu.setUseSystemMenuBar(true);

    sequenceProperty.bindBidirectional(sequence.textProperty());
    symbolsProperty.bindBidirectional(symbols.textProperty());
    outputChooser.setTitle("Select Output");
    outputChooser.getExtensionFilters().add(new ExtensionFilter("Tab delimited", "*.txt"));
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
    return getSequence().chars().distinct().mapToObj(c -> String.valueOf((char) c)).sorted()
        .collect(Collectors.joining());
  }

  @FXML
  private void about() {
    new AboutDialog(layout.getScene().getWindow());
  }

  @FXML
  private void save() {
    JavafxUtils.setValidInitialDirectory(outputChooser);
    File output = outputChooser.showSaveDialog(layout.getScene().getWindow());
    if (output != null) {
      outputChooser.setInitialDirectory(output.getParentFile());
      Task<?> task = taskFactory.create(getSequence(), symbolsProperty.get(), output.toPath());
      task.stateProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == State.FAILED || newValue == State.SUCCEEDED
            || newValue == State.CANCELLED) {
          //progressDialog.close();
        }
        if (newValue == State.FAILED) {
          new Alert(AlertType.ERROR, "Could not save composition map to file " + output.getName())
              .showAndWait();
        } else if (newValue == State.SUCCEEDED) {
          new Alert(AlertType.INFORMATION, "Composition map saved to " + output.getName())
              .showAndWait();
        }
      });
      new Thread(task).start();
    }
  }

  private String getSequence() {
    return sequenceProperty.getValueSafe().replaceAll("[\\r\\n]", "");
  }

  final ObjectProperty<File> initialDirectoryProperty() {
    return outputChooser.initialDirectoryProperty();
  }
}
