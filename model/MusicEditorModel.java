package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Music editor
 */
public interface MusicEditorModel<N> {

  /**
   * Adds the given note to the music editor
   *
   * @param note to be added to the music editor
   */
  void addNote(N note);

  /**
   * Adds the given notes to the music editor
   *
   * @param notes to be added to the music editor
   */
   void addNotes(N... notes);

  /**
   * Removes the given note from the music editor
   *
   * @param note to be removed
   * @return true if given note is successfully removed, false if not present
   */
  boolean removeNote(N note);

  /**
   * Removes all notes that start on the given beat
   *
   * @param beat to be checked against
   * @return true if notes are successfully removed, false if no notes present
   * @throws IllegalArgumentException if given beat is less than one
   */
  boolean removeAllNotesAt(int beat) throws IllegalArgumentException;

  /**
   * Returns a List of all PlayedNotes on the given beat
   *
   * @param beat beat to be checked against
   * @return list of all PlayedNotes on the given beat
   * @throws IllegalArgumentException if given beat is less than one
   */
  List<N> notesAt(int beat) throws IllegalArgumentException;

  /**
   * Returns a Map of all PlayedNotes in the music editor, where Integer represents the beat and
   * List represents the notes that are played on that beat
   *
   * @return List of all PlayedNotes in the music editor
   */
  Map<Integer, List<N>> allNotes();

  /**
   * Adds the notes of the given model to this model
   *
   * @param model whose notes are to be added
   */
  MusicEditorModel combineSimultaneous(MusicEditorModel model);

  /**
   * Adds the notes of the given model to this model such that the notes of the given model come
   * after the notes of this model
   *
   * @param model whose notes are to be added
   */
  MusicEditorModel combineConsecutive(MusicEditorModel model);

  /**
   * returns the range of tones of this composition
   * @return
   */
  List<N> getRange();

  /**
   * Returns the tempo(in microseconds) of this model
   *
   * @return tempo(in microseconds) of this model
   */
  int getTempo();
}
