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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.qc.ircm.compositionmap.test.config.TestFxTestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestFxTestAnnotations
public class SaveCompositionMapTaskFactoryTest {
  @Autowired
  private SaveCompositionMapTaskFactory factory;
  @MockBean
  private SequenceService service;
  private String sequence;
  private String symbols;
  private Path output;

  /**
   * Before each test.
   */
  @BeforeEach
  public void beforeEach() {
    sequence = RandomStringUtils.randomAlphabetic(500).toUpperCase();
    symbols = sequence.chars().distinct().mapToObj(c -> String.valueOf((char) c))
        .collect(Collectors.joining());
    output = Paths.get("map.txt");
  }

  @Test
  public void create() {
    assertNotNull(factory.create(sequence, symbols, output));
  }
}
