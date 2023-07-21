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
