package cs3500.music.model;

/**
 * To represent the 10 octaves that are audible to human ears. This MusicModel is made for human
 * listeners therefore only the octaves heard by humans are represented. Each octave has 12 pitches
 * and the corresponding pitches in each octave have the same sound. For example, C in octave 4 has
 * the same pitch as C in octave 5 the later just sounds higher.
 */
public enum Octave {
  ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
  EIGHT("8"), NINE("9"), TEN("10");

  private final String value; // to represent the string value of this octave

  /**
   * To construct an Octave
   *
   * @param value the string representation of this Octave
   */
  Octave(String value) {
    this.value = value;
  }

  /**
   * Accessor method that returns the value of this Octave
   *
   * @return returns the string representation of this octave
   */
  @Override
  public String toString() {
    return this.value;
  }

}
