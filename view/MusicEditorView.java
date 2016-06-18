package cs3500.music.view;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.ReadOnlyModel;

/**
 * Music editor view
 *
 * @param <N> type of Note
 */
public interface MusicEditorView<N> {

  /**
   * Initializes this view
   */
  void initialize();

  /**
   * Displays the Notes in the given model
   *
   * @param m model whose Notes is to be displayed
   */
  void display(ReadOnlyModel<N> m);
}
