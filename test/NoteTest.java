package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Tests the Note class
 */
public class NoteTest {

  Note c4;
  Note c4Copy;
  Note c4Copy2;
  Note f3Sharp;
  Note f4Sharp;
  Note a6;
  Note a5;

  @Before
  public void init() {
    c4 = new Note(Pitch.C, 4);
    c4Copy = new Note(Pitch.C);
    c4Copy2 = new Note(c4);
    f3Sharp = new Note(Pitch.FSharp, 3);
    f4Sharp = new Note(Pitch.FSharp, 4);
    a6 = new Note(Pitch.A, 6);
    a5 = new Note(Pitch.A, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteConstructorNegative() {
    Note n = new Note(Pitch.B, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteConstructorZero() {
    Note n = new Note(Pitch.D, 0);
  }

  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, c4.getPitch());
    assertEquals(Pitch.C, c4Copy.getPitch());
    assertEquals(Pitch.C, c4Copy2.getPitch());
    assertEquals(Pitch.FSharp, f3Sharp.getPitch());
    assertEquals(Pitch.A, a6.getPitch());
  }

  @Test
  public void testGetOctave() {
    assertEquals(4, c4.getOctave());
    assertEquals(4, c4Copy.getOctave());
    assertEquals(4, c4Copy2.getOctave());
    assertEquals(3, f3Sharp.getOctave());
    assertEquals(6, a6.getOctave());
  }

  @Test
  public void testSetPitch() {
    c4.setPitch(Pitch.C);
    assertEquals(Pitch.C, c4.getPitch());

    c4.setPitch(Pitch.GSharp);
    assertEquals(Pitch.GSharp, c4.getPitch());

    c4.setPitch(Pitch.C);
    assertEquals(Pitch.C, c4.getPitch());

    a5.setPitch(Pitch.F);
    assertEquals(Pitch.F, a5.getPitch());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetOctaveInvalidNegative() {
    a6.setOctave(-4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetOctaveInvalidZero() {
    a6.setOctave(0);
  }

  @Test
  public void testSetOctaveValid() {
    f4Sharp.setOctave(4);
    assertEquals(4, f4Sharp.getOctave());

    f4Sharp.setOctave(6);
    assertEquals(6, f4Sharp.getOctave());

    f4Sharp.setOctave(4);
    assertEquals(4, f4Sharp.getOctave());
  }

  @Test
  public void testCompareToSelf() {
    assertEquals(0, c4.compareTo(c4));
  }

  @Test
  public void testCompareToCopy() {
    assertEquals(0, c4.compareTo(c4Copy));
  }

  @Test
  public void testCompareToOctave() {
    assertEquals(12, f4Sharp.compareTo(f3Sharp));
    assertEquals(-12, f3Sharp.compareTo(f4Sharp));
  }

  @Test
  public void testCompareToSameOctave() {
    assertEquals(6, f4Sharp.compareTo(c4));
    assertEquals(-6, c4.compareTo(f4Sharp));
  }

  @Test
  public void testCompareToOtherNote() {
    assertEquals(21, a5.compareTo(c4));
    assertEquals(-21, c4.compareTo(a5));
  }

  @Test
  public void testEqualsOtherObject() {
    assertFalse(c4.equals("hi"));
  }

  @Test
  public void testEqualsSelf() {
    assertTrue(c4.equals(c4));
  }

  @Test
  public void testEqualsSameOctaveDifferentPitch() {
    assertFalse(c4.equals(f4Sharp));
    assertFalse(f4Sharp.equals(c4));
  }

  @Test
  public void testEqualsSamePitchDifferentOctave() {
    assertFalse(a5.equals(a6));
    assertFalse(a6.equals(a5));
  }

  @Test
  public void testEqualsSameOctaveSamePitch() {
    assertTrue(c4.equals(c4Copy));
    assertTrue(c4.equals(c4Copy2));
    assertTrue(c4Copy.equals(c4Copy2));
  }

  @Test
  public void testHashCodeSameObject() {
    assertEquals(a5.hashCode(), a5.hashCode());
  }

  @Test
  public void testHashCode() {
    assertEquals(f3Sharp.hashCode(), Objects.hash(Pitch.FSharp, 3));
  }

  @Test
  public void testHashCodeEqualObjects() {
    assertEquals(c4.hashCode(), c4Copy.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("C4", c4.toString());
    assertEquals("F#3", f3Sharp.toString());
  }

  @Test
  public void testNextNote() {
    assertEquals(new Note(Pitch.CSharp, 4), c4.nextNote());
    assertEquals(new Note(Pitch.G, 3), f3Sharp.nextNote());
    assertEquals(new Note(Pitch.C, 5), new Note(Pitch.B, 4).nextNote());
  }
}