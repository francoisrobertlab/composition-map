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

import static ca.qc.ircm.compositionmap.sequence.SequenceType.DNA;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.PROTEIN;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.RNA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.compositionmap.test.config.ServiceTestAnnotations;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTestAnnotations
public class SequenceServiceTest {
  @Autowired
  private SequenceService service;

  @Test
  public void symbolsOrder_Dna() {
    assertEquals("ATCG", service.symbolsOrder(DNA));
  }

  @Test
  public void symbolsOrder_Rna() {
    assertEquals("AUCG", service.symbolsOrder(RNA));
  }

  @Test
  public void symbolsOrder_Protein() {
    assertEquals("EDRKHYFWMILVQNCSTAGP", service.symbolsOrder(PROTEIN));
  }

  @Test
  public void symbolsOrder_Null() {
    assertEquals("", service.symbolsOrder(null));
  }

  @Test
  public void compositionMap() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ATCG");

    String[] lines = compositionMap.split(SystemUtils.LINE_SEPARATOR);
    assertEquals(5, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 2, lines[0].split("\t", -1).length);
    }
    assertEquals("UNIQID", lines[0].split("\t", -1)[0]);
    assertEquals("Name", lines[0].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 2]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    assertEquals("A", lines[1].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 2]);
    }
    assertEquals("T", lines[2].split("\t", -1)[0]);
    assertEquals("T", lines[2].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[2].split("\t", -1)[i + 2]);
    }
    assertEquals("C", lines[3].split("\t", -1)[0]);
    assertEquals("C", lines[3].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[3].split("\t", -1)[i + 2]);
    }
    assertEquals("G", lines[4].split("\t", -1)[0]);
    assertEquals("G", lines[4].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'G' ? "1" : "0", lines[4].split("\t", -1)[i + 2]);
    }
  }

  @Test
  public void compositionMap_AddChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACGRT");

    String[] lines = compositionMap.split(SystemUtils.LINE_SEPARATOR);
    assertEquals(6, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 2, lines[0].split("\t", -1).length);
    }
    assertEquals("UNIQID", lines[0].split("\t", -1)[0]);
    assertEquals("Name", lines[0].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 2]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    assertEquals("A", lines[1].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 2]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    assertEquals("C", lines[2].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 2]);
    }
    assertEquals("G", lines[3].split("\t", -1)[0]);
    assertEquals("G", lines[3].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'G' ? "1" : "0", lines[3].split("\t", -1)[i + 2]);
    }
    assertEquals("R", lines[4].split("\t", -1)[0]);
    assertEquals("R", lines[4].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("0", lines[4].split("\t", -1)[i + 2]);
    }
    assertEquals("T", lines[5].split("\t", -1)[0]);
    assertEquals("T", lines[5].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[5].split("\t", -1)[i + 2]);
    }
  }

  @Test
  public void compositionMap_RemoveChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACT");

    String[] lines = compositionMap.split(SystemUtils.LINE_SEPARATOR);
    assertEquals(4, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 2, lines[0].split("\t", -1).length);
    }
    assertEquals("UNIQID", lines[0].split("\t", -1)[0]);
    assertEquals("Name", lines[0].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 2]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    assertEquals("A", lines[1].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 2]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    assertEquals("C", lines[2].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 2]);
    }
    assertEquals("T", lines[3].split("\t", -1)[0]);
    assertEquals("T", lines[3].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[3].split("\t", -1)[i + 2]);
    }
  }

  @Test
  public void compositionMap_AddAndRemoveChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACRT");

    String[] lines = compositionMap.split(SystemUtils.LINE_SEPARATOR);
    assertEquals(5, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 2, lines[0].split("\t", -1).length);
    }
    assertEquals("UNIQID", lines[0].split("\t", -1)[0]);
    assertEquals("Name", lines[0].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 2]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    assertEquals("A", lines[1].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 2]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    assertEquals("C", lines[2].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 2]);
    }
    assertEquals("R", lines[3].split("\t", -1)[0]);
    assertEquals("R", lines[3].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("0", lines[3].split("\t", -1)[i + 2]);
    }
    assertEquals("T", lines[4].split("\t", -1)[0]);
    assertEquals("T", lines[4].split("\t", -1)[1]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[4].split("\t", -1)[i + 2]);
    }
  }
}
