package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;


/**
 * Gui view implementation
 */
public class GuiViewFrame extends javax.swing.JFrame implements MusicEditorView<Note> {
  static final int SCALE = 25;
  private final ConcreteGuiViewPanel displayPanel;

  /**
   * Creates new GuiViews
   */
  public GuiViewFrame() {
    this.setTitle("Music Editor!");
    setSize(300, 300);
    this.displayPanel = new ConcreteGuiViewPanel(300, 300);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    JScrollPane scrollPane = new JScrollPane(displayPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(200, 200));
    this.add(scrollPane);
    scrollPane.revalidate();
    this.pack();
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public void display(ReadOnlyModel<Note> m) {
    this.displayPanel.setModel(m);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(300, 300);
  }

}
