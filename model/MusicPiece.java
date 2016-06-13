package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a piece of music
 */
public class MusicPiece implements MusicEditorModel {
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
  public void combineSimultaneous(MusicEditorModel m) {
    Map<Integer, List<Note>> noteMap = m.allNotes();
    Set<Integer> keys = noteMap.keySet();
    for (Integer i : keys) {
      if (this.beatsToNotes.containsKey(i)) {
        this.beatsToNotes.get(i).addAll(noteMap.get(i));
      } else {
        this.beatsToNotes.put(i, noteMap.get(i));
      }
    }
  }

  @Override
  public void combineConsecutive(MusicEditorModel model) {
    Map<Integer, List<Note>> noteMap = model.allNotes();
    Set<Integer> keys = noteMap.keySet();
    int maxKey = Collections.max(this.beatsToNotes.keySet());
    for (Integer i : keys) {
      List<Note> listCopy = new ArrayList<Note>();
      for (Note pn : noteMap.get(i)) {
        listCopy.add(new Note(pn));
      }
      this.beatsToNotes.put(i + maxKey, listCopy);
    }
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

  @Override
  public String stringView() throws IllegalArgumentException {
    String ans = "";
    if (this.beatsToNotes.isEmpty()) {
      ans = "Add Notes to Start Editing Music!";
    }
    else {
      Note[] lowhigh = this.lowestHighest();
      int noteRange = lowhigh[1].compareTo(lowhigh[0]) + 1;
      Set<Integer> keys = this.beatsToNotes.keySet();
      String[][] grid = new String[Collections.max(keys) + 2][noteRange + 1];

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
      grid[0][1] = String.format("%4s", lowhigh[0]) + " ";
      Tone n = lowhigh[0].nextTone();
      for (int j = 2; j < grid[0].length; j++) {
        grid[0][j] = String.format("%4s", n.toString()) + " ";
        n = n.nextTone();
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
          grid[i + 1][pn.compareTo(lowhigh[0]) + 1] = str;
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
}
