package cs3500.music.view;

import java.awt.*;

import cs3500.music.model.MusicEditorModel;


/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements MusicEditorView {
  static final int SCALE = 25;

  private final ConcreteGuiViewPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiViews
   */
  public GuiViewFrame() {
    this.setTitle("Music Editor!");
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.displayPanel.setBackground(new Color(236, 236, 236));
    this.pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void display(MusicEditorModel m) {
    this.displayPanel.setModel(m);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(300, 300);
  }

}
