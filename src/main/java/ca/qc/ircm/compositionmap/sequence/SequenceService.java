package ca.qc.ircm.compositionmap.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

/**
 * Service for sequences.
 */
@Service
public class SequenceService {
  /**
   * Returns symbols order for sequence type.
   *
   * @param type
   *          sequence type
   * @return symbols order for sequence type
   */
  public String symbolsOrder(SequenceType type) {
    if (type == null) {
      return "";
    }

    switch (type) {
      case DNA:
        return "ATCG";
      case RNA:
        return "AUCG";
      case PROTEIN:
        return "EDRKHYFWMILVQNCSTAGP";
      default:
        return "";
    }
  }

  /**
   * Creates composition map for the sequence.
   *
   * @param sequence
   *          sequence
   * @param symbols
   *          symbols to use
   * @return composition map for the sequence
   */
  public String compositionMap(String sequence, String symbols) {
    List<String> lines = new ArrayList<>();
    // Column headers
    lines.add("UNIQID\tName\t" + IntStream.range(0, sequence.length()).mapToObj(v -> "S" + (v + 1))
        .collect(Collectors.joining("\t")));
    for (int i = 0; i < symbols.length(); i++) {
      Character symbol = symbols.charAt(i);
      lines.add(symbol + "\t" + symbol + "\t" + sequence.chars()
          .mapToObj(c -> symbol == c ? "1" : "0").collect(Collectors.joining("\t")));
    }
    return lines.stream().collect(Collectors.joining(SystemUtils.LINE_SEPARATOR));
  }
}
