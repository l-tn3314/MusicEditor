package cs3500.music.controller;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom implementation of a MouseHandler that accepts mouseEvents and processes them.
 */
public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> mouseClickedMap; // to represent when the mouse is clicked
  private int x; // the x position of the mouse
  private int y; // the y position of the mouse

  /**
   * to construct a MouseHandler
   */
  public MouseHandler() {
    this.mouseClickedMap = new HashMap<Integer, Runnable>();
    this.x = 0;
    this.y = 0;
  }

  /**
   * To set this mouseClickedMap to the given map
   *
   * @param map the map to update mouseClickedMap
   */
  public void setMouseClickedMap(Map<Integer, Runnable> map) {
    this.mouseClickedMap = map;
  }

  /**
   * If the mouseClickedMap contains the keyevent, then run the function that corresponds to it.
   *
   * @param e the MouseEvent
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    x = e.getX();
    y = e.getY();
    if (this.mouseClickedMap.containsKey(MouseEvent.MOUSE_CLICKED)) {
      this.mouseClickedMap.get(MouseEvent.MOUSE_CLICKED).run();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  /**
   * get method that returns the x position of the mouse
   *
   * @return the x position of the mouse
   */
  int returnX() {
    return x;
  }

  /**
   * get method that returns the y position of the mouse
   *
   * @return the y position of the mouse
   */
  int returnY() {
    return y;
  }
}
