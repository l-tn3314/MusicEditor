package cs3500.music.controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom implementation of KeyListener that receives keyboard events.
 */
public class KeyboardHandler implements KeyListener {
  private Map<Character, Runnable> keyTypedMap; // keeps track of all the keys that are typed
  private Map<Integer, Runnable> keyPressedMap; // keeps track of all the keys that are pressed
  private Map<Integer, Runnable> keyReleasedMap; // keeps track of all the keys that are released

  /**
   * to construct a keyBoardHandler
   */
  public KeyboardHandler() {
    this.keyTypedMap = new HashMap<Character, Runnable>();
    this.keyPressedMap = new HashMap<Integer, Runnable>();
    this.keyReleasedMap = new HashMap<Integer, Runnable>();
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */

  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
   */

  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes
   */

  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * If the map contains the key that was typed, it runs the function that corresponds to it
   *
   * @param e a KeyEvent
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar()))
      keyTypedMap.get(e.getKeyChar()).run();
  }

  /**
   * If the map contains the key that was pressed, it runs the function that corresponds to it
   *
   * @param e a KeyEvent
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode()))
      keyPressedMap.get(e.getKeyCode()).run();
  }

  /**
   * If the map contains the key that was released, it runs the function that corresponds to it
   *
   * @param e a KeyEvent
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode()))
      keyReleasedMap.get(e.getKeyCode()).run();
  }
}
