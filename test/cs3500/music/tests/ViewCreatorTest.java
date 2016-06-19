package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.util.ViewCreator;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.TextViewImpl;

import static org.junit.Assert.assertTrue;

// to test the static factory method create in ViewCreator
public class ViewCreatorTest {

  // to test that the create method creates an instanceOf the console view given the
  // string "console"
  @Test
  public void testCreateConsole() {
    assertTrue(ViewCreator.create("console") instanceof TextViewImpl);
  }

  // to test that the create method creates an instanceOf the gui view given the
  // string "visual"
  @Test
  public void testCreateGUI() {
    assertTrue(ViewCreator.create("visual") instanceof GuiViewFrame);
  }

  // to test that the create method creates an instanceOf the midi view given the
  // string midi
  @Test
  public void testCreateMidi() {
    assertTrue(ViewCreator.create("midi") instanceof MidiViewImpl);
  }

  // to test the exception when passed in a string besides console, visual, and midi,
  @Test(expected = IllegalArgumentException.class)
  public void testException() {
    ViewCreator.create("helloworld");
  }
}
