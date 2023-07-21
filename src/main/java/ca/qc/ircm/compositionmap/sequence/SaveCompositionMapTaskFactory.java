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
