package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

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
    setSize(300, 300);
    this.displayPanel = new ConcreteGuiViewPanel(300, 300);
    //this.displayPanel.setPreferredSize(new Dimension(5000, 500));
    //JScrollPane scrollPane = new JScrollPane(this.displayPanel);
    //scrollPane.setBackground(new Color(236, 236, 236));

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    //this.displayPanel.setBackground(new Color(236, 236, 236));
    //JScrollPane scrollPane = new JScrollPane(this.displayPanel);
    JScrollPane scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(200, 200));
    this.add(scrollPane);
    scrollPane.revalidate();
    this.pack();
  }


  @Override
  public void initialize(){
    this.setVisible(true);
    //this.setResizable(false);
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
