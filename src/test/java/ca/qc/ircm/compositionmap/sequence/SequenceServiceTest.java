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

    String[] lines = compositionMap.split("\n");
    assertEquals(5, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 1, lines[0].split("\t", -1).length);
    }
    assertEquals("Symbols", lines[0].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 1]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 1]);
    }
    assertEquals("T", lines[2].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[2].split("\t", -1)[i + 1]);
    }
    assertEquals("C", lines[3].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[3].split("\t", -1)[i + 1]);
    }
    assertEquals("G", lines[4].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'G' ? "1" : "0", lines[4].split("\t", -1)[i + 1]);
    }
  }

  @Test
  public void compositionMap_AddChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACGRT");

    String[] lines = compositionMap.split(SystemUtils.LINE_SEPARATOR);
    assertEquals(6, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 1, lines[0].split("\t", -1).length);
    }
    assertEquals("Symbols", lines[0].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 1]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 1]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 1]);
    }
    assertEquals("G", lines[3].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'G' ? "1" : "0", lines[3].split("\t", -1)[i + 1]);
    }
    assertEquals("R", lines[4].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("0", lines[4].split("\t", -1)[i + 1]);
    }
    assertEquals("T", lines[5].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[5].split("\t", -1)[i + 1]);
    }
  }

  @Test
  public void compositionMap_RemoveChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACT");

    String[] lines = compositionMap.split("\n");
    assertEquals(4, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 1, lines[0].split("\t", -1).length);
    }
    assertEquals("Symbols", lines[0].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 1]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 1]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 1]);
    }
    assertEquals("T", lines[3].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[3].split("\t", -1)[i + 1]);
    }
  }

  @Test
  public void compositionMap_AddAndRemoveChars() {
    String dna = "GAAGAAAATTTGTGAAAGAAGGACGGGTCA";

    String compositionMap = service.compositionMap(dna, "ACRT");

    String[] lines = compositionMap.split("\n");
    assertEquals(5, lines.length);
    for (int i = 0; i < lines.length; i++) {
      assertEquals(dna.length() + 1, lines[0].split("\t", -1).length);
    }
    assertEquals("Symbols", lines[0].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("S" + (i + 1), lines[0].split("\t", -1)[i + 1]);
    }
    assertEquals("A", lines[1].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'A' ? "1" : "0", lines[1].split("\t", -1)[i + 1]);
    }
    assertEquals("C", lines[2].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'C' ? "1" : "0", lines[2].split("\t", -1)[i + 1]);
    }
    assertEquals("R", lines[3].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals("0", lines[3].split("\t", -1)[i + 1]);
    }
    assertEquals("T", lines[4].split("\t", -1)[0]);
    for (int i = 0; i < dna.length(); i++) {
      assertEquals(dna.charAt(i) == 'T' ? "1" : "0", lines[4].split("\t", -1)[i + 1]);
    }
  }
}
