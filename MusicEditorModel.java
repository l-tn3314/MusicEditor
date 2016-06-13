package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Music editor
 */
public interface MusicEditorModel {

  /**
   * Adds the given note to the music editor
   *
   * @param note to be added to the music editor
   */
  void addNote(Note note);

  /**
   * Removes the given note from the music editor
   *
   * @param note to be removed
   * @return true if given note is successfully removed, false if not present
   */
  boolean removeNote(Note note);

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
  List<Note> notesAt(int beat) throws IllegalArgumentException;

  /**
   * Returns a Map of all PlayedNotes in the music editor, where Integer represents the beat and
   * List represents the notes that are played on that beat
   *
   * @return List of all PlayedNotes in the music editor
   */
  Map<Integer, List<Note>> allNotes();

  /**
   * Adds the notes of the given model to this model
   *
   * @param model whose notes are to be added
   */
  void combineSimultaneous(MusicEditorModel model);

  /**
   * Adds the notes of the given model to this model such that the notes of the given model come
   * after the notes of this model
   *
   * @param model whose notes are to be added
   */
  void combineConsecutive(MusicEditorModel model);

  /**
   * Creates a String representation of this model.
   * Columns are five characters.
   * The leftmost column represents the beats, right-justified.
   * The top row represents each pitch.
   * Each note head is "  X  " and each note-sustain is "  |  ".
   *
   * @return String representation of this model
   * @throws IllegalArgumentException if model does not contain notes
   */
  String stringView() throws IllegalArgumentException;
}
