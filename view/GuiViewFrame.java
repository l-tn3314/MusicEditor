package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;


/**
 * To create a Graphical User Interface to render all of the notes in a composition. The beat
 * numbers are render to the side and the pitches are rendered at the top. There are
 * also four beats per measure when rendering measure lines
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView {
  static final int SCALE = 25; // scale for drawing
  private final ConcreteGuiViewPanel displayPanel; // the panel which is drawn on
  private final JScrollPane scrollPane;

  /**
   * Creates new GuiViews
   */
  public GuiViewFrame() {
    this.setTitle("Music Editor!");
    setSize(300, 300);
    this.displayPanel = new ConcreteGuiViewPanel(300, 300);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    //this.getContentPane().add(displayPanel);
    scrollPane = new JScrollPane(displayPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(200, 200));
    this.add(scrollPane);
    scrollPane.revalidate();
    this.pack();
    scrollPane.setFocusable(true);
  }

  /**
   * To inialize this GUI to be seen on the frame
   */
  @Override
  public void initialize() {
    this.setVisible(true);
  }

  /**
   * To display the given model in a GUI.
   *
   * @param m model whose Notes is to be displayed
   */
  @Override
  public void display(ReadOnlyModel<Note> m) {
    this.displayPanel.setModel(m);
  }

  /**
   * To set the preferred size of this GUI
   *
   * @return a new Dimension which represents the size of the GUI.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(300, 300);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void moveHome() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point(0, (int)p.getY()));
  }

  @Override
  public void moveEnd() {
    Point p = scrollPane.getViewport().getViewPosition();
    int x = displayPanel.getWidth() - scrollPane.getViewport().getWidth();
    scrollPane.getViewport().setViewPosition(new Point(x, (int)p.getY()));
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    scrollPane.addKeyListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    //.addActionListener(listener);
  }
}
