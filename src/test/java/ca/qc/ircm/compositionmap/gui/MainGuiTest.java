package ca.qc.ircm.compositionmap.gui;

import static ca.qc.ircm.compositionmap.sequence.SequenceType.DNA;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.PROTEIN;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.RNA;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.compositionmap.sequence.SaveCompositionMapTask;
import ca.qc.ircm.compositionmap.sequence.SaveCompositionMapTaskFactory;
import ca.qc.ircm.compositionmap.sequence.SequenceService;
import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

@TestFxTestAnnotations
public class MainGuiTest {
  private MainGui gui;
  private Stage stage;
  @MockBean
  private SaveCompositionMapTaskFactory factory;
  @MockBean
  private SequenceService service;

  /**
   * Before each test.
   *
   * @param stage
   *          stage
   */
  @Start
  public void beforeEach(Stage stage) {
    this.stage = stage;
    gui = new MainGui(stage);
    gui.show();
  }

  @Stop
  public void afterEach() {
    stage.hide();
  }

  @Test
  public void init(FxRobot robot) {
    assertNotNull(stage.getScene());
    stage.getScene().getStylesheets().contains("error.css");
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    assertEquals(visualBounds.getHeight(), stage.getMaxHeight());
    assertEquals(visualBounds.getWidth(), stage.getMaxWidth());
    if (SystemUtils.IS_OS_MAC_OSX) {
      assertTrue(robot.lookup(".menu").queryAs(MenuBar.class).isUseSystemMenuBar());
    }
  }

  @Test
  public void show(FxRobot robot) {
    robot.interact(() -> stage.hide());
    robot.interact(() -> gui.show());
    assertTrue(stage.isShowing());
  }

  @Test
  public void hide(FxRobot robot) {
    robot.interact(() -> stage.show());
    robot.interact(() -> gui.hide());
    assertFalse(stage.isShowing());
  }

  @Test
  public void proteinSymbols(FxRobot robot) {
    String symbols = "NMVBXCZKLHJFGSDA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".protein");
    verify(service).symbolsOrder(PROTEIN);
    assertEquals(symbols, robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void proteinSymbols_WithSequence(FxRobot robot) {
    robot.lookup("#sequence").queryTextInputControl().setText("OQADJGNCNAVPFAFLJVFNVF");
    String symbols = "NMVBXCZKLHJFGSDA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".protein");
    verify(service).symbolsOrder(PROTEIN);
    assertEquals(symbols + "OPQ", robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void dnaSymbols(FxRobot robot) {
    String symbols = "GCTA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".dna");
    verify(service).symbolsOrder(DNA);
    assertEquals(symbols, robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void dnaSymbols_WithSequence(FxRobot robot) {
    robot.lookup("#sequence").queryTextInputControl().setText("GTCCADTTGBCCA");
    String symbols = "GCTA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".dna");
    verify(service).symbolsOrder(DNA);
    assertEquals(symbols + "BD", robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void rnaSymbols(FxRobot robot) {
    String symbols = "GCUA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".rna");
    verify(service).symbolsOrder(RNA);
    assertEquals(symbols, robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void rnaSymbols_WithSequence(FxRobot robot) {
    robot.lookup("#sequence").queryTextInputControl().setText("GUCACGUHUAGUBC");
    String symbols = "GCUA";
    when(service.symbolsOrder(any())).thenReturn(symbols);
    robot.clickOn(".rna");
    verify(service).symbolsOrder(RNA);
    assertEquals(symbols + "BH", robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void otherSymbols(FxRobot robot) {
    robot.clickOn(".other");
    assertEquals("", robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void otherSymbols_WithSequence(FxRobot robot) {
    robot.lookup("#sequence").queryTextInputControl().setText("GUCACGUHUAGUBC");
    robot.clickOn(".other");
    assertEquals("ABCGHU", robot.lookup("#symbols").queryTextInputControl().getText());
  }

  @Test
  public void about(FxRobot robot) {
    MenuBar bar = robot.lookup(".menu").queryAs(MenuBar.class);
    Menu help = bar.getMenus().filtered(menu -> menu.getStyleClass().contains("help")).get(0);
    MenuItem about =
        help.getItems().filtered(item -> item.getStyleClass().contains("about")).get(0);
    robot.interact(() -> about.fire());
    Optional<Node> aboutDialog = robot.lookup(".about-dialog").tryQuery();
    assertTrue(aboutDialog.isPresent());
    assertTrue(aboutDialog.get().getScene().getWindow().isShowing());
    assertTrue(aboutDialog.get().getScene().getWindow().isFocused());
  }

  @Test
  public void save(FxRobot robot, @TempDir Path folder) throws Throwable {
    String symbols = "ATCG";
    String outputContent = RandomStringUtils.random(1000);
    when(factory.create(any(), any(), any())).then(a -> {
      return new SaveCompositionMapTask(service, a.getArgument(0), a.getArgument(1),
          a.getArgument(2)) {};
    });
    when(service.symbolsOrder(any())).thenReturn(symbols);
    when(service.compositionMap(any(), any())).thenReturn(outputContent);
    gui.presenter.initialDirectoryProperty().setValue(folder.toFile());
    String sequence = "CGCGCAGGCACCGTAATCCTTCCACCGTGA";
    robot.lookup("#sequence").queryTextInputControl().setText(sequence);
    robot.clickOn(".dna");

    robot.clickOn(".save");
    // Enter "dna" for dna.txt output file in FileChooser.
    robot.sleep(3, SECONDS);
    robot.push(KeyCode.D, KeyCode.N, KeyCode.A);
    robot.push(KeyCode.ENTER);
    robot.sleep(3, SECONDS);

    Path output = folder.resolve("dna.txt");
    verify(factory).create(sequence, symbols, output.toRealPath());
    verify(service).symbolsOrder(DNA);
    verify(service).compositionMap(sequence, symbols);
    assertTrue(Files.exists(output));
    assertEquals(outputContent, Files.readString(output));
    assertTrue(robot.lookup(".alert.information").tryQuery().isPresent());
    robot.clickOn(robot.lookup(".alert").lookup(".button").queryButton());
  }
}
