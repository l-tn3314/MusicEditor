package cs3500.music.tests;


import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.controller.KeyboardHandler;

import static org.junit.Assert.assertEquals;

/**
 * Creates mock Runnables
 */
public class KeyboardHandlerTest {
  KeyboardHandler kbh;
  StringBuilder result;
  Runnable charA;
  Runnable charSpace;
  Runnable home;
  Runnable end;
  Map<Integer, Runnable> keyPressed;

  @Before
  public void init() {
    kbh = new KeyboardHandler();
    result = new StringBuilder();
    charA = new Runnable() {
      @Override
      public void run() {
        result.append("Add a Note\n");
      }
    };
    charSpace = new Runnable() {
      @Override
      public void run() {
        result.append("Pause\n");
      }
    };
    home = new Runnable() {
      @Override
      public void run() {
        result.append("Back to Beginning\n");
      }
    };
    end = new Runnable() {
      @Override
      public void run() {
        result.append("To the End\n");
      }
    };
    keyPressed = new HashMap<Integer, Runnable>();
    keyPressed.put(KeyEvent.VK_A, charA);
    keyPressed.put(KeyEvent.VK_SPACE,charSpace);
    keyPressed.put(KeyEvent.VK_HOME, home);
    keyPressed.put(KeyEvent.VK_END, end);
    kbh.setKeyPressedMap(keyPressed);
  }

  // to test when the 'A key on the keyboard has not been pressed
  @Test
  public void testANotPressed() {
    assertEquals("",
            result.toString());
  }

  // to test when the 'A key on the keyboard has been pressed
  @Test
  public void testAPressed() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_A, 'a');
    kbh.keyPressed(event);
    assertEquals("Add a Note\n",
            result.toString());
  }

  // to test when another key besides 'A is pressed
  @Test
  public void testAnotherKey() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_COMMA, ',');
    kbh.keyPressed(event);
    assertEquals("",
            result.toString());
  }

  // to test when 'A is pressed multiple times
  @Test
  public void testAMultiple() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_A, 'A');
    kbh.keyPressed(event);
    kbh.keyPressed(event);
    assertEquals("Add a Note\n" +
            "Add a Note\n",
            result.toString());
  }

  // to test when another key that is not in the keyset is pressed and then 'A is pressed
  @Test
  public void testPress() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_UNDERSCORE, '_');
    KeyEvent event1 = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_A, 'A');
    kbh.keyPressed(event);
    kbh.keyPressed(event1);
    assertEquals("Add a Note\n",
            result.toString());
  }

  // to test when the SpaceBar and 'A are pressed one after the other
  @Test
  public void testSpaceThenA() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_SPACE, ' ');
    KeyEvent event1 = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_A, 'A');
    kbh.keyPressed(event);
    kbh.keyPressed(event1);
    assertEquals("Pause\n" +
            "Add a Note\n",
            result.toString());
  }

  // to test when Home is pressed
  @Test
  public void testHome() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_HOME, 'H');
    kbh.keyPressed(event);
    assertEquals("Back to Beginning\n",
            result.toString());
  }

  // to test when End is pressed
  @Test
  public void testEnd() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_END, 'E');
    kbh.keyPressed(event);
    assertEquals("To the End\n",
            result.toString());
  }

  // to test when End is released
  @Test
  public void testEndRelease() {
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_END, 'E');
    kbh.keyReleased(event);
    assertEquals("",
            result.toString());
  }

}
