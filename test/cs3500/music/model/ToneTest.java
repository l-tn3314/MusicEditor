package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Tests the Tone class
 */
public class ToneTest {

  Tone c4;
  Tone f3Sharp;
  Tone f4Sharp;
  Tone a6;
  Tone a5;

  @Before
  public void init() {
    c4 = new Tone(Pitch.C, Octave.FOUR);
    f3Sharp = new Tone(Pitch.FSHARP, Octave.THREE);
    f4Sharp = new Tone(Pitch.FSHARP, Octave.FOUR);
    a6 = new Tone(Pitch.A, Octave.SIX);
    a5 = new Tone(Pitch.A, Octave.FIVE);
  }


  @Test
  public void testGetPitch() {
    assertEquals(Pitch.C, c4.getPitch());
    assertEquals(Pitch.FSHARP, f3Sharp.getPitch());
    assertEquals(Pitch.A, a6.getPitch());
  }

  @Test
  public void testGetOctave() {
    assertEquals(Octave.FOUR, c4.getOctave());
    assertEquals(Octave.THREE, f3Sharp.getOctave());
    assertEquals(Octave.SIX, a6.getOctave());
  }

  @Test
  public void testCompareToSelf() {
    assertEquals(0, c4.compareTo(c4));
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
  public void testCompareToOtherTone() {
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
  public void testHashCodeSameObject() {
    assertEquals(a5.hashCode(), a5.hashCode());
  }

  @Test
  public void testHashCode() {
    assertEquals(f3Sharp.hashCode(), Objects.hash(Pitch.FSHARP, Octave.THREE));
  }


  @Test
  public void testToString() {
    assertEquals("C4", c4.toString());
    assertEquals("F#3", f3Sharp.toString());
  }
}