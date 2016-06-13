import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.PlayedNote;

import static org.junit.Assert.*;

/**
 * Tests the MusicPiece class
 */
public class MusicPieceTest {

  MusicEditorModel piece;
  MusicEditorModel piece2;
  PlayedNote e4BeatOne;
  PlayedNote d4BeatThree;
  PlayedNote c4BeatFive;
  PlayedNote d4BeatSeven;
  PlayedNote e4BeatNine;
  PlayedNote e4BeatEleven;
  PlayedNote e4BeatThirteen;

  PlayedNote g3BeatOne;
  PlayedNote g3BeatNine;

  Note e4 = new Note(Pitch.E, 4);
  Note d4 = new Note(Pitch.D, 4);
  Note g3 = new Note(Pitch.G, 3);

  @Before
  public void init() {
    piece = new MusicPiece();
    piece2 = new MusicPiece();
    e4BeatOne = new PlayedNote(e4, 2, 1);
    d4BeatThree = new PlayedNote(d4, 2, 3);
    c4BeatFive = new PlayedNote(new Note(Pitch.C, 4), 2, 5);
    d4BeatSeven = new PlayedNote(d4, 2, 7);
    e4BeatNine = new PlayedNote(e4, 2, 9);
    e4BeatEleven = new PlayedNote(e4, 2, 11);
    e4BeatThirteen = new PlayedNote(e4, 2, 13);
    g3BeatOne = new PlayedNote(g3, 7, 1);
    g3BeatNine = new PlayedNote(g3, 7, 9);
  }

  @Test
  public void testAllNotesEmpty() {
    assertEquals(new HashMap<Integer, List<PlayedNote>>(), piece.allNotes());
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
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(1));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(5));
  }

  @Test
  public void testAddNoteToEmpty() {
    piece.addNote(e4BeatOne);
    List<PlayedNote> arr = new ArrayList<PlayedNote>();
    arr.add(e4BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(3));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr);
    noteMap.put(2, arr);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testAddNoteToNonEmptyNonOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(d4BeatThree);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr1, piece.notesAt(2));
    assertEquals(arr2, piece.notesAt(3));
    assertEquals(arr2, piece.notesAt(4));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(5));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testAddNoteToNonEmptyOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(g3BeatOne);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr1, piece.notesAt(2));
    assertEquals(arr2, piece.notesAt(3));
    assertEquals(arr2, piece.notesAt(7));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(8));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);
    noteMap.put(5, arr2);
    noteMap.put(6, arr2);
    noteMap.put(7, arr2);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testSameNoteOverlap() {
    piece.addNote(e4BeatOne);
    PlayedNote e4BeatTwo = new PlayedNote(new Note(Pitch.E, 4), 2, 2);
    piece.addNote(e4BeatTwo);

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(e4BeatOne);
    arr2.add(e4BeatTwo);
    List<PlayedNote> arr3 = new ArrayList<PlayedNote>();;
    arr3.add(e4BeatTwo);
    assertEquals(arr1, piece.notesAt(1));
    assertEquals(arr2, piece.notesAt(2));
    assertEquals(arr3, piece.notesAt(3));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr2);
    noteMap.put(3, arr3);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testRemoveNoteNotThere() {
    assertFalse(piece.removeNote(e4BeatOne));
  }

  @Test
  public void testRemoveNoteToEmpty() {
    piece.addNote(e4BeatOne);
    assertTrue(piece.removeNote(e4BeatOne));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(1));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(2));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testRemoveToNonEmptyNoOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    assertTrue(piece.removeNote(d4BeatThree));

    List<PlayedNote> arr = new ArrayList<PlayedNote>();
    arr.add(e4BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(3));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr);
    noteMap.put(2, arr);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testRemoveToNonEmptyOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    assertTrue(piece.removeNote(e4BeatOne));

    List<PlayedNote> arr = new ArrayList<PlayedNote>();
    arr.add(g3BeatOne);
    assertEquals(arr, piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(arr, piece.notesAt(7));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(8));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    for (int i = 1; i < 8; i++) {
      noteMap.put(i, arr);
    }
    assertEquals(noteMap, piece.allNotes());
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
    List<PlayedNote> arr = new ArrayList<PlayedNote>();
    arr.add(e4BeatOne);
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(1));
    assertEquals(arr, piece.notesAt(2));
    assertEquals(new ArrayList<PlayedNote>(), piece.notesAt(3));

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(2, arr);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testRemoveAllNotesAtOverlap() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatOne);
    assertTrue(piece.removeAllNotesAt(2));

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(g3BeatOne);

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    for (int i = 3; i < 8; i++) {
      noteMap.put(i, arr2);
    }
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testCombineSimultaneousWithEmpty() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(d4BeatThree);

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);

    piece.combineSimultaneous(piece2);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testCombineSimultaneous() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece2.addNote(g3BeatOne);

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<PlayedNote> arr3 = new ArrayList<PlayedNote>();
    arr3.add(g3BeatOne);

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);
    noteMap.put(5, arr3);
    noteMap.put(6, arr3);
    noteMap.put(7, arr3);

    piece.combineSimultaneous(piece2);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testCombineConsecutiveWithEmpty() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(g3BeatOne);

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<PlayedNote> arr3 = new ArrayList<PlayedNote>();
    arr3.add(g3BeatOne);

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);
    noteMap.put(5, arr3);
    noteMap.put(6, arr3);
    noteMap.put(7, arr3);

    piece.combineConsecutive(piece2);
    assertEquals(noteMap, piece.allNotes());
  }

  @Test
  public void testCombineConsecutive() {
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(g3BeatOne);
    PlayedNote c4BeatEight = new PlayedNote(new Note(Pitch.C, 4), 1, 8);
    piece.addNote(c4BeatEight);

    PlayedNote e4BeatOne = new PlayedNote(new Note(Pitch.E, 4), 2, 1);
    piece2.addNote(e4BeatOne);
    PlayedNote e4BeatThree = new PlayedNote(new Note(Pitch.E, 4), 2, 3);
    piece2.addNote(e4BeatThree);

    List<PlayedNote> arr1 = new ArrayList<PlayedNote>();
    arr1.add(e4BeatOne);
    arr1.add(g3BeatOne);
    List<PlayedNote> arr2 = new ArrayList<PlayedNote>();
    arr2.add(d4BeatThree);
    arr2.add(g3BeatOne);
    List<PlayedNote> arr3 = new ArrayList<PlayedNote>();
    arr3.add(g3BeatOne);
    List<PlayedNote> arr4 = new ArrayList<PlayedNote>();
    arr4.add(c4BeatEight);

    List<PlayedNote> arr5 = new ArrayList<PlayedNote>();
    arr5.add(e4BeatOne);
    List<PlayedNote> arr6 = new ArrayList<PlayedNote>();
    arr6.add(e4BeatThree);

    Map<Integer, List<PlayedNote>> noteMap = new HashMap<Integer, List<PlayedNote>>();
    noteMap.put(1, arr1);
    noteMap.put(2, arr1);
    noteMap.put(3, arr2);
    noteMap.put(4, arr2);
    noteMap.put(5, arr3);
    noteMap.put(6, arr3);
    noteMap.put(7, arr3);
    noteMap.put(8, arr4);
    noteMap.put(9, arr5);
    noteMap.put(10, arr5);
    noteMap.put(11, arr6);
    noteMap.put(12, arr6);

    piece.combineConsecutive(piece2);
    assertEquals(noteMap, piece.allNotes());
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