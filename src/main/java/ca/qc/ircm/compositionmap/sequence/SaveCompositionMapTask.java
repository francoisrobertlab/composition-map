package ca.qc.ircm.compositionmap.sequence;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javafx.concurrent.Task;

/**
 * Save composition map task.
 */
public class SaveCompositionMapTask extends Task<Void> {
  private final SequenceService service;
  private final String sequence;
  private final String symbols;
  private final Path output;

  protected SaveCompositionMapTask(SequenceService service, String sequence, String symbols,
      Path output) {
    this.service = service;
    this.sequence = sequence;
    this.symbols = symbols;
    this.output = output;
  }

  @Override
  protected Void call() throws Exception {
    String compositionMap = service.compositionMap(sequence, symbols);
    updateProgress(0.3, 1.0);
    Files.write(output, compositionMap.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    updateProgress(1.0, 1.0);
    return null;
  }
}
