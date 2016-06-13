import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the MusicPiece class
 */
public class MusicPieceTest {

  MusicEditorModel piece;
  MusicEditorModel piece2;
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

  @Before
  public void init() {
    piece = new MusicPiece();
    piece2 = new MusicPiece();
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
  }

  @Test
  public void testAllNotesEmpty() {
    assertEquals(new HashMap<Integer, List<Note>>(), piece.allNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotesAtNegative() {
    piece.notesAt(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotesAtZero() {
    piece.notesAt(0);
  }

  @Test
  public void testNotesAtEmpty() {
    assertEquals(new ArrayList<Note>(), piece.notesAt(1));
    assertEquals(new ArrayList<Note>(), piece.notesAt(5));
  }

  @Test
  public void testAddNoteToEmpty() {
    piece.addNote(e4BeatOne);
    List<Note> arr = new ArrayList<Note>();
    arr.add(e4BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<Note>(), piece.notesAt(3));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr);
    NoteMap.put(2, arr);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testAddNoteToNonEmptyNonOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(d4BeatThree);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr1, piece.notesAt(2));
    assertEquals(arr2, piece.notesAt(3));
    assertEquals(arr2, piece.notesAt(4));
    assertEquals(new ArrayList<Note>(), piece.notesAt(5));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testAddNoteToNonEmptyOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(g3BeatOne);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr1, piece.notesAt(2));
    assertEquals(arr2, piece.notesAt(3));
    assertEquals(arr2, piece.notesAt(7));
    assertEquals(new ArrayList<Note>(), piece.notesAt(8));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);
    NoteMap.put(5, arr2);
    NoteMap.put(6, arr2);
    NoteMap.put(7, arr2);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testSameNoteOverlap() {
    piece.addNote(e4BeatOne);
    Note e4BeatTwo = new Note(new Tone
            (Pitch.E, Octave.FOUR), 2, 2);
    piece.addNote(e4BeatTwo);

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(e4BeatOne);
    arr2.add(e4BeatTwo);
    List<Note> arr3 = new ArrayList<Note>();;
    arr3.add(e4BeatTwo);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr2, piece.notesAt(2));
    assertEquals(arr3, piece.notesAt(3));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr2);
    NoteMap.put(3, arr3);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testRemoveNoteNotThere() {
    assertFalse(piece.removeNote(e4BeatOne));
  }

  @Test
  public void testRemoveNoteToEmpty() {
    piece.addNote(e4BeatOne);
    assertTrue(piece.removeNote(e4BeatOne));
    assertEquals(new ArrayList<Note>(), piece.notesAt(1));
    assertEquals(new ArrayList<Note>(), piece.notesAt(2));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testRemoveToNonEmptyNoOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    assertTrue(piece.removeNote(d4BeatThree));

    List<Note> arr = new ArrayList<Note>();
    arr.add(e4BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<Note>(), piece.notesAt(3));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr);
    NoteMap.put(2, arr);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testRemoveToNonEmptyOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    assertTrue(piece.removeNote(e4BeatOne));

