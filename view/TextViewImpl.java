package cs3500.music.view;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;

/**
 * Text view implementation that renders the notes in this composition in the console with their
 * range of tones and beat numbers
 */
public class TextViewImpl implements MusicEditorView<Note> {

  private Appendable out; // to be appended to for console rendering

  // to construct a text view of a composition
  public TextViewImpl() {
    this.out = System.out;
  }

  // convenience constructor for testing
  public TextViewImpl(Appendable out) {
    this.out = out;
  }

  /**
   * Method not supported in this implementation
   */
  @Override
  public void initialize() {
  }


  /**
   * Creates a String representation of this model. Columns are five characters. The leftmost column
   * represents the beats, right-justified. The top row represents each pitch. Each note head is " X
   * " and each note-sustain is "  |  ".
   *
   * @param m model whose Notes is to be displayed
   * @return String representation of this model
   */
  @Override
  public void display(ReadOnlyModel<Note> m) {
    String ans = "";
    if (m.allNotes().isEmpty()) {
      ans = "Add Notes to Start Editing Music!";
    } else {
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
        } else {
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
    try {
      out.append(ans);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
