package ca.qc.ircm.compositionmap.sequence;

import static ca.qc.ircm.compositionmap.sequence.SequenceType.DNA;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.PROTEIN;
import static ca.qc.ircm.compositionmap.sequence.SequenceType.RNA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.qc.ircm.compositionmap.test.config.ServiceTestAnnotations;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTestAnnotations
public class SequenceServiceTest {
  @Autowired
  private SequenceService service;

  @Test
  public void type_DnaPerfect() {
    @SuppressWarnings("checkstyle:linelength")
    String dna =
        "GAAACCATGTTATGTATGCACTAGGTCAACAATAGGACATAGCCTTGTAGTTAACACGTAGCCCGGTCGTATAAGTACAGTAGACCCTTCGCCGGCATCC";

    assertEquals(DNA, service.type(dna).orElse(null));
  }

  @Test
  public void type_DnaWithNonalpha() {
    @SuppressWarnings("checkstyle:linelength")
    String dna =
        "CTCACTGTAATACTCA-ATTACCGACGGGTTGGAACGGAAT+CCGTCTAAGTCATGGT--TCGTACAAGCGACGCTTGTCAC$CAACGCAGTCACCTTTGGAATT";

    assertEquals(DNA, service.type(dna).orElse(null));
  }

  @Test
  public void type_DnaBelowThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String dna =
        "GCTTTGTGCCGTACGGGTTGACGGTAGAGACATGTCCAAAMCTCCGCGTAAAGTAACCTCGAAGACGTTCCTCCAAGGACTCACATCTCCCGTTTACTTCAAACGGAAAA";

