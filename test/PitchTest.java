package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Pitch enum
 */
public class PitchTest {

  @Test
  public void testToStringC() {
    assertEquals("C", Pitch.C.toString());
  }

  @Test
  public void testToStringCSharp() {
    assertEquals("C#", Pitch.CSharp.toString());
  }

  @Test
  public void testToStringD() {
    assertEquals("D", Pitch.D.toString());
  }

  @Test
  public void testToStringDSharp() {
    assertEquals("D#", Pitch.DSharp.toString());
  }

  @Test
  public void testToStringE() {
    assertEquals("E", Pitch.E.toString());
  }

  @Test
  public void testToStringF() {
    assertEquals("F", Pitch.F.toString());
  }

  @Test
  public void testToStringFSharp() {
    assertEquals("F#", Pitch.FSharp.toString());
  }

  @Test
  public void testToStringG() {
    assertEquals("G", Pitch.G.toString());
  }

  @Test
  public void testToStringGSharp() {
    assertEquals("G#", Pitch.GSharp.toString());
  }

  @Test
  public void testToStringA() {
    assertEquals("A", Pitch.A.toString());
  }

  @Test
  public void testToStringASharp() {
    assertEquals("A#", Pitch.ASharp.toString());
  }

  @Test
  public void testToStringB() {
    assertEquals("B", Pitch.B.toString());
  }
}