package ca.qc.ircm.compositionmap.sequence;

import static org.junit.Assert.assertNotNull;

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
