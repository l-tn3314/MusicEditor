package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.music.util.CompositionBuilder;

/**
 * /** To represent a piece of music in a Music Editor that has the ability to be edited and altered
 * in various ways. The music editor represents all music based off of western music. The notes are
 * based off of the western music rules where there are 12 distinct pitches in one octave. This
 * model also only represents music that can be heard by humans therefore only 10 octaves are
 * supported. The tempo of this music is represented only in beats. Meausres are not considered in
 * this Model. The functionality of this model implements that of the GenericMusicEditor therefore
 * users have the ability to add notes to this editor, delete notes, combine two pieces of music to
 * be played either cosecutively or simultaneously. Some class invariants include putting in no
 * notes into the editor in which it would return the default value and putting in a note into the
 * method removeNote() that is not in the Music Model.
 */
public final class MusicPiece implements MusicEditorModel<Note> {
  private int tempo; // tempo in microseconds per beat
  private Map<Integer, List<Note>> beatsToNotes; // integer does not map to empty list

  /**
   * Constructs a MusicPiece with a default of 200000 microseconds per beat
   */
  public MusicPiece() {
    this.tempo = 200000;
    this.beatsToNotes = new HashMap<Integer, List<Note>>();
  }

  /**
   * Constructs a MusicPiece with the given beats per measure
   *
   * @param tempo in microseconds
   */
  public MusicPiece(int tempo) {
    this();
    this.tempo = tempo;
  }

  /**
   * to add a note to this piece of music one note at a time
   *
   * @param note the note which to be added to this piece of music
   * @throws IllegalArgumentException if the given note or its fields are null
   */
  @Override
  public void addNote(Note note) throws IllegalArgumentException {
    if (note == null || note.getTone() == null) {
      throw new IllegalArgumentException("Note or fields are null");
    } else {
      int noteBeat = note.getDownbeat();
      int noteDur = note.getDuration();
      for (int i = 0; i < noteDur; i++) {
        if (this.beatsToNotes.containsKey(noteBeat + i)) {
          this.beatsToNotes.get(noteBeat + i).add(note);
        } else {
          List<Note> newList = new ArrayList<Note>();
          newList.add(note);
          this.beatsToNotes.put(noteBeat + i, newList);
        }
      }
    }
  }

  /**
   * Adds the given Notes to this music editor model
   *
   * @param notes to be added to the music editor
   */
  @Override
  public void addNotes(Note... notes) {
    for (Note n : notes) {
      this.addNote(n);
    }
  }

  /**
   * Removes keys that point to empty Lists in this piece's beatsToNotes
   */
  private void cleanEmptyKeys() {
    Map<Integer, List<Note>> temp = new HashMap<Integer, List<Note>>(this.beatsToNotes);
    Set<Integer> keys = temp.keySet();
    for (Integer i : keys) {
      if (this.beatsToNotes.get(i).isEmpty()) {
        this.beatsToNotes.remove(i);
      }
    }
  }

  /**
   * Removes the given Note from this music editor model. Users may not remove part of the duration
   * of a note.
   *
   * @param note to be removed
   * @return true if given note is successfully removed, false if not present
   * @throws IllegalArgumentException if the note or its fields are null.
   */
  @Override
  public boolean removeNote(Note note) throws IllegalArgumentException {
    if (note == null || note.getTone() == null) {
      throw new IllegalArgumentException("Note or fields are null");
    } else {
      int noteBeat = note.getDownbeat();
      boolean temp;
      if (this.beatsToNotes.containsKey(noteBeat)) {
        int noteDur = note.getDuration();
        for (int i = 0; i < noteDur; i++) {
          this.beatsToNotes.get(noteBeat + i).remove(note);
        }
        temp = true;
      } else {
        temp = false;
      }
      cleanEmptyKeys();
      return temp;
    }
  }

  /**
   * Removes all Notes that play on the given beat from this music editor model
   *
   * @param beat to be checked against
   * @return true if notes are successfully removed, false if no notes present
   * @throws IllegalArgumentException if given beat is less than one
   */

