package ca.qc.ircm.compositionmap.sequence;

import ca.qc.ircm.compositionmap.SequenceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for sequences.
 */
@Service
public class SequenceService {
  private static final Logger logger = LoggerFactory.getLogger(SequenceService.class);

  /**
   * Threshold used to guess if a sequence matches a specific type.
   */
  private static final double SEQUENCE_TYPE_ERROR_THRESHOLD = 0.01;

  /**
   * Guess sequence type of sequence.
   *
   * @param sequence
   *          sequence
   * @return returns most likely type of sequence
   */
  public Optional<SequenceType> type(String sequence) {
    String filteredSequence = sequence.replaceAll("\\W", "");
    Set<SequenceType> types = Stream.of(SequenceType.values()).filter(type -> {
      double error = error(filteredSequence, type.charPattern);
      logger.debug("type {} error: {}", type, error);
      return error < SEQUENCE_TYPE_ERROR_THRESHOLD;
    }).collect(Collectors.toSet());
    logger.debug("types below threshold: {}", types);
    return Arrays.stream(SequenceType.values()).filter(types::contains).findFirst();
  }

  private double error(String sequence, String allowedChars) {
    Pattern pattern = Pattern.compile(allowedChars);
    logger.debug("mismatch chars: {}",
        sequence.chars().filter(c -> !pattern.matcher(String.valueOf((char) c)).matches())
            .distinct().collect(() -> new StringBuilder(), (builder, c) -> builder.append((char) c),
                (b1, b2) -> b1.append(b2)));
    return 1 - 1.0
        * sequence.chars().filter(c -> pattern.matcher(String.valueOf((char) c)).matches()).count()
        / sequence.length();
  }

  /**
   * Creates composition map for the sequence.
   *
   * @param sequence
   *          sequence
   * @param addChars
   *          add these chars to the composition map
   * @param removeChars
   *          remove these chars from the composition map
   * @return composition map for the sequence
   */
  public String compositionMap(String sequence, char[] addChars, char[] removeChars) {
    List<Character> symbols = sequence.chars().distinct().sorted()
        .mapToObj(c -> Character.valueOf((char) c)).collect(Collectors.toList());
    if (addChars != null) {
      String.valueOf(addChars).chars().mapToObj(c -> Character.valueOf((char) c))
          .filter(c -> !symbols.contains(c)).forEach(c -> symbols.add(c));
    }
    if (removeChars != null) {
      String.valueOf(removeChars).chars().mapToObj(c -> Character.valueOf((char) c))
          .forEach(c -> symbols.remove(Character.valueOf(c)));
    }
    Collections.sort(symbols);
    List<String> lines = new ArrayList<>();
    // Column headers
    lines.add("Symbols\t" + IntStream.range(0, sequence.length()).mapToObj(v -> "S" + (v + 1))
        .collect(Collectors.joining("\t")));
    for (Character symbol : symbols) {
      lines.add(symbol + "\t" + sequence.chars().mapToObj(c -> symbol == c ? "1" : "0")
          .collect(Collectors.joining("\t")));
    }
    return lines.stream().collect(Collectors.joining(SystemUtils.LINE_SEPARATOR));
  }
}