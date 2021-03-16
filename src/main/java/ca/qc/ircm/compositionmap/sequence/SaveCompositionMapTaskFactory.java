package ca.qc.ircm.compositionmap.sequence;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates save composition map tasks.
 */
@Component
public class SaveCompositionMapTaskFactory {
  private final SequenceService service;

  @Autowired
  SaveCompositionMapTaskFactory(SequenceService service) {
    this.service = service;
  }

  /**
   * Creates a save composition map task.
   *
   * @param sequence
   *          sequence
   * @param symbols
   *          symbols to use
   * @param output
   *          where composition map is saved
   * @return save composition map task
   */
  public SaveCompositionMapTask create(String sequence, String symbols, Path output) {
    return new SaveCompositionMapTask(service, sequence, symbols, output);
  }
}
