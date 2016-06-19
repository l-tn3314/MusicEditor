package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Read-only music editor model that only offers limited functionality like accessing copy of the
 * notes in a composition, the notes in a given beat, the range of notes in the composition and the
 * tempo. This model is used in our view to keep both components separate.
 *
 * @param <N> type of Note
 */
public interface ReadOnlyModel<N> {

  /**
   * Returns a List of all Notes on the given beat
   *
   * @param beat beat to be checked against
   * @return List of all Notes on the given beat
   * @throws IllegalArgumentException if given beat is less than one
   */
  List<N> notesAt(int beat) throws IllegalArgumentException;

  /**
   * Returns a Map of all Notes in the music editor model, where Integer represents the beat and
   * List represents the Notes that are played on that beat
   *
   * @return List of all Notes in the music editor
   */
  Map<Integer, List<N>> allNotes();

  /**
   * Returns the range of Notes of this composition
   *
   * @return List of Note that represent the range of (tones for) this composition. If this
   * composition is empty, returns an empty list of notes.
   */
  List<N> getRange();

  /**
   * Returns the tempo(in microseconds) of this model
   *
   * @return tempo(in microseconds) of this model
   */
  int getTempo();
}
