package cs3500.music.view;


import java.util.Collections;
import java.util.List;
import java.util.Set;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;

/**
 * Text view implementation
 */
public class TextViewImpl implements MusicEditorView<Note> {

  @Override
  public void initialize() {
  }


  @Override
  public void display(ReadOnlyModel<Note> m) {
    String ans = "";
    if (m.allNotes().isEmpty()) {
      ans = "Add Notes to Start Editing Music!";
    }
    else {
      List<Note> noteRange = m.getRange();
      Set<Integer> keys = m.allNotes().keySet();
      String[][] grid = new String[Collections.max(keys) + 2][noteRange.size() + 1];

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
      for (int i = 0; i < noteRange.size(); i++) {
        if (noteRange.get(i).toString().length() == 4) {
          grid[0][i + 1] = String.format(" " + "%-4s", noteRange.get(i).toString());
        }
        else {
          grid[0][i + 1] = String.format("%4s", noteRange.get(i).toString()) + " ";
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
        List<Note> notesPlaying = m.allNotes().get(i);
        for (Note pn : notesPlaying) {
          String str;
          if (pn.getDownbeat() == i) {
            str = "  X  ";
          } else {
            str = "  |  ";
          }
          if (!grid[i + 1][pn.compareTo(noteRange.get(0)) + 1].equals("  X  ")) {
            grid[i + 1][pn.compareTo(noteRange.get(0)) + 1] = str;
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
    System.out.println(ans);
  }


  public static void main(String[] args) {
    MusicEditorView t = new TextViewImpl();
    MusicEditorModel piece;
    Note e4BeatOne;
    Note d4BeatThree;
    Note c4BeatFive;
    Note d4BeatSeven;
    Note e4BeatNine;
    Note e4BeatEleven;
    Note e4BeatThirteen;

    Note g3BeatOne;
    Note g3BeatNine;

    Tone e4 = new Tone
            (Pitch.E, Octave.FOUR);
    Tone d4 = new Tone
            (Pitch.D, Octave.FOUR);
    Tone g3 = new Tone
            (Pitch.G, Octave.THREE);

      piece = new MusicPiece();
      e4BeatOne = new Note(e4, 2, 1);
      d4BeatThree = new Note(d4, 2, 3);
      c4BeatFive = new Note(new Tone
              (Pitch.C, Octave.FOUR), 2, 5);
      d4BeatSeven = new Note(d4, 2, 7);
      e4BeatNine = new Note(e4, 2, 9);
      e4BeatEleven = new Note(e4, 2, 11);
      e4BeatThirteen = new Note(e4, 2, 13);
      g3BeatOne = new Note(g3, 7, 1);
      g3BeatNine = new Note(g3, 7, 9);
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(c4BeatFive);
    piece.addNote(d4BeatSeven);
    piece.addNote(e4BeatNine);
    piece.addNote(e4BeatEleven);
    piece.addNote(e4BeatThirteen);
    piece.addNote(g3BeatOne);
    piece.addNote(g3BeatNine);

    t.display(piece);
  }
}
