package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cs3500.music.MusicEditor;
import cs3500.music.util.CompositionBuilder;

/**
 * Represents a piece of music
 */
public final class MusicPiece implements MusicEditorModel {
  private int beatsInMeasure; // beats in a measure
  private int bpm; // beats per minute
  private Map<Integer, List<Note>> beatsToNotes; // integer does not map to empty list

  /**
   * Constructs a MusicPiece
   */
  public MusicPiece() {
    this.beatsInMeasure = 4;
    this.bpm = 120;
    this.beatsToNotes = new HashMap<Integer, List<Note>>();
  }

  /**
   * Constructs a MusicPiece with the given bpm
   *
   * @param beatsInMeasure beats in a measure
   * @param bpm            beats per minute
   */
  public MusicPiece(int beatsInMeasure, int bpm) {
    this();
    this.beatsInMeasure = beatsInMeasure;
    this.bpm = bpm;
  }

  @Override
  public void addNote(Note n) {
    int noteBeat = n.getDownbeat();
    int noteDur = n.getDuration();
    for (int i = 0; i < noteDur; i++) {
      if (this.beatsToNotes.containsKey(noteBeat + i)) {
        this.beatsToNotes.get(noteBeat + i).add(n);
      } else {
        List<Note> newList = new ArrayList<Note>();
        newList.add(n);
        this.beatsToNotes.put(noteBeat + i, newList);
      }
    }
  }

  @Override
  public void addNotes(Note... notes) {
    for (Note n : notes) {
      this.addNote(n);
    }
  }

  @Override
  public boolean removeNote(Note n) {
    int noteBeat = n.getDownbeat();
    boolean temp;
    if (this.beatsToNotes.containsKey(noteBeat)) {
      int noteDur = n.getDuration();
      for (int i = 0; i < noteDur; i++) {
        this.beatsToNotes.get(noteBeat + i).remove(n);
      }
      temp = true;
    } else {
      temp = false;
    }
    cleanEmptyKeys();
    return temp;
  }

