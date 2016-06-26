package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;

/**
 * To represent an implementation of the GuiView that synchronizes both the visual and
 * the midi view into one cohesive unit
 */
public class CompositeView implements GuiView<Note> {
  GuiView gui;  // the visual view
  MidiView midi;  // the audio view

  /**
   * To construct a CompositeView with a visual and audio
   *
   * @param gui  the visual component of this view
   * @param midi the midi or audio component of this view
   */
  public CompositeView(GuiView gui, MidiView midi) {
    this.gui = gui;
    this.midi = midi;
  }

  /**
   * passes information to the gui and midi view in order to restart the music
   */
  @Override
  public void moveHome() {
    gui.moveHome();
    midi.moveHome();
    gui.setPaused(midi.isPaused());
  }

  /**
   * passes information to the gui and midi components of this view in order to pause both the midi
   * and gui view
   */
  @Override
  public void pause() {
    gui.pause();
    midi.pause();
    gui.setPaused(midi.isPaused());
  }

  /**
   * Keeps track of the current beat in the midi view and passes it into the guiview in order to
   * update the position of the redline
   *
   * @param i the current beat to update this view with
   */
  @Override
  public void updateCurBeat(float i) {
    gui.updateCurBeat(midi.getCurBeat());
  }

  /**
   * passes information to the gui and midi componenets to move the song to the end.
   */
  @Override
  public void moveEnd() {
    gui.moveEnd();
    midi.moveEnd();
    gui.setPaused(midi.isPaused());
  }

  /**
   * To add a KeyListener to the gui to process KeyEvents from the user
   *
   * @param listener the listener to add to the gui
   */
  @Override
  public void addKeyListener(KeyListener listener) {
    gui.addKeyListener(listener);
  }

  /**
   * To add a MouseListener to the gui to process MouseEvents from the user
   *
   * @param listener the listener to add to the gui
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    gui.addMouseListener(listener);
  }

  /**
   * To initialize both the gui and midi views to display
   */
  @Override
  public void initialize() {
    gui.initialize();
    midi.initialize();
  }

  /**
   * To display the given model by constantly calling updateCurBeat to pass the current beat the
   * midi is on to the gui in order to synchronize the red line with the view
   *
   * @param m model whose Notes is to be displayed
   */
  @Override
  public void display(ReadOnlyModel<Note> m) {
    TimerTask timerTask = new TimerTask() {
      public void run() {
        updateCurBeat(midi.getCurBeat());
      }
    };
    Timer t = new Timer();
    t.schedule(timerTask, 0, 10);

    gui.display(m);
    midi.display(m);
    midi.pause();
  }

  /**
   * boolean flag that determines when the composite view is paused (not scrolling or playing
   * sound)
   *
   * @param isPaused field that determines if the view is puased
   */
  @Override
  public void setPaused(boolean isPaused) {
    gui.setPaused(isPaused);
  }

  /**
   * Redirects information to the gui when 'A has been pressed to display a popup asking what Note
   * the user would like to add
   *
   * @param message the message to display to the user. If there is an invalid input, it will
   *                display this to the user.
   * @return An array list of strings containing the information from the user about the note they
   * desire to add. the 0th index is the pitch, the 1st is the octave, the 2nd is the duration, and
   * the third is the downbeat
   */
  @Override
  public String[] addNotePopUp(String message) {
    gui.setPaused(midi.isPaused());
    return gui.addNotePopUp(message);
  }

  /**
   * redirects the information to the guiview to get the beat number corresponding to the x of the
   * mouse
   *
   * @param x position of the mouse
   * @return the beat number at the x position of the mouse
   */
  @Override
  public int beatAt(int x) {
    return gui.beatAt(x);
  }

  /**
   * redirects the information to the guiview to get the Tone corresponding to the y of the mouse
   *
   * @param y position of the mouse
   * @return the Tone at the y position of the mouse
   */
  @Override
  public Tone toneAt(int y) {
    return gui.toneAt(y);
  }

  /**
   * Redirects information to the gui when a note has been removed by a user in the controller to
   * display a popup asking if they want this note to be removed
   *
   * @param removedNote the Note that is to be removed. This information is passed to the gui view
   *                    to display to the user
   * @return true if the note has been removed, false otherwise.
   */
  @Override
  public boolean removeNotePopUp(String removedNote) {
    gui.setPaused(midi.isPaused());
    return gui.removeNotePopUp(removedNote);
  }
}

