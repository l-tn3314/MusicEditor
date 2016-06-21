package cs3500.music.model;


import java.util.Objects;

/**
 * To represent a Note in a music which has a Tone, a duration, and a starting beat or downbeat. A
 * note is present in the MusicModel if it is inputed by the user or if this note inputted has a
 * duration. If the note has a duration, then notes are inserted for the beats after it for as long
 * as the duration of the given note is, and their boolean flag playing is changed to true. A rest
 * is represented as the absence of a note or null. Some class invariants are that a user can put in
 * a duration that is either zero or negative because the type is an int. This is restricted in the
 * constructor. Another class invariant is that a user can input a negative downbeat since its type
 * is an int. This is also restricted in the constructor to prevent these errors at compile time.
 */
public class Note implements Comparable<Note> {
  private Tone tone; // column where placed
  private int downbeat; // the starting beat(starts from 0)
  private int duration; // how long each note is played
  private int instrument; // the number of instrument in midi
  private int volume; // how loud the note is

  /**
   * Constructs a Note
   *
   * @param tone       the pitch and octave of this Note
   * @param duration   how long this Note is played for
   * @param downbeat   the first beat this Note is played on
   * @param instrument instrument that plays this Note
   * @param volume     volume of this Note
   * @throws IllegalArgumentException if downbeat or duration is less than 1
   */
  public Note(Tone tone, int duration, int downbeat, int instrument, int volume)
          throws IllegalArgumentException {
    this.tone = tone;
    if (downbeat < 0) {
      throw new IllegalArgumentException("Can't have negative downbeat");
    }
    this.downbeat = downbeat;
    if (duration < 1) {
      throw new IllegalArgumentException("Invalid Duration");
    }
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
  }


  /**
   * To construct a Note
   *
   * @param tone     the pitch and octave of this Note
   * @param downbeat the first beat this Note is played on
   * @param duration how long this Note is played for. The input is restricted to numbers greater
   *                 than 1 because a duration of zero or a negative number is not possible
   * @throws IllegalArgumentException if the duration is less than one and the downbeat is less
   * than zero
   */
  public Note(Tone tone, int duration, int downbeat) throws IllegalArgumentException {
    this(tone, duration, downbeat, 1, 70);
  }

  /**
   * Constructs a copy of the given Note
   *
   * @param note Note to be copied
   */
  public Note(Note note) {
    this(new Tone(note.tone.getPitch(), note.tone.getOctave()), note.downbeat, note.duration);
  }

  /**
   * To Override the method equals for objects of the type Note. Two Notes are equal if their tone,
   * duration, and downbeat are the same.
   *
   * @return true if this Note and the given object have the same tone, duration, and downbeat;
   * false if the note and the given object do not have the same tone, duration, and downbeat or if
   * the given object is not of type object
   */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Note)) return false;
    Note that = (Note) object;
    return this.tone.equals(that.tone) &&
            this.duration == that.duration &&
            this.downbeat == that.downbeat;
  }

  /**
   * To Override the hashCode() method to assign notes of the same tone, duration and downbeat the
   * same value.
   *
   * @return an integer that is different for each different note (notes that do not have the same
   * tone, duration, downbeat)
   */
  @Override
  public int hashCode() {
    return Objects.hash(tone, duration, downbeat);
  }

  /**
   * Accessor method that returns a copy of this Note's tone to be accessed outside this class
   *
   * @return returns a cop of this note's tone
   * @throws IllegalArgumentException if the tone is null
   */
  public Tone getTone() throws IllegalArgumentException {
    if (tone == null) {
      throw new IllegalArgumentException("Tone is null");
    } else {
      return new Tone(tone.getPitch(), tone.getOctave());
    }
  }

  /**
   * Accessor method that returns the beat where this note starts playing.
   *
   * @return this note's downbeat
   */
  public int getDownbeat() {
    return this.downbeat;
  }

  /**
   * Accessor method that returns the duration of this note to be accessed outside of this class
   *
   * @return this notes's duration
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Accessor method that returns the instrument of this note to be accessed outside of this class
   *
   * @return this notes's instrument
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Accessor method that returns the volume of this note to be accessed outside of this class
   *
   * @return this notes's volume
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Set method that updates this Note's downbeat with the given downbeat
   *
   * @param newBeat the given downbeat to update this note's downbeat to.
   */
  void updateDownbeat(int newBeat) {
    downbeat = newBeat;
  }

  /**
   * Overrides compareTo to compare two notes
   *
   * @param t the note that is being compared to this
   * @return a 0 if the two notes are equal, a -1 one if this tone comes before the given one, and a
   * 1 if this tone comes after this one.
   */
  @Override
  public int compareTo(Note t) {
    return this.tone.compareTo(t.tone);
  }

  /**
   * Overrides toString to return a note's tone
   *
   * @return A String represnting this note
   */
  @Override
  public String toString() {
    return this.tone.toString();
  }
}
