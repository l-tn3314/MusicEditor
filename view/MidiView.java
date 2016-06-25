package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Midi view
 */
public interface MidiView extends MusicEditorView<Note>{
  void moveHome();

  void moveEnd();

  void pause();

  float getCurBeat();

  boolean isPaused();
}