  @Override
  public boolean removeAllNotesAt(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Beat cannot be less than zero!");
    }
    return this.beatsToNotes.remove(beat) != null;
  }

  /**
   * Returns a List of all Notes on the given beat
   *
   * @param beat beat to be checked against
   * @return List of all Notes on the given beat
   * @throws IllegalArgumentException if given beat is less than one
   */
  @Override
  public List<Note> notesAt(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Beat cannot be less than zero!");
    }
    if (this.beatsToNotes.containsKey(beat)) {
      return this.beatsToNotes.get(beat);
    } else {
      return new ArrayList<Note>();
    }
  }

  /**
   * Returns a Map of all Notes in the music editor model, where Integer represents the beat and
   * List represents the Notes that are played on that beat
   *
   * @return List of all Notes in the music editor
   */
  @Override
  public Map<Integer, List<Note>> allNotes() {
    return new HashMap<Integer, List<Note>>(this.beatsToNotes);
  }

  /**
   * To take this piece of music and the given piece of music, other, and create a new music editor
   * that has the notes of both this and the given and plays them simultaneously.
   *
   * @param model the piece of music that a user might wish to combine with this.
   * @return a new MusicEditorModel<Note></Note> that plays this MusicPiece and the given at the
   * same time
   */
  @Override
  public MusicEditorModel<Note> combineSimultaneous(MusicEditorModel<Note> model) {
    MusicEditorModel<Note> result = new MusicPiece();
    List<Note> notes = new ArrayList<Note>();
    for (Integer i : this.beatsToNotes.keySet()) {
      notes.addAll(this.beatsToNotes.get(i));
    }
    notes = new ArrayList<Note>(new HashSet<Note>(notes));
    result.addNotes(notes.toArray(new Note[notes.size()]));

    notes = new ArrayList<Note>();
    Map<Integer, List<Note>> noteMap = model.allNotes();
    Set<Integer> keys = noteMap.keySet();
    for (Integer k : keys) {
      notes.addAll(noteMap.get(k));
    }
    notes = new ArrayList<Note>(new HashSet<Note>(notes));
    result.addNotes(notes.toArray(new Note[notes.size()]));
    return result;
  }

  /**
   * To create a new GenericMusicEditor that takes the given piece of music and this piece of music
   * and plays the other after this one.
   *
   * @param model piece of music the user wishes to play after this one
   * @return a new MusicEditorModel<Note></Note> that takes the given piece of music and this one
   * and plays them one after the other.
   */
  @Override
  public MusicEditorModel<Note> combineConsecutive(MusicEditorModel<Note> model) {
    MusicEditorModel<Note> result = new MusicPiece();
    List<Note> notes = new ArrayList<Note>();
    for (Integer i : this.beatsToNotes.keySet()) {
      notes.addAll(this.beatsToNotes.get(i));
    }
    notes = new ArrayList<Note>(new HashSet<Note>(notes));
    result.addNotes(notes.toArray(new Note[notes.size()]));

    List<Note> listCopy = new ArrayList<Note>();
    Map<Integer, List<Note>> noteMap = model.allNotes();
    Set<Integer> keys = noteMap.keySet();
    int maxKey = Collections.max(this.beatsToNotes.keySet());
    for (Integer i : keys) {
      for (Note pn : noteMap.get(i)) {
        listCopy.add(new Note(pn.getTone(), pn.getDuration(), pn.getDownbeat() + maxKey + 1));
      }
    }
    listCopy = new ArrayList<Note>(new HashSet<Note>(listCopy));
    result.addNotes(listCopy.toArray(new Note[listCopy.size()]));

    return result;
  }

  /**
   * Creates a two-element array containing the lowest and highest note
   *
   * @return A two-element array containing the lowest and highest note
   */
  private Note[] lowestHighest() {
    Note[] arr = new Note[2];
    Set<Integer> keys = this.beatsToNotes.keySet();
    for (Integer i : keys) {
      Note pn = this.beatsToNotes.get(i).get(0);
      arr[0] = pn;
      arr[1] = pn;
      break;
    }
    for (Integer i : keys) {
      List<Note> notesList = this.beatsToNotes.get(i);
      for (Note pn : notesList) {
        if (pn.compareTo(arr[0]) < 0) {
          arr[0] = pn;
        }
        if (pn.compareTo(arr[1]) > 0) {
          arr[1] = pn;
        }
      }
    }
    return arr;
  }

  /**
   * Returns the Note that comes after this Note chromatically
   *
   * @return Note that comes after this Note chromatically
   */
  private Tone nextTone(Tone t) {
    switch (t.getPitch()) {
      case C:
        return new Tone(Pitch.CSHARP, t.getOctave());
      case CSHARP:
        return new Tone(Pitch.D, t.getOctave());
      case D:
        return new Tone(Pitch.DSHARP, t.getOctave());
      case DSHARP:
        return new Tone(Pitch.E, t.getOctave());
      case E:
        return new Tone(Pitch.F, t.getOctave());
      case F:
        return new Tone(Pitch.FSHARP, t.getOctave());
      case FSHARP:
        return new Tone(Pitch.G, t.getOctave());
      case G:
        return new Tone(Pitch.GSHARP, t.getOctave());
      case GSHARP:
        return new Tone(Pitch.A, t.getOctave());
      case A:
        return new Tone(Pitch.ASHARP, t.getOctave());
      case ASHARP:
        return new Tone(Pitch.B, t.getOctave());
      case B:
        Octave[] octaves = t.getOctave().values();
        return new Tone(Pitch.C, octaves[(t.getOctave().ordinal() + 1) % octaves.length]);
      default:
        return null; // never gets here
    }
  }

  /**
   * Returns the range of Notes of this composition
   *
   * @return List of Note that represent the range of (tones for) this composition. If this
   * composition is empty, returns an empty list of notes.
   */
  @Override
  public List<Note> getRange() {
    List<Note> result = new ArrayList<Note>();
    if (beatsToNotes.size() == 0) {
      return result;
    }
    Note[] lowhigh = this.lowestHighest();
    Tone lowest = lowhigh[0].getTone();
    Tone highest = lowhigh[1].getTone();
    while (!lowest.equals(highest)) {
      result.add(new Note(lowest, 1, 1));
      lowest = this.nextTone(lowest);
    }
    result.add(new Note(highest, 1, 1));
    return result;
  }

  /**
   * Returns the tempo(in microseconds) of this model
   *
   * @return tempo(in microseconds) of this model
   */
  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Returns a Builder for a MusicPiece that allows different parameters to be added to a note
   * object.
   *
   * @return Builder for MusicPiece
   */
  public static CompositionBuilder<MusicEditorModel> builder() {
    return new MusicPiece.Builder();
  }

  /**
   * Builder for MusicPiece
   */
  public static final class Builder implements CompositionBuilder<MusicEditorModel> {
    int tempo;
    List<Note> notes;

    /**
     * Constructs a Builder with a default tempo of 200000 microseconds per beat
     */
    public Builder() {
      tempo = 200000;
      notes = new LinkedList<Note>();
    }

    /**
     * Builds a new piece of composition
     *
     * @return a new composition model for the the type note
     */
    @Override
    public MusicEditorModel<Note> build() {
      MusicEditorModel<Note> m = new MusicPiece(tempo);
      for (Note n : notes) {
        m.addNote(n);
      }
      return m;
    }

    /**
     * updates the tempo to the given
     *
     * @param tempo The speed, in microseconds per beat
     * @return a new compositionBuilder with an updated tempo
     */
    @Override
    public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    /**
     * Converts the given midi pitch to a Tone
     *
     * @param pitch midi pitch to be converted
     * @return Tone of the given midi pitch
     */
    private Tone toTone(int pitch) {
      Octave[] octs = Octave.values();
      Pitch[] pits = Pitch.values();
      return new Tone(pits[pitch % 12], octs[((pitch - 24) / 12)]);
    }

    /**
     * Adds a note from the given midi text files to this piece of composition
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
     *                   piano)
     * @param volume     The volume (in the range [0, 127])
     * @return a new CompositionBuilder with a new note added to it with the given parameters
     */
    @Override
    public CompositionBuilder<MusicEditorModel> addNote(int start, int end, int instrument,
                                                        int pitch, int volume) {
      notes.add(new Note(toTone(pitch), end - start, start, instrument, volume));
      return this;
    }
  }
}
