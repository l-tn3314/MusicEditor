package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;


/**
 * To create a Graphical User Interface to render all of the notes in a composition. The beat
 * numbers are render to the side and the pitches are rendered at the top. There are
 * also four beats per measure when rendering measure lines
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView {
  static final int SCALE = 25; // scale for drawing
  private final ConcreteGuiViewPanel displayPanel; // the panel which is drawn on
  private final JScrollPane scrollPane;
  private boolean isPaused;
  private JOptionPane popup;

  /**
   * Creates new GuiViews
   */
  public GuiViewFrame() {
    this.popup = new JOptionPane();
    this.setTitle("Music Editor!");
    setSize(600, 500);
    this.displayPanel = new ConcreteGuiViewPanel(600, 500);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    //this.getContentPane().add(displayPanel);
    scrollPane = new JScrollPane(displayPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane().add(scrollPane);
    scrollPane.setPreferredSize(new Dimension(600, 500));
    this.scrollPane.revalidate();
    this.scrollPane.repaint();
    //
    //
    // this.pack();
    scrollPane.setFocusable(true);
    isPaused = true;
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
    //this.displayPanel.revalidate();
    //this.displayPanel.repaint();
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
    updateCurBeat(0);
  }

  @Override
  public void moveEnd() {
    Point p = scrollPane.getViewport().getViewPosition();
    int x = displayPanel.getWidth() - scrollPane.getViewport().getWidth();
    scrollPane.getViewport().setViewPosition(new Point(x, (int)p.getY()));
    displayPanel.updateCurBeatToEnd();
  }

  @Override
  public void pause() {

  }

  @Override
  public void updateCurBeat(float i) {
    autoScroll(i);
    this.displayPanel.updateBeat(i);
  }

  private void autoScroll(float i) {
    JViewport viewport = scrollPane.getViewport();
    double topright = viewport.getViewPosition().getX() + viewport.getWidth();
    if (!isPaused && (i + 2) * SCALE / MidiViewImpl.ticksPerBeat > topright) {
      viewport.setViewPosition(new Point((int)topright, (int)viewport.getViewPosition().getY()));
    }
    if (!isPaused && (i + 2) * SCALE/ MidiViewImpl.ticksPerBeat < viewport.getViewPosition().getX()) {
      viewport.setViewPosition(new Point((int)((i + 2) * SCALE / MidiViewImpl.ticksPerBeat),
              (int)viewport.getViewPosition().getY()));
    }
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    scrollPane.addKeyListener(listener);
    popup.addKeyListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    scrollPane.addMouseListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
    this.displayPanel.paintComponent(g);
  }

  @Override
  public void setPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  @Override
  public String[] addNotePopUp(String message) {
    String[] result = new String[4];
    if (isPaused) {
      message += "\nEnter the following values: ";
      JLabel label = new JLabel(message + "\n");
      JPanel poppanel = new JPanel(new GridLayout(5, 1));
      poppanel.add(label);

      JLabel selectOctave = new JLabel("Octave: ");
      String[] octaves = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
      JLabel selectPitch = new JLabel("Pitch: ");
      String[] pitches = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
      JComboBox dropDownOct = new JComboBox<String>(octaves);
      JComboBox dropDownPitch = new JComboBox<String>(pitches);
      poppanel.add(selectPitch);
      poppanel.add(dropDownPitch);
      poppanel.add(Box.createHorizontalStrut(15));
      poppanel.add(selectOctave);
      poppanel.add(dropDownOct);
      poppanel.add(Box.createHorizontalStrut(15));
      poppanel.add(new JLabel("Downbeat: "));
      JTextField beatField = new JTextField(5);
      poppanel.add(beatField);
      poppanel.add(Box.createHorizontalStrut(15));
      poppanel.add(new JLabel("Duration: "));
      JTextField durationField = new JTextField(5);
      poppanel.add(durationField);


      int operation = popup.showConfirmDialog(null, poppanel, "Add A Note!",
              JOptionPane.OK_CANCEL_OPTION);
      if(operation == JOptionPane.OK_OPTION) {
        result[0] = (String)dropDownPitch.getSelectedItem();
        result[1] = (String)dropDownOct.getSelectedItem();
        result[2] = durationField.getText();
        result[3] = beatField.getText();
      }
      else {
        result = new String[1];
      }
    } else {
      result = new String[1];
    }
    return result;
  }

  @Override
  public int beatAt(int x) {
    return displayPanel.beatAt((int)scrollPane.getViewport().getViewPosition().getX() + x);
  }

  @Override
  public Tone toneAt(int y) {
    return displayPanel.toneAt((int)scrollPane.getViewport().getViewPosition().getY() + y);
  }

  @Override
  public boolean removeNotePopUp(String removedNote) {
    if(isPaused) {
      int operation = popup.showConfirmDialog(null, removedNote, "Remove this note?",
              JOptionPane.YES_NO_OPTION);
      return operation == JOptionPane.YES_OPTION;
    }
    return false;
  }
}
