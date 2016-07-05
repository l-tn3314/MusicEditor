package cs3500.music.view;

import cs3500.music.model.ReadOnlyModel;

/**
 * A general type of view that can be created for a Music Editor. We have implemented a text view,
 * a gui view, and a midi view.
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
