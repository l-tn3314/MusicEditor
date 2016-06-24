package cs3500.music.controller;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> mouseClickedMap;

  public MouseHandler() {
    this.mouseClickedMap = new HashMap<Integer, Runnable>();
  }

  public void setMouseClickedMap(Map<Integer, Runnable> map) {
    this.mouseClickedMap = map;
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      if (this.mouseClickedMap.containsKey(MouseEvent.MOUSE_CLICKED)) {
        this.mouseClickedMap.get(MouseEvent.MOUSE_CLICKED).run();
      }
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
}
