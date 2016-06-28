package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;


/**
 * To create a Graphical User Interface to render all of the notes in a composition. The beat
 * numbers are render to the side and the pitches are rendered at the top. There are also four
 * beats per measure when rendering measure lines
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView<Note> {
  static final int SCALE = 25; // scale for drawing
  private final ConcreteGuiViewPanel displayPanel; // the panel which is drawn on
  private final JScrollPane scrollPane;
  private boolean isPaused;
  private JOptionPane popup;

  /**
   * Creates new GuiViews
   */
  public GuiViewFrame() {
    this(new ConcreteGuiViewPanel(600, 500));
  }

  public GuiViewFrame(ConcreteGuiViewPanel jp) {
    this.popup = new JOptionPane();
    this.setTitle("Music Editor!");
    setSize(600, 500);
    this.displayPanel = jp;
    this.scrollPane = new JScrollPane(displayPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane().add(scrollPane);
    scrollPane.setPreferredSize(new Dimension(600, 500));
    this.scrollPane.revalidate();
    this.scrollPane.repaint();
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

  /**
   * To move the scrollPane to the beginning by setting the viewport position.
   */
  @Override
  public void moveHome() {
    Point p = scrollPane.getViewport().getViewPosition();
    scrollPane.getViewport().setViewPosition(new Point(0, (int) p.getY()));
    updateCurBeat(0);
  }

  /**
   * To move the scrollPane to the end by setting the viewport position
   */
  @Override
  public void moveEnd() {
    Point p = scrollPane.getViewport().getViewPosition();
    int x = displayPanel.getWidth() - scrollPane.getViewport().getWidth();
    scrollPane.getViewport().setViewPosition(new Point(x, (int) p.getY()));
    displayPanel.updateCurBeatToEnd();
  }

  /**
   * To pause the scrolling
   */
  @Override
  public void pause() {

  }

  /**
   * To update the current beat of the song and setting the auto scrolling based on the currentbeat
   *
   * @param i the beat that the song is on
   */
  @Override
  public void updateCurBeat(float i) {
    autoScroll(i);
    this.displayPanel.updateBeat(i);
  }

  /**
   * Calculates when to scroll the viewPort based off of when the redline reaches the end of the
   * port
   *
   * @param i the currentbeat the composition is on
   */
  private void autoScroll(float i) {
    JViewport viewport = scrollPane.getViewport();
    double topright = viewport.getViewPosition().getX() + viewport.getWidth();
    if (!isPaused && (i + 2) * SCALE / MidiViewImpl.ticksPerBeat > topright) {
      viewport.setViewPosition(new Point((int) topright, (int) viewport.getViewPosition().getY()));
    }
    if (!isPaused && (i + 2) * SCALE / MidiViewImpl.ticksPerBeat <
            viewport.getViewPosition().getX()) {
      viewport.setViewPosition(new Point((int) ((i + 2) * SCALE / MidiViewImpl.ticksPerBeat),
              (int) viewport.getViewPosition().getY()));
    }
  }

  /**
   * To add a KeyListener to this view to allow key inputs from the user
   *
   * @param listener the listener to be added
   */
  @Override
  public void addKeyListener(KeyListener listener) {
    scrollPane.addKeyListener(listener);
    popup.addKeyListener(listener);
  }

  /**
   * To add a MouseListener to this view to allow mouse inputs from the user
   *
   * @param listener the listener to be added
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    scrollPane.addMouseListener(listener);
  }

  /**
   * To render all of the components in the panel of this frame
   *
   * @param g the graphics to be rendered
   */
  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);
    this.displayPanel.paintComponent(g);
  }

  /**
   * To update whether the composition is paused
   *
   * @param isPaused true if it is paused, false otherwise
   */
  @Override
  public void setPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  /**
   * To create a popup asking if the user would like to Add a Note. Takes this information and puts
   * it into an array of strings of 4 if all of the inputs have been correctly put else an array of
   * one item.
   *
   * @param message the message to be displayed to the user if there is an invalid input
   * @return An array of string representing all the information about a Note that the user would
   * like to add. This is sent to the controller to be passed into the model. The 0th index of the
   * array represents the Pitch of the note, the 1st the Octave, the 2nd the Duration, and the
   * third is the Downbeat
   */
  @Override
  public String[] addNotePopUp(String message) {
    String[] result = new String[4];
    if (isPaused) {
      message += "\nEnter the following values: ";
      JLabel label = new JLabel(message + "\n");
      JPanel poppanel = new JPanel(new GridLayout(5, 1));
      poppanel.add(label);

      JLabel selectOctave = new JLabel("Octave: ");
      String[] octaves = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
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
      if (operation == JOptionPane.OK_OPTION) {
        result[0] = (String) dropDownPitch.getSelectedItem();
        result[1] = (String) dropDownOct.getSelectedItem();
        result[2] = durationField.getText();
        result[3] = beatField.getText();
      } else {
        result = new String[1];
      }
    } else {
      result = new String[1];
    }
    return result;
  }

  /**
   * Converts mouse position to the beat which a Note is at
   *
   * @param x the x position of the mouse
   * @return the beat given an x position
   */
  @Override
  public int beatAt(int x) {
    return displayPanel.beatAt((int) scrollPane.getViewport().getViewPosition().getX() + x);
  }

  /**
   * Converts the mouse position to the Tone of the Note
   *
   * @param y the y position of the mouse
   * @return the Tone given a y position
   */
  @Override
  public Tone toneAt(int y) {
    return displayPanel.toneAt((int) scrollPane.getViewport().getViewPosition().getY() + y);
  }

  /**
   * opens a dialog which asks the user if they want to remove a Note when the composition is
   * paused
   *
   * @param removedNote a string representing the note to be removed
   * @return true if the user clicks yes; false otherwise
   */
  @Override
  public boolean removeNotePopUp(String removedNote) {
    if (isPaused) {
      int operation = popup.showConfirmDialog(null, removedNote, "Remove this note?",
              JOptionPane.YES_NO_OPTION);
      return operation == JOptionPane.YES_OPTION;
    }
    return false;
  }
}
