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
  private Map<Integer, List<PlayedNote>> beatsToNotes; // integer does not map to empty list

  /**
   * Constructs a MusicPiece
   */
  public MusicPiece() {
    this.beatsInMeasure = 4;
    this.bpm = 120;
    this.beatsToNotes = new HashMap<Integer, List<PlayedNote>>();
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
  public void addNote(PlayedNote n) {
    int noteBeat = n.getBeat();
    int noteDur = n.getDuration();
    for (int i = 0; i < noteDur; i++) {
      if (this.beatsToNotes.containsKey(noteBeat + i)) {
        this.beatsToNotes.get(noteBeat + i).add(n);
      } else {
        List<PlayedNote> newList = new ArrayList<PlayedNote>();
        newList.add(n);
        this.beatsToNotes.put(noteBeat + i, newList);
      }
    }
  }

  @Override
  public boolean removeNote(PlayedNote n) {
    int noteBeat = n.getBeat();
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
    if (beat < 1) {
      throw new IllegalArgumentException("Beat cannot be less than one!");
    }
    boolean temp = this.beatsToNotes.remove(beat) != null;
    return temp;
  }

  @Override
  public List<PlayedNote> notesAt(int beat) throws IllegalArgumentException {
    if (beat < 1) {
      throw new IllegalArgumentException("Beat cannot be less than one!");
    }
    if (this.beatsToNotes.containsKey(beat)) {
      return this.beatsToNotes.get(beat);
    } else {
      return new ArrayList<PlayedNote>();
    }
  }

  @Override
  public Map<Integer, List<PlayedNote>> allNotes() {
    return new HashMap(this.beatsToNotes);
  }

  @Override
  public void combineSimultaneous(MusicEditorModel m) {
    Map<Integer, List<PlayedNote>> noteMap = m.allNotes();
    Set<Integer> keys = noteMap.keySet();
    for (Integer i : keys) {
      if (this.beatsToNotes.containsKey(i)) {
        this.beatsToNotes.get(i).addAll(noteMap.get(i));
      } else {
        this.beatsToNotes.put(i, noteMap.get(i));
      }
    }
  }

  /**
   * Removes keys that point to empty Lists in this piece's beatsToNotes
   */
  private void cleanEmptyKeys() {
    Map<Integer, List<PlayedNote>> temp = new HashMap(this.beatsToNotes);
    Set<Integer> keys = temp.keySet();
    for (Integer i : keys) {
      if (this.beatsToNotes.get(i).isEmpty()) {
        this.beatsToNotes.remove(i);
      }
    }
  }

  @Override
  public void combineConsecutive(MusicEditorModel m) {
    Map<Integer, List<PlayedNote>> noteMap = m.allNotes();
    Set<Integer> keys = noteMap.keySet();
    int maxKey = Collections.max(this.beatsToNotes.keySet());
    for (Integer i : keys) {
      List<PlayedNote> listCopy = new ArrayList<PlayedNote>();
      for (PlayedNote pn : noteMap.get(i)) {
        listCopy.add(new PlayedNote(pn));
      }
      this.beatsToNotes.put(i + maxKey, listCopy);
    }
  }

  /**
   * Creates a two-element array containing the lowest and highest note
   *
   * @return A two-element array containing the lowest and highest note
   */
  private PlayedNote[] lowestHighest() {
    PlayedNote[] arr = new PlayedNote[2];
    Set<Integer> keys = this.beatsToNotes.keySet();
    for (Integer i : keys) {
      PlayedNote pn = this.beatsToNotes.get(i).get(0);
      arr[0] = pn;
      arr[1] = pn;
      break;
    }
    for (Integer i : keys) {
      List<PlayedNote> notesList = this.beatsToNotes.get(i);
      for (PlayedNote pn : notesList) {
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
    if (this.beatsToNotes.isEmpty()) {
      throw new IllegalArgumentException("There is nothing to display!");
    }
    PlayedNote[] lowhigh = this.lowestHighest();
    int noteRange = lowhigh[1].compareTo(lowhigh[0]) + 1;
    Set<Integer> keys = this.beatsToNotes.keySet();
    String[][] grid = new String[Collections.max(keys) + 1][noteRange + 1];

    // fills in first row and first column
    int padLeft = Integer.toString(Collections.max(keys)).length();
    String temp = "";
    for (int k = 0; k < padLeft; k++) {
      temp += " ";
    }
    grid[0][0] = temp; // five character space for top left
    for (int i = 1; i < grid.length; i++) {
      grid[i][0] = String.format("%" + Integer.toString(padLeft) + "s", Integer.toString(i));
    }
    grid[0][1] = String.format("%4s", lowhigh[0]) + " ";
    Note n = lowhigh[0].nextNote();
    for (int j = 2; j < grid[0].length; j++) {
      grid[0][j] = String.format("%4s", n.toString()) + " ";
      n = n.nextNote();
    }

    // placeholders for all other rows and columns
    for (int i = 1; i < grid.length; i++) {
      for (int j = 1; j < grid[0].length; j++) {
        grid[i][j] = "     "; // five character space
      }
    }

    // replacing placeholders, if applicable
    for (Integer i : keys) {
      List<PlayedNote> notesPlaying = this.beatsToNotes.get(i);
      for (PlayedNote pn : notesPlaying) {
        String str;
        if (pn.getBeat() == i) {
          str = "  X  ";
        } else {
          str = "  |  ";
        }
        grid[i][pn.compareTo(lowhigh[0]) + 1] = str;
      }
    }

    // building the representation from 2D array
    String ans = "";
    for (String[] line : grid) {
      for (String box : line) {
        ans += box;
      }
      ans += "\n";
    }
    return ans;
  }
}
