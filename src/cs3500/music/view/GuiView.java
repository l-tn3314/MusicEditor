package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Tone;

/**
 * To represent a subinterface of the MusicEditorView for all view that have a visual or Graphic
 * User Interface
 */
public interface GuiView<N> extends MusicEditorView<N> {
  /**
   * to move the view Home, or to restart the view
   */
  void moveHome();

  /**
   * To move the view to the end of the composition
   */
  void moveEnd();

  /**
   * To pause the view in teh middle of a composition
   */
  void pause();

  /**
   * To update the current beat that the song is on
   *
   * @param i the beat that the song is on
   */
  void updateCurBeat(float i);

  /**
   * To add a KeyListener to this view to allow key inputs from the user
   *
   * @param listener the listener to be added
   */
  void addKeyListener(KeyListener listener);

  /**
   * To add a MouseListener to this view to allow mouse inputs from the user
   *
   * @param listener the listener to be added
   */
  void addMouseListener(MouseListener listener);

  /**
   * To update whether the composition is paused
   *
   * @param isPaused true if it is paused, false otherwise
   */
  void setPaused(boolean isPaused);

  /**
   * To return information about the note the user would like to add
   *
   * @param message the message to be displayed to the user if there is an invalid input
   * @return An array of strings representing all of the attributes of the note that the user would
   * like to add to a composition
   */
  String[] addNotePopUp(String message);

  /**
   * Converts mouse position to the beat which a Note is at
   *
   * @param x the x position of the mouse
   * @return the beat given an x position
   */
  int beatAt(int x);

  /**
   * Converts the mouse position to the Tone of the Note
   *
   * @param y the y position of the mouse
   * @return the Tone given a y position
   */
  Tone toneAt(int y);

  /**
   * opens a dialog which asks the user if they want to remove a Note.
   *
   * @param removedNote a string representing the note to be removed
   * @return true if the note is to be removed; false otherwise
   */
  boolean removeNotePopUp(String removedNote);
}