  @Override
  public boolean removeAllNotesAt(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Beat cannot be less than zero!");
    }
    boolean temp = this.beatsToNotes.remove(beat) != null;
    return temp;
  }

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

  @Override
  public Map<Integer, List<Note>> allNotes() {
    return new HashMap(this.beatsToNotes);
  }

  @Override
  public MusicEditorModel combineSimultaneous(MusicEditorModel m) {
    MusicEditorModel result = new MusicPiece();
    for (Integer i : this.beatsToNotes.keySet()) {
      List<Note> notes = this.beatsToNotes.get(i);
      result.addNotes(notes.toArray(new Note[notes.size()]));
    }
    Map<Integer, List<Note>> noteMap = m.allNotes();
    Set<Integer> keys = noteMap.keySet();
    for (Integer k : keys) {
      List<Note> givenNotes = noteMap.get(k);
      result.addNotes(givenNotes.toArray(new Note[givenNotes.size()]));
    }
    return result;
  }

  @Override
  public MusicEditorModel combineConsecutive(MusicEditorModel model) {
    MusicEditorModel result = new MusicPiece();
    for (Integer i : this.beatsToNotes.keySet()) {
      List<Note> notes = this.beatsToNotes.get(i);
      result.addNotes(notes.toArray(new Note[notes.size()]));
    }
    Map<Integer, List<Note>> noteMap = model.allNotes();
    Set<Integer> keys = noteMap.keySet();
    int maxKey = Collections.max(this.beatsToNotes.keySet());
    for (Integer i : keys) {
      List<Note> listCopy = new ArrayList<Note>();
      for (Note pn : noteMap.get(i)) {
        listCopy.add(new Note(pn.getTone(), pn.getDuration(), pn.getDownbeat() + maxKey + 1));
      }
      result.addNotes(listCopy.toArray(new Note[listCopy.size()]));
    }
    return result;
  }

  @Override
  public List<Tone> getRange() {
    List<Tone> result = new ArrayList<Tone>();
    Note[] lowhigh = this.lowestHighest();
    Tone lowest = lowhigh[0].getTone();
    Tone highest = lowhigh[1].getTone();
    while (!lowest.equals(highest)) {
      result.add(lowest);
      lowest = this.nextTone(lowest);
    }
    result.add(highest);
    return result;
  }

  /**
   * Removes keys that point to empty Lists in this piece's beatsToNotes
   */
  private void cleanEmptyKeys() {
    Map<Integer, List<Note>> temp = new HashMap(this.beatsToNotes);
    Set<Integer> keys = temp.keySet();
    for (Integer i : keys) {
      if (this.beatsToNotes.get(i).isEmpty()) {
        this.beatsToNotes.remove(i);
      }
    }
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
    switch(t.getPitch()) {
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

  @Override
  public String stringView() {
    String ans = "";
    if (this.beatsToNotes.isEmpty()) {
      ans = "Add Notes to Start Editing Music!";
    }
    else {
      List<Tone> toneRange = this.getRange();
      Set<Integer> keys = this.beatsToNotes.keySet();
      String[][] grid = new String[Collections.max(keys) + 2][toneRange.size() + 1];

      // fills in first row and first column
      int padLeft = Integer.toString(Collections.max(keys)).length();
      String temp = "";
      for (int k = 0; k < padLeft; k++) {
        temp += " ";
      }
      grid[0][0] = temp; // five character space for top left
      for (int i = 0; i < grid.length - 1; i++) {
        grid[i + 1][0] = String.format("%" + Integer.toString(padLeft) + "s", Integer.toString(i));
      }
      for (int i = 0; i < toneRange.size(); i++) {
        if (toneRange.get(i).toString().length() == 4) {
          grid[0][i + 1] = String.format(" " + "%-4s", toneRange.get(i).toString());
        }
        else {
          grid[0][i + 1] = String.format("%4s", toneRange.get(i).toString()) + " ";
        }
      }

      // placeholders for all other rows and columns
      for (int i = 1; i < grid.length; i++) {
        for (int j = 1; j < grid[0].length; j++) {
          grid[i][j] = "     "; // five character space
        }
      }

      // replacing placeholders, if applicable
      for (Integer i : keys) {
        List<Note> notesPlaying = this.beatsToNotes.get(i);
        for (Note pn : notesPlaying) {
          String str;
          if (pn.getDownbeat() == i) {
            str = "  X  ";
          } else {
            str = "  |  ";
          }
          if (!grid[i + 1][pn.getTone().compareTo(toneRange.get(0)) + 1].equals("  X  ")) {
            grid[i + 1][pn.getTone().compareTo(toneRange.get(0)) + 1] = str;
          }
        }
      }

      // building the representation from 2D array
      for (String[] line : grid) {
        for (String box : line) {
          ans += box;
        }
        ans += "\n";
      }
    }
    return ans;
  }

  public static CompositionBuilder<MusicEditorModel> builder() {
    return new MusicPiece.Builder();
  }

  /**
   * Builder
   */
  public static final class Builder implements CompositionBuilder<MusicEditorModel> {
    int tempo;
    List<Note> notes;

    public Builder() {
      tempo = 120;
      notes = new LinkedList<Note>();
    }

    @Override
    public MusicEditorModel build() {
      MusicEditorModel m = new MusicPiece(4, tempo);
      for (Note n : notes) {
        m.addNote(n);
      }
      return m;
    }

    @Override
    public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    private Tone toTone(int pitch) {
      Octave[] octs = Octave.values();
      Pitch[] pits = Pitch.values();
      return new Tone(pits[pitch % 12], octs[((pitch - 24) / 12)]);
    }

    @Override
    public CompositionBuilder<MusicEditorModel> addNote(int start, int end, int instrument, int pitch, int volume) {
      notes.add(new Note(toTone(pitch), end - start, start));
      return this;
    }
  }
}
