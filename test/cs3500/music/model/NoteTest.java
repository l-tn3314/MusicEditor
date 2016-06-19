package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Tests the Note class
 */
public class NoteTest {

  Tone c4;
  Tone gSharp2;

  Note c4BeatOne;
  Note c4BeatSix;
  Note c4BeatSixCopy;
  Note gSharp2BeatTwo;
  Note gSharp2BeatTen;

  @Before
  public void init() {
    c4 = new Tone(Pitch.C, Octave.FOUR);
    gSharp2 = new Tone(Pitch.GSHARP, Octave.TWO);
    c4BeatOne = new Note(c4, 1, 1);
    c4BeatSix = new Note(c4, 3, 6);
    c4BeatSixCopy = new Note(c4, 3, 6);
    gSharp2BeatTwo = new Note(gSharp2, 2, 2);
    gSharp2BeatTen = new Note(gSharp2, 5, 10);
  }

  // to test the exception when the given duration is negative
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidDurationNegative() {
    Note pn = new Note(c4, -1, 2);
  }

  // to test the exception when the given duration is zero
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidDurationZero() {
    Note pn = new Note(c4, 0, 2);
  }

  // to test when the given downbeat is negative
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidBeatNegative() {
    Note pn = new Note(c4, 1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidBeatZero() {
    Note pn = new Note(c4, 1, 0);
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, c4BeatOne.getTone().getPitch());
    assertEquals(Pitch.GSHARP, gSharp2BeatTen.getTone().getPitch());
  }

  @Test
  public void testGetOctave() {
    assertEquals(Octave.FOUR, c4BeatOne.getTone().getOctave());
    assertEquals(Octave.TEN, gSharp2BeatTen.getTone().getOctave());
  }

  @Test
  public void testGetDuration() {
    assertEquals(1, c4BeatOne.getDuration());
    assertEquals(3, c4BeatSix.getDuration());
  }

  @Test
  public void testGetBeat() {
    assertEquals(2, gSharp2BeatTwo.getDownbeat());
    assertEquals(10, gSharp2BeatTen.getDownbeat());
  }

  @Test
  public void testOtherObject() {
    assertFalse(c4BeatSix.equals("hi"));
  }

  @Test
  public void testEqualsSelf() {
    assertTrue(c4BeatSix.equals(c4BeatSix));
  }

  @Test
  public void testEqualSamePitchSameOctave() {
    assertFalse(c4BeatOne.equals(c4BeatSix));
  }

  @Test
  public void testEqualSameDurationSameBeat() {
    assertFalse(gSharp2BeatTwo.equals(new Note(new Tone(Pitch.D, Octave.NINE), 2, 2)));
  }

  @Test
  public void testEqualsCopy() {
    assertTrue(c4BeatSix.equals(c4BeatSixCopy));
  }

  @Test
  public void testHashCodeSameObject() {
    assertEquals(gSharp2BeatTen.hashCode(), gSharp2BeatTen.hashCode());
  }

  @Test
  public void testHashCode() {
    assertEquals(Objects.hash(gSharp2, 5, 10), gSharp2BeatTen.hashCode());
  }

  @Test
  public void testHashCodeEqualObject() {
    assertEquals(c4BeatSix.hashCode(), c4BeatSixCopy.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("C4", c4BeatSix.toString());
    assertEquals("G#2", gSharp2BeatTwo.toString());
  }

  @Test
  public void testCompareToSelf() {
    assertEquals(0, c4BeatSix.compareTo(c4BeatSix));
  }

  @Test
  public void testCompareToCopy() {
    assertEquals(0, c4BeatSix.compareTo(c4BeatSixCopy));
  }

  @Test
  public void testCompareToSameNote() {
    assertEquals(0, c4BeatOne.compareTo(c4BeatSix));
  }

  @Test
  public void testCompareToDifferentNote() {
    assertEquals(16, c4BeatOne.compareTo(gSharp2BeatTwo));
    assertEquals(-16, gSharp2BeatTen.compareTo(c4BeatSix));
  }
}