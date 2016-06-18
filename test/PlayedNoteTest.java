package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Tests the PlayedNote class
 */
public class PlayedNoteTest {

  Note invalidNote = new Note(Pitch.E, 11);
  Note c4;
  Note gSharp2;

  PlayedNote c4BeatOne;
  PlayedNote c4BeatSix;
  PlayedNote c4BeatSixCopy;
  PlayedNote gSharp2BeatTwo;
  PlayedNote gSharp2BeatTen;

  @Before
  public void init() {
    c4 = new Note(Pitch.C, 4);
    gSharp2 = new Note(Pitch.GSharp, 2);
    c4BeatOne = new PlayedNote(c4, 1, 1);
    c4BeatSix = new PlayedNote(c4, 3, 6);
    c4BeatSixCopy = new PlayedNote(c4, 3, 6);
    gSharp2BeatTwo = new PlayedNote(gSharp2, 2, 2);
    gSharp2BeatTen = new PlayedNote(gSharp2, 5, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidNote() {
    PlayedNote pn = new PlayedNote(invalidNote, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidDurationNegative() {
    PlayedNote pn = new PlayedNote(c4, -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidDurationZero() {
    PlayedNote pn = new PlayedNote(c4, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidBeatNegative() {
    PlayedNote pn = new PlayedNote(c4, 1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionInvalidBeatZero() {
    PlayedNote pn = new PlayedNote(c4, 1, 0);
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, c4BeatOne.getPitch());
    assertEquals(Pitch.GSharp, gSharp2BeatTen.getPitch());
  }

  @Test
  public void testGetOctave() {
    assertEquals(4, c4BeatOne.getOctave());
    assertEquals(2, gSharp2BeatTen.getOctave());
  }

  @Test
  public void testGetDuration() {
    assertEquals(1, c4BeatOne.getDuration());
    assertEquals(3, c4BeatSix.getDuration());
  }

  @Test
  public void testGetBeat() {
    assertEquals(2, gSharp2BeatTwo.getBeat());
    assertEquals(10, gSharp2BeatTen.getBeat());
  }

  @Test
  public void testSetPitch() {
    assertEquals(Pitch.C, c4BeatOne.getPitch());
    c4BeatOne.setPitch(Pitch.A);
    assertEquals(Pitch.A, c4BeatOne.getPitch());
    c4BeatOne.setPitch(Pitch.C);
    assertEquals(Pitch.C, c4BeatOne.getPitch());

    gSharp2BeatTen.setPitch(Pitch.F);
    assertEquals(Pitch.F, gSharp2BeatTen.getPitch());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetOctaveGreaterThanTen() {
    gSharp2BeatTwo.setOctave(11);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetOctaveZero() {
    gSharp2BeatTwo.setOctave(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetOctaveNegative() {
    gSharp2BeatTwo.setOctave(-3);
  }

  @Test
  public void testSetOctave() {
    assertEquals(4, c4BeatSix.getOctave());
    c4BeatSix.setOctave(2);
    assertEquals(2, c4BeatSix.getOctave());
    c4BeatSix.setOctave(4);
    assertEquals(4, c4BeatSix.getOctave());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetDurationNegative() {
    c4BeatOne.setDuration(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetDurationZero() {
    c4BeatOne.setDuration(0);
  }

  @Test
  public void testSetDuration() {
    assertEquals(5, gSharp2BeatTen.getDuration());
    gSharp2BeatTen.setDuration(11);
    assertEquals(11, gSharp2BeatTen.getDuration());
    gSharp2BeatTen.setDuration(5);
    assertEquals(5, gSharp2BeatTen.getDuration());

    c4BeatSix.setDuration(8);
    assertEquals(8, c4BeatSix.getDuration());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBeatNegative() {
    c4BeatOne.setBeat(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBeatZero() {
    c4BeatOne.setBeat(0);
  }

  @Test
  public void testSetBeat() {
    assertEquals(10, gSharp2BeatTen.getBeat());
    gSharp2BeatTen.setBeat(11);
    assertEquals(11, gSharp2BeatTen.getBeat());
    gSharp2BeatTwo.setBeat(10);
    assertEquals(10, gSharp2BeatTwo.getBeat());

    c4BeatSix.setBeat(9);
    assertEquals(9, c4BeatSix.getBeat());
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
    assertFalse(gSharp2BeatTwo.equals(new PlayedNote(new Note(Pitch.D, 9), 2, 2)));
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

  @Test
  public void testNextNote() {
    assertEquals(new Note(Pitch.CSharp, 4), c4BeatOne.nextNote());
    assertEquals(new Note(Pitch.A, 2), gSharp2BeatTen.nextNote());
  }
}