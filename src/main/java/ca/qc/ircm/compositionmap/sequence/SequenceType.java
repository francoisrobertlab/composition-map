package ca.qc.ircm.compositionmap.sequence;

/**
 * Sequence types.
 */
public enum SequenceType {
  DNA("[ACGT]"), RNA("[ACGU]"), PROTEIN("[AC-IK-NP-TVWY]");

  /**
   * Pattern that matches accepted chars for sequence type.
   */
  public final String charPattern;

  SequenceType(String charPattern) {
    this.charPattern = charPattern;
  }
}
