package cs3500.music.model;

import java.util.Objects;

/**
 * To represent a tone in music which is made up of both a pitch and an octave. There are twelve
 * distinct pitches in western style of music and 10 octaves in which a human can hear. This class
 * also implements the Comparable<Tone> interface to sucessfully sort a Collection of Tones. Some
 * class invariants could be that a user could input in a wrong Pitch and Octave but this is
 * prevented because they are both enum types and a user can only input in the values specified by
 * the enum.
 */
public final class Tone implements Comparable<Tone> {
  private Pitch pitch;
  private Octave octave;

  /**
   * to construct a tone object
   *
   * @param pitch  one of the twelve pitches in western music
   * @param octave one of the ten octaves in western music
   */
  public Tone(Pitch pitch, Octave octave) {
    this.pitch = pitch;
    this.octave = octave;
  }

  /**
   * to override the method toString() in order to return a string representation of a tone that
   * writes the pitch before the octave. If length of the tone is two characters long ("C4" for
   * example) then the string is padded by two in the front and one in the back. If the length of
   * the tone is 3 characters long, then it should be paded by one on either side. If the length of
   * the tone is 4 characters long, then it is padded by one before. The length of the tone should
   * never be longer than 4 characters, so it should not get to the else case.
   *
   * @return a String representation of a tone padded based off the length of the tone
   */
  @Override
  public String toString() {
    return this.pitch.toString() + this.octave.toString();
  }

  /**
   * To override the method equals to compare two Tones by their pitch and octave
   *
   * @param object the given object to compare this to
   * @return true if the pitch and octave of this tone and the given object parameter are the same;
   * false if the given object is not of the type Tone and if its pitch and octave are not the
   * same.
   */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Tone)) return false;
    Tone that = (Tone) object;
    return this.pitch.equals(that.pitch) &&
            this.octave.equals(that.octave);
  }

  /**
   * To Override hashcode to assign tones of the same pitch and octave the same value
   *
   * @return an integer that is unique for each tone type
   */
  @Override
  public int hashCode() {
    return Objects.hash(pitch, octave);
  }

  /**
   * overrides the method compareTo to sort a list of Tones. The tones are compared by the ordinal
   * value's of their octaves and pitches.
   *
   * @param that the given tone to compare this to
   * @return a negative number if this tone comes before that tone. A positive number if this tone
   * comes after this tone and zero if this and that tone are both the same tone.
   */
  @Override
  public int compareTo(Tone that) {
    return (this.octave.ordinal() - that.octave.ordinal()) * 12 +
            (this.pitch.ordinal() - that.pitch.ordinal());
  }

  /**
   * Accessor method that returns the pitch of this tone
   *
   * @return the pitch of this tone
   */
  Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Acessor method that returns the octave of this tone
   *
   * @return the octave of this tone
   */
  Octave getOctave() {
    return this.octave;
  }

  }

