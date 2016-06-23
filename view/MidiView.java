package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Created by Tuna on 6/22/2016.
 */
public interface MidiView extends MusicEditorView<Note>{
  void moveHome();

  void moveEnd();

  void pause();

  float getCurBeat();

  boolean isPaused();
}
