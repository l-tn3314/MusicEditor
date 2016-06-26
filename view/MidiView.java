package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * To represent a view with sound that has all of the functionality of the MusicEditor with
 * additional functionality specific to a view that plays sound.
 */
public interface MidiView extends MusicEditorView<Note> {

  /**
   * To start the song from the beginning
   */
  void moveHome();

  /**
   * To move to the end of the song
   */
  void moveEnd();

  /**
   * To pause a song
   */
  void pause();

  /**
   * To get the current beat that the song is on
   *
   * @return a float representing the current beat of a song
   */
  float getCurBeat();

  /**
   * Determines if the song is paused
   *
   * @return true if the song is paused; false otherwise
   */
  boolean isPaused();
}
