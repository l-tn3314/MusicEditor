package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.controller.MouseHandler;

import static org.junit.Assert.assertEquals;

/**
 * Creates mock Runnables
 */
public class MouseHandlerTest {
  MouseHandler mh;
  Runnable mouseClick;
  StringBuilder result;
  Map<Integer, Runnable> clicked;


  @Before
  public void init() {
    mh = new MouseHandler();
    result = new StringBuilder();
    clicked = new HashMap<Integer, Runnable>();
    mouseClick = new Runnable() {
      public void run() {
        result.append("Remove Note");
      }
    };
    mh.setMouseClickedMap(clicked);
  }

  @Test
  public void testClickOnce() {
    MouseEvent mouse = new MouseEvent(new JFrame(), MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    clicked.put(MouseEvent.MOUSE_CLICKED, mouseClick);
    mh.mouseClicked(mouse);
    assertEquals("Remove Note",
            result.toString());
  }

  @Test
  public void testNoClick() {
    MouseEvent mouse = new MouseEvent(new JFrame(), MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    mh.mouseClicked(mouse);
    assertEquals("",
            result.toString());
  }

  @Test
  public void testOtherClick() {
    MouseEvent mouse = new MouseEvent(new JFrame(), MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    clicked.put(MouseEvent.MOUSE_CLICKED, mouseClick);
    mh.mousePressed(mouse);
    assertEquals("",
            result.toString());
  }

  @Test
  public void testClickMoreThanOnce() {
    MouseEvent mouse = new MouseEvent(new JFrame(), MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    clicked.put(MouseEvent.MOUSE_CLICKED, mouseClick);
    mh.mouseClicked(mouse);
    mh.mouseClicked(mouse);
    assertEquals("Remove NoteRemove Note",
            result.toString());
  }

  @Test
  public void testClickAfterOtherEvent() {
    MouseEvent mouse = new MouseEvent(new JFrame(), MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    MouseEvent mouseRelease = new MouseEvent(new JFrame(), MouseEvent.MOUSE_RELEASED, 1, 0, 10, 10, 1, true);
    clicked.put(MouseEvent.MOUSE_CLICKED, mouseClick);
    mh.mouseReleased(mouseRelease);
    mh.mouseClicked(mouse);
    assertEquals("Remove Note",
            result.toString());
  }
}
