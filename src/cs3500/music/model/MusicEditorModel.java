package cs3500.music.model;

/**
 * /** This is an editor for a general music editor and all of the functionality that can be applied
 * to a piece of music. An implementation of this interface will allow the user to add a song to the
 * editor, add music one note at a time, remove a note, combine two pieces of music to play
 * simultaneous, play two pieces of music consecutively, and access the notes from a piece of
 * music.
 *
 * @param <N> type of Note which to be used in this Editor
 */
public interface MusicEditorModel<N> extends ReadOnlyModel<N> {

  /**
   * to add a note to this piece of music one note at a time
   *
   * @param note the note which to be added to this piece of music
   * @throws IllegalArgumentException if the given note or its fields are null
   */
  void addNote(N note) throws IllegalArgumentException;

  /**
   * Adds the given Notes to this music editor model
   *
   * @param notes to be added to the music editor
   */
  void addNotes(N... notes);

  /**
   * Removes the given Note from this music editor model. Users may not remove part of the duration
   * of a note.
   *
   * @param note to be removed
   * @return true if given note is successfully removed, false if not present
   * @throws IllegalArgumentException if the note or its fields are null.
   */
  boolean removeNote(N note) throws IllegalArgumentException;

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
