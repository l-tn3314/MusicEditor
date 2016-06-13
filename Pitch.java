package cs3500.music.model;

/**
 * To represent the 12 distinct pitches in Western Music each with a distinct sound. The 13th pitch
 * sounds the same as the first one but its just an octave higher. The following notes are the only
 * notes supported by this MusicModel "C C♯ D D♯ E F F♯ G G♯ A A♯ B". Sharps, Naturals, and any
 * other pitch that is equivalent to these sounds are not supported by this implementation.
 */
public enum Pitch {
  C("C"), CSHARP("C♯"), D("D"), DSHARP("D♯"), E("E"), F("F"), FSHARP("F♯"),
  G("G"), GSHARP("G♯"), A("A"), ASHARP("A♯"), B("B");

  private final String value; // to represent the string value of this Pitch

  /**
   * To construct a Pitch
   *
   * @param value the String value of this Pitch
   */
  Pitch(String value) {
    this.value = value;
  }

  /**
   * An accessor method that returns the string value of this Pitch
   *
   * @return the string representation of this Pitch
   */
  @Override
  public String toString() {
    return this.value;
  }

}