    assertEquals(DNA, service.type(dna).orElse(null));
  }

  @Test
  public void type_DnaAboveThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String dna =
        "GCTTTGTGCCGTACGGGTTGACGGTASAGACATGTCCAAAMCYCCGCGTAAAGTAACCTCGAAGACGTTCCTCCAAGGACTCACATCTCCCGTTTACTTCAAACGGAAAA";

    SequenceType type = service.type(dna).orElse(null);
    assertTrue(type == null || type == PROTEIN);
  }

  @Test
  public void type_RnaPerfect() {
    @SuppressWarnings("checkstyle:linelength")
    String rna =
        "CCCUAACGCCAUUAAUCAGGAGCGCUUACAGGUCAAAUAUUUACUUGAAUCUCCCUCUUCGUUAUGCUAGCUAUUGACCUUAGUAACCUUUAAGCGCCCA";

    assertEquals(RNA, service.type(rna).orElse(null));
  }

  @Test
  public void type_RnaPerfectWithNonalpha() {
    @SuppressWarnings("checkstyle:linelength")
    String rna =
        "UUAUCGACCCAGGUCGCAU-AACGAGAGUAGUGCGUCAUGU+CUCUCUGCGUUCGUGCGUUGAUUG*GCCU$ACGGACAUG#CUAAGGCGGCCGGGACCAGAGGG";

    assertEquals(RNA, service.type(rna).orElse(null));
  }

  @Test
  public void type_RnaBelowThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String rna =
        "UAGCUGCCGAAUCGUCUGAAUACCUGCAUCAGUGUCAUAUGAAGUUCCGUGAAMCACCUGACACAACGUCUCGCCGUCUCAACGUGUGGUCCUGCCCGAAGAUCGUUUCC";

    assertEquals(RNA, service.type(rna).orElse(null));
  }

  @Test
  public void type_RnaAboveThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String rna =
        "UAGCUGCCGAAUCGUCUGAAUACCUGCAUCAGUGUCAUAUGAAGWUCCRUGAAMCACCUGACACAACGUCUCGCCGUCUCAACGUGUGGUCCUGCCCGAAGAUCGUUUCC";

    assertEquals(null, service.type(rna).orElse(null));
  }

  @Test
  public void type_ProteinPerfect() {
    @SuppressWarnings("checkstyle:linelength")
    String protein =
        "MAEKGNDKIFSLIVDIVENSAAAWYERPLLFRQAATVHKCKLNIQSSSLIMMEGGLQLIGTAKVSSVSRRVYTTQAFDLHKFKGIKGLNKLADKNEPCAGASDQEEDVRLRCDPVATRNVETATWKHQSGTEIHNKIASYVKRASLRAPDNTEIVHADSTNTYDHVKELAELHHKHVHDVIMDGQDWLPIAADGIGRQDAGSHGGITRPDCVTALLTGKPGNRAGRAPFEPSANCVRPNTFFEKVIREEILKWSIIFNYSRKEGMPLSVRHGESDHLEDEPVSLYKRVNNGVSSKIPPHGRNSKPNSRKPYASRFCSDDEDYGKDAPPTSTNEPSTGSAENNVIGDAPVLNASSKVSLEDKQYNQRGYNGQEVKVQSLSYGGLRCGGARYRVVLSRRLGGVLLNLNSIWWANGFFEATDDGIDMVDIVPTPAIAMLELDLAKNKVLNPISSRGDGARIIVNMQVEGISFAIARLLMHIEAIAEVRFLSEIVAFAGRFRIEKVGQLARYRKMKLAGARTTMTNTYRLTELGAEMFDNEAEKAAISAGNFGGSDLRQPLIPGLLPGLQREFNLYQGRFMPSAVLAADAGKVSFEFVSGLNSISIKWVTIENKGITAVTALLKHGKGETGPPANLHSAWLLMFMRFQLGKFKVLAGLVGRTELLQRGLALIFYMAGKSKVEPFWNCFPVFGTNKAFNFGAQLNILLSPCEMAELVVLRVKDGQHIWTKPITSEFYALKGHPRAKSRISPILLHTGGFENVQKGLFEHSSRLARVMTSAAYLFFEIVVFPKGINAADSAMPFKESALNMDQMIEETVGGGRSGPIYMLATIAAVILTDAMTFLDSLKRLSKSYKIISEVGSRAETFESTAIGRKTYRKFIESDARLALSEPIKKEEYTCAIDQCGTHQDFSTEAERNPFAKVLIQNVTIGQIQIENKLTPAVNPQEVIKRVVASLLSIMEHDKGYVARRQFVTFLQTLQPVGAKPMYITMRHQEKFEFRLQLGG";

    assertEquals(PROTEIN, service.type(protein).orElse(null));
  }

  @Test
  public void type_ProteinWithNonAlpha() {
    @SuppressWarnings("checkstyle:linelength")
    String protein =
        "MSEHRHRVELFVKASADCIH-AVEEQADRPKMKSLEAVKP+AIGYISAIVGPDTSSPRDHPKAEDLPLL?FKVIEANPRKQNRALGEDFFYRELVQHVLEWK*GVTKLYYNGRIASIMIAPEIPINMEV$SIGCDPEEARAWRILSFVSDLEARYCPARGDQAQINCDKFLDFNGPSAYGCWIVAHTLFADTTLQVKLGLKFVVHADLSYRLRVKCPSLVPVPDNMFWSIRREGDEEELQEANKIFDCIYQVREFAVVMEDSASPKIRKRTGPVFGRFLNGKAGWFVVWLFQADILKVAGYGRSTPPRKADHEIPDREDIRMESQWSFIKCYGRIPDLALNKGLVVNALHNRILLHIIWWQSDIKSPDGSQLHSHPIRSASVGRVQDNKELTCPIIEAASFKPGTIASNKRTAKRNLCITIACADDLVYGMNIHGDLLAKLEVTVQSADVTTEDGTKMMQSETEATLLGSNEVEVLERTLRGEEWRSLQRQQKCSMTDTYLGYKAKGDRFVFYEQIARRKEVSFDEALYQGFAWGHPEIVVFIKLSADPKCEVQKAGRRAAGVQFECHALQNVCGVGWAFRCTKIPYASSGKVTLGRQCDAKRFVKEEKKPNEDKRVETYRDVAQRSTCGPGHASTMLAGSTAKLYTGGVMLLSYQQPFMGKGDKYKDASNNSTEATLGEECVLCTDVGMMCVQPGDSVFLDIDLTLLVKDDTVVSYPLALWSWVATDGVQQYLVGTIIICPVDGDLSLEILPMSTPMRDIVLITSATLQHTLGGEVLDEAPDNKHLLETQIAIPRIYDHFLVAIAVGELDGGEENAIPMSLVWYPIFEEPEENLLNDQPMVVGIGVLGSSKWEATGELMHIRGIEEPPINCYAKAQAEVRYQLFERCRGPGRNYIRKLWDECRILTLQSIQEIRANELLLAFEAPYCQTNLSCLTMSTYKSAGREKDTYELQCILIHATWFSPISNKWPGIKGFEGTNAFGRDYKQVQTKHLGYQVYLQSSRLE";

    assertEquals(PROTEIN, service.type(protein).orElse(null));
  }

  @Test
  public void type_ProteinBelowThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String protein =
        "MAEKGNDKIFSLJVDIVENSAAAWYERPLLFRQAATVHKCKLNIQSSSLIMMEGGLQLIGTAKVSSVSRRVYTTQAFBLHKFKGIKGLNKLADKNZPCAGASDQEEDVRLRCDPVATRNVETATWKHQSGTEIHNKIASYVKRASLRAPDNTEIVHADSTNTYDHVKELAELHHKHVHDVIMDGQDWLPIAADGIGRQDAGSHGGITRPDCVTALLTGKPGNRAGRAPFEPSANCVRPNTFFEKVIREEILKWSIIFNYSRKEGMPLSVRHGESDHLEDEPVSLYKRVNNGVSSKIPPHGRNSKPNSRKPYASRFCSDDEDYGKDAPPTSTNEPSTGSAENNVIGDAPVLNASSKVSLEDKQYNQRGYNGQEVKVQSLSYGGLRCGGARYRVVLSRRLGGVLLNLNSIWWANGFFEATDDGIDMVDIVPTPAIAMLELDLAKNKVLNPISSRGDGARIIVNMQVEGISFAIARLLMHIEAIAEVRFLSEIVAFAGRFRIEKVGQLARYRKMKLAGARTTMTNTYRLTELGAEMFDNEAEKAAISAGNFGGSDLRQPLIPGLLPGLQREFNLYQGRFMPSAVLAADAGKVSFEFVSGLNSISIKWVTIENKGITAVTALLKHGKGETGPPANLHSAWLLMFMRFQLGKFKVLAGLVGRTELLQRGLALIFYMAGKSKVEPFWNCFPVFGTNKAFNFGAQLNILLSPCEMAELVVLRVKDGQHIWTKPITSEFYALKGHPRAKSRISPILLHTGGFENVQKGLFEHSSRLARVMTSAAYLFFEIVVFPKGINAADSAMPFKESALNMDQMIEETVGGGRSGPIYMLATIAAVILTDAMTFLDSLKRLSKSYKIISEVGSRAETFESTAIGRKTYRKFIESDARLALSEPIKKEEYTCAIDQCGTHQDFSTEAERNPFAKVLIQNVTIGQIQIENKLTPAVNPQEVIKRVVASLLSIMEHDKGYVARRQFVTFLQTLQPVGAKPMYITMRHQEKFEFRLQLGG";

    assertEquals(PROTEIN, service.type(protein).orElse(null));
  }

  @Test
  public void type_ProteinAboveThreshold() {
    @SuppressWarnings("checkstyle:linelength")
    String protein =
        "MAEKGNDKIFSLJVDIVENSAAAWYERPLLFRQAATVHKCKLNIQSSSLIMMEGGLQLIGTAKVSSVSRRVYTTQAFBLHKFKGIKGLNKLADKNZPCAGASDQEEDVRLRCDPVATRNVETATWKHQSGTEIHNKIASYVKRASLRAPDNTEIVHADSTNTYDHVKELAELHHKHVHDVIMDGQDWLPIAADGIGRQDAGSHGGITRPDCVTALLTGKPGNRAGRAPFEPSANCVRPNTFFEKVIREEILKWSIIFNYSRKEGMPLSVRHGESDHLEDEPVSLYKRVNNGVSSKIPPHGRNSKPNSRKPYASRFCSDDEDYGKDAPPTSTNEPSTGSAENNVIGDAPVLNASSKVSLEDKQYNQRGYNGQEVKVQSLSYGGLRCGGARYRVVLSRRLGGVLLNLNSIWWANGFFEATDDGIDMVDIVPTPAIAMLELDLAKNKVLNPISSRGDGARIIVNMQVEGISFAIARLLMHIEAIAEVRFLSEIVAFAGRFRIEKVGQLARYRKMKLAGARTTMTNTYRLTELGAEMFDNEAEKAAISAGNFGGSDLRQPLIPGLLPGLQREFNLYQGRFMPSAVLAADAGKVSFEFVSGLNSISIKWVTIENKGITAVTALLKHGKGETGPPANLHSAWLLMFMRFQLGKFKVLAGLVGRTELLQRGLALIFYMAGKSKVEPFWNCFPVFGTNKAFNFGAQLNILLSPCEMAELVVLRVKDGQHIWTKPITSEFYALKGHPRAKSRISPILLHTGGFENVQKGLFEHSSRLARVMTSAAYLFFEIVVFPKGINAADSAMPFKESALNMDQMIEETVGGGRSGPIYMLATIAAVILTDAMTFLDSLKRLSKSYKIISEVGSRAETFESTAIGRKTYRKFIESDARLALSEPIKKEEYTCAIDQCGTHQDFSTEAERNPFAKVLIQNVTIGQIQIENKLTPAXBPZEVIKRVVASLLSJMEHBKGYVARRQFVTFLQTJQPVGAKPMYITMRHZZKFEFRLQLXG";

    assertEquals(null, service.type(protein).orElse(null));
  }

  @Test
  public void type_Other() {
    @SuppressWarnings("checkstyle:linelength")
    String other =
        "QBGURATSLZPFRTMQHAZBPJXWARNQYKNXVLRGXNZXPHQZOLZYDZLCHEASLRVMVRRYKMCUNVVEPHVTZOCEGCBRAVJTBWMPJMUYXHFOYHCVMQBZJJGOEQNOJTMNMTWDSRGWCCNEKLJDUAOBBROMAWBKQGFYXLJRZRLHNBZXEUFMRJUECVMHSHSNJALOMSIDWULOZCMOERCF";

    assertEquals(null, service.type(other).orElse(null));
  }

  @Test
  public void type_Empty() {
    assertEquals(null, service.type("").orElse(null));
  }

  @Test
  public void type_Null() {
    assertEquals(null, service.type(null).orElse(null));
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
