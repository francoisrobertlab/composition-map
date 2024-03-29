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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestFxTestAnnotations
public class SaveCompositionMapTaskTest {
  private SaveCompositionMapTask task;
  @MockBean
  private SequenceService service;
  private String sequence;
  private String symbols;
  private Path output;
  @TempDir
  Path tempDir;

  /**
   * Before each test.
   */
  @BeforeEach
  public void beforeEach() {
    sequence = RandomStringUtils.randomAlphabetic(500).toUpperCase();
    symbols = sequence.chars().distinct().mapToObj(c -> String.valueOf((char) c))
        .collect(Collectors.joining());
    output = tempDir.resolve("map.txt");
    task = new SaveCompositionMapTask(service, sequence, symbols, output);
  }

  @Test
  public void call() throws Throwable {
    String map = "test\tsecond header\nline\t2\r\nline\t3";
    when(service.compositionMap(any(), any())).thenReturn(map);

    assertNull(task.call());

    verify(service).compositionMap(sequence, symbols);
    assertTrue(Files.exists(output));
    assertEquals(map, Files.readString(output));
    assertEquals(1.0, task.getProgress());
  }

  @Test
  public void call_Exception() throws Throwable {
    when(service.compositionMap(any(), any())).thenThrow(new IllegalStateException("test"));

    assertThrows(IllegalStateException.class, () -> task.call());

    verify(service).compositionMap(sequence, symbols);
    assertFalse(Files.exists(output));
  }
}
