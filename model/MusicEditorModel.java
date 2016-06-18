package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Music editor
 *
 * @param <N> type of Note
 */
public interface MusicEditorModel<N> extends ReadOnlyModel<N> {

  /**
   * Adds the given Note to this music editor model
   *
   * @param note to be added to the music editor
   */
  void addNote(N note);

  /**
   * Adds the given Notes to this music editor model
   *
   * @param notes to be added to the music editor
   */
  void addNotes(N... notes);

  /**
   * Removes the given Note from this music editor model
   *
   * @param note to be removed
   * @return true if given note is successfully removed, false if not present
   */
  boolean removeNote(N note);

  /**
   * Removes all Notes that play on the given beat from this music editor model
   *
   * @param beat to be checked against
   * @return true if notes are successfully removed, false if no notes present
   * @throws IllegalArgumentException if given beat is less than one
   */
  boolean removeAllNotesAt(int beat) throws IllegalArgumentException;

  /**
   * Adds the Notes of the given model to this model
   *
   * @param model whose notes are to be added
   */
  MusicEditorModel<N> combineSimultaneous(MusicEditorModel<N> model);

  /**
   * Adds the Notes of the given model to this model such that the Notes of the given model come
   * after the Notes of this model
   *
   * @param model whose notes are to be added
   */
  MusicEditorModel<N> combineConsecutive(MusicEditorModel<N> model);

}