    List<Note> arr = new ArrayList<Note>();
    arr.add(g3BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(arr, piece.notesAt(7));
    assertEquals(new ArrayList<Note>(), piece.notesAt(8));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    for (int i = 1; i < 8; i++) {
      NoteMap.put(i, arr);
    }
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAllNotesAtNegative() {
    piece.removeAllNotesAt(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAllNotesAtZero() {
    piece.removeAllNotesAt(0);
  }

  @Test
  public void testRemoveAllNotesAtEmpty() {
    assertFalse(piece.removeAllNotesAt(1));
  }

  @Test
  public void testRemoveAllNotesAtSingle() {
    piece.addNote(e4BeatOne);
    assertTrue(piece.removeAllNotesAt(1));
    List<Note> arr = new ArrayList<Note>();
    arr.add(e4BeatOne);
    assertEquals(new ArrayList<Note>(), piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<Note>(), piece.notesAt(3));

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(2, arr);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testRemoveAllNotesAtOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    assertTrue(piece.removeAllNotesAt(2));

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(g3BeatOne);

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    for (int i = 3; i < 8; i++) {
      NoteMap.put(i, arr2);
    }
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testCombineSimultaneousWithEmpty() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(d4BeatThree);

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);

    piece.combineSimultaneous(piece2);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testCombineSimultaneous() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece2.addNote(g3BeatOne);

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<Note> arr3 = new ArrayList<Note>();
    arr3.add(g3BeatOne);

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);
    NoteMap.put(5, arr3);
    NoteMap.put(6, arr3);
    NoteMap.put(7, arr3);

    piece.combineSimultaneous(piece2);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testCombineConsecutiveWithEmpty() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(g3BeatOne);

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<Note> arr3 = new ArrayList<Note>();
    arr3.add(g3BeatOne);

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);
    NoteMap.put(5, arr3);
    NoteMap.put(6, arr3);
    NoteMap.put(7, arr3);

    piece.combineConsecutive(piece2);
    assertEquals(NoteMap, piece.allNotes());
  }

  @Test
  public void testCombineConsecutive() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(g3BeatOne);
    Note c4BeatEight = new Note(new Tone
            (Pitch.C, Octave.FOUR), 1, 8);
    piece.addNote(c4BeatEight);

    Note e4BeatOne = new Note(new Tone
            (Pitch.E, Octave.FOUR), 2, 1);
    piece2.addNote(e4BeatOne);
    Note e4BeatThree = new Note(new Tone
            (Pitch.E, Octave.FOUR), 2, 3);
    piece2.addNote(e4BeatThree);

    List<Note> arr1 = new ArrayList<Note>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<Note> arr2 = new ArrayList<Note>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<Note> arr3 = new ArrayList<Note>();
    arr3.add(g3BeatOne);
    List<Note> arr4 = new ArrayList<Note>();
    arr4.add(c4BeatEight);

    List<Note> arr5 = new ArrayList<Note>();
    arr5.add(e4BeatOne);
    List<Note> arr6 = new ArrayList<Note>();
    arr6.add(e4BeatThree);

    Map<Integer, List<Note>> NoteMap = new HashMap<Integer, List<Note>>();
    NoteMap.put(1, arr1);
    NoteMap.put(2, arr1);
    NoteMap.put(3, arr2);
    NoteMap.put(4, arr2);
    NoteMap.put(5, arr3);
    NoteMap.put(6, arr3);
    NoteMap.put(7, arr3);
    NoteMap.put(8, arr4);
    NoteMap.put(9, arr5);
    NoteMap.put(10, arr5);
    NoteMap.put(11, arr6);
    NoteMap.put(12, arr6);

//    piece.combineConsecutive(piece2);
//    assertEquals(NoteMap, piece.allNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringViewNoNotes() {
    piece.stringView();
  }

  @Test
  public void testStringView() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(c4BeatFive);
    piece.addNote(d4BeatSeven);
    piece.addNote(e4BeatNine);
    piece.addNote(e4BeatEleven);
    piece.addNote(e4BeatThirteen);
    piece.addNote(g3BeatOne);
    piece.addNote(g3BeatNine);
    assertEquals("    G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n" +
                 " 1  X                                            X  \n" +
                 " 2  |                                            |  \n" +
                 " 3  |                                  X            \n" +
                 " 4  |                                  |            \n" +
                 " 5  |                        X                      \n" +
                 " 6  |                        |                      \n" +
                 " 7  |                                  X            \n" +
                 " 8                                     |            \n" +
                 " 9  X                                            X  \n" +
                 "10  |                                            |  \n" +
                 "11  |                                            X  \n" +
                 "12  |                                            |  \n" +
                 "13  |                                            X  \n" +
                 "14  |                                            |  \n" +
                 "15  |                                               \n", piece.stringView());
  }
}