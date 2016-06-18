package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;

// to create the views for this
public interface MusicEditorView<N> {

  void initialize();

  void display(MusicEditorModel<N> m);
}
