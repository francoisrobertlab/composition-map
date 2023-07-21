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
