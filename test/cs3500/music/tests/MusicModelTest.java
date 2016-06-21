package cs3500.music.tests;

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

// to test all of the public methods in the MusicModel
public class MusicModelTest {
  private MusicEditorModel<Note> mm = new MusicPiece();

  private MusicEditorModel<Note> piece;
  private MusicEditorModel<Note> piece2;
  private Note e4BeatOne;
  private Note d4BeatThree;
  private Note c4BeatFive;
  private Note d4BeatSeven;
  private Note e4BeatNine;
  private Note e4BeatEleven;
  private Note e4BeatThirteen;

  private Note g3BeatOne;
  private Note g3BeatNine;

  private Tone e4 = new Tone
          (Pitch.E, Octave.FOUR);
  private Tone d4 = new Tone
          (Pitch.D, Octave.FOUR);
  private Tone g3 = new Tone
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

  // to test the method allNotes when the composition is empty
  @Test
  public void testAllNotesEmpty() {
    assertEquals(new HashMap<Integer, List<Note>>(), piece.allNotes());
  }

  // to test the exception for the method notesAt when the note at the beat does not exist
  // because it is negative
  @Test(expected = IllegalArgumentException.class)
  public void testNotesAtNegative() {
    piece.notesAt(-2);
  }

  // to test the method notesAt returns an empty list of notes at a beat if no notes have been
  // added to the composition.
  @Test
  public void testNotesAtEmpty() {
    assertEquals(new ArrayList<Note>(), piece.notesAt(1));
    assertEquals(new ArrayList<Note>(), piece.notesAt(5));
  }

  // to test that all of the notes are there when they are added to an empty composition
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

  //  // to test the method allNotes and that all of the notes have been added to the MusicModel are
  //  // there. This test is for one note
  @Test
  public void testGetNotesOne() {
    Note n = new Note(new Tone(Pitch.C, Octave.ONE), 3, 3);
    mm.addNote(n);

    List<Note> arr = new ArrayList<Note>();
    arr.add(n);
    assertEquals(arr, mm.notesAt(3));
    assertEquals(arr, mm.notesAt(4));
    assertEquals(arr, mm.notesAt(5));
    HashMap<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();
    notes.put(3, arr);
    notes.put(4, arr);
    notes.put(5, arr);
    assertEquals(notes,
            mm.allNotes());
  }

  // to test the method allNotes for a list of notes to see if all of the notes have been added
  // to the Music Model
  @Test
  public void testGetNotesMultiple() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.ONE), 2, 4);
    Note n1 = new Note(new Tone(Pitch.F, Octave.SEVEN), 2, 3);
    mm.addNote(n);
    mm.addNote(n1);
    List<Note> arr3 = new ArrayList<Note>(); // notes at beat 3
    List<Note> arr4 = new ArrayList<Note>(); // notes at beat 4
    List<Note> arr34 = new ArrayList<Note>(); // notes at 3 and 4
    arr3.add(n1);
    arr4.add(n);
    arr34.add(n);
    arr34.add(n1);
    HashMap<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();
    notes.put(3, arr3);
    notes.put(4, arr34);
    notes.put(5, arr4);
    assertEquals(notes,
            mm.allNotes());
  }

  // to test the method addNote when the is one note at the beat
  @Test
  public void testGetNotesAtBeatOne() {
    mm.addNote(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 4, 2));
    List<Note> notes = new ArrayList<Note>();
    notes.add(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 4, 2));
    assertEquals(notes,
            mm.notesAt(4));
  }

  // to test the method notesAt when there are no notes at the beat
  @Test
  public void testGetBeatNotesNoNotes() {
    mm.addNote(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 2, 4));
    List<Note> notes = new ArrayList<Note>();
    assertEquals(notes,
            mm.notesAt(2));
  }

  // to test the method addNote when there are multiple notes at the beat
  @Test
  public void testMultipleNotesAtBeat() {
    mm.addNote(new Note(new Tone(Pitch.D, Octave.SIX), 1, 0));
    mm.addNote(new Note(new Tone(Pitch.D, Octave.FIVE), 5, 0));
    mm.addNote(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 2, 4));
    List<Note> notes = new ArrayList<Note>();
    notes.add(new Note(new Tone(Pitch.D, Octave.SIX), 1, 0));
    notes.add(new Note(new Tone(Pitch.D, Octave.FIVE), 5, 0));
    assertEquals(notes,
            mm.notesAt(0));
  }

  // to test the method add note when the same note is added to a composition
  @Test
  public void testAddSameNote() {
    Note n = new Note(new Tone(Pitch.A, Octave.FIVE), 2, 3);
    mm.addNote(n);
    mm.addNote(n);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    notes.add(n);
    assertEquals(notes,
            mm.notesAt(3));
    assertEquals(notes,
            mm.notesAt(4));
  }

  // to test the method add note when you have two notes that start on the same downbeat
  // but there durations are different
  @Test
  public void testSameDownBeatDiffDuration() {
    Note n = new Note(new Tone(Pitch.FSHARP, Octave.ONE), 3, 4);
    Note n1 = new Note(new Tone(Pitch.FSHARP, Octave.ONE), 1, 4);
    mm.addNote(n);
    mm.addNote(n1);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    notes.add(n1);
    List<Note> notes1 = new ArrayList<Note>();
    notes1.add(n);
    assertEquals(notes,
            mm.notesAt(4));
    assertEquals(notes1,
            mm.notesAt(5));
    assertEquals(notes1,
            mm.notesAt(6));
  }

  // to test the method add note when two notes start on different downbeats but their durations
  // overlap
  @Test
  public void testOverlappingNotes() {
    Note n = new Note(new Tone(Pitch.B, Octave.SEVEN), 2, 0);
    Note n1 = new Note(new Tone(Pitch.B, Octave.SEVEN), 1, 1);
    mm.addNote(n);
    mm.addNote(n1);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    List<Note> notes1 = new ArrayList<Note>();
    notes1.add(n);
    notes1.add(n1);
    assertEquals(notes,
            mm.notesAt(0));
    assertEquals(notes1,
            mm.notesAt(1));
  }

  // to test the exception in the method addNote where the given note is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteNull() {
    mm.addNote(null);
  }

  // to test the exception in the method addNote where the given Tone is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddToneNull() {
    mm.addNote(new Note(null, 0, 1));
  }

  // to test the method addNotes when the user wants to add a song at once
  @Test
  public void testAddSong() {
    Note n = new Note(new Tone(Pitch.F, Octave.FOUR), 4, 10);
    Note n1 = new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 3, 10);
    mm.addNotes(n, n1);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    notes.add(n1);
    List<Note> notes1 = new ArrayList<Note>();
    notes1.add(n);
    assertEquals(notes,
            mm.notesAt(10));
    assertEquals(notes,
            mm.notesAt(11));
    assertEquals(notes,
            mm.notesAt(12));
    assertEquals(notes1,
            mm.notesAt(13));
  }

  // to test the method addNotes when no songs are added
  @Test
  public void testAddNoNotes() {
    mm.addNotes();
    Map<Integer, List<Note>> notes = new HashMap<Integer, List<Note>>();
    assertEquals(notes,
            mm.allNotes());
  }

  // to test the exception in the method addNotes when one of the notes added is null
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullNote() {
    Note n = null;
    Note n1 = new Note(new Tone(Pitch.B, Octave.EIGHT), 3, 4);
    mm.addNotes(n, n1);
  }

  // to test the method removeNote when no notes have been added to the composition
  @Test
  public void testNoNotesAdded() {
    Note n = new Note(new Tone(Pitch.B, Octave.EIGHT), 3, 4);
    assertFalse(mm.removeNote(n));
  }

  // to test the method removeNote after one note has been added
  @Test
  public void testRemoveOneNote() {
    Note n = new Note(new Tone(Pitch.A, Octave.TEN), 3, 2);
    mm.addNote(n);
    mm.removeNote(n);
    ArrayList<Note> notes = new ArrayList<Note>();
    assertEquals(notes,
            mm.notesAt(2));
    assertEquals(notes,
            mm.notesAt(3));
    assertEquals(notes,
            mm.notesAt(4));
  }

  // to test the method removeNote when several notes have been added and one is removed
  @Test
  public void testRemoveOneNoteMultipleAdded() {
    Note n = new Note(new Tone(Pitch.A, Octave.TEN), 3, 2);
    Note n1 = new Note(new Tone(Pitch.B, Octave.TEN), 2, 5);
    Note n2 = new Note(new Tone(Pitch.ASHARP, Octave.NINE), 2, 4);
    mm.addNote(n);
    mm.addNote(n1);
    mm.addNote(n2);
    mm.removeNote(n1);
    ArrayList<Note> notes = new ArrayList<Note>();
    notes.add(n2);
    ArrayList<Note> notes1 = new ArrayList<Note>();
    assertEquals(notes,
            mm.notesAt(5));
    assertEquals(notes1,
            mm.notesAt(6));
  }

  // to test the method removeNote when the same note has been added twice
  @Test
  public void testRemoveSameNote() {
    Note n = new Note(new Tone(Pitch.A, Octave.TEN), 3, 2);
    mm.addNote(n);
    mm.addNote(n);
    mm.removeNote(n);
    ArrayList<Note> notes = new ArrayList<Note>();
    notes.add(n);
    assertEquals(notes,
            mm.notesAt(2));
    mm.removeNote(n);
    ArrayList<Note> notesmt = new ArrayList<Note>();
    assertEquals(notesmt,
            mm.notesAt(2));
  }

  // to test the method removeNote when two notes that are added are overlapping
  @Test
  public void testOverlappingRemove() {
    Note n = new Note(new Tone(Pitch.B, Octave.SIX), 2, 3);
    Note n1 = new Note(new Tone(Pitch.B, Octave.SIX), 2, 2);
    mm.addNote(n);
    mm.addNote(n1);
    mm.removeNote(n1);
    ArrayList<Note> notes = new ArrayList<Note>();
    notes.add(n);
    assertEquals(notes,
            mm.notesAt(3));
  }

  // to test when the note that one desires to remove is not in the editor
  @Test
  public void testRemoveNotThere() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 4, 0);
    assertFalse(mm.removeNote(n));
  }

  // to test the exception when the given note to be removed is null
  @Test(expected = IllegalArgumentException.class)
  public void testRemovedNull() {
    Note n = null;
    mm.removeNote(n);
  }

  // to test the method removeNotes when several notes are to be removed at once
  @Test
  public void testRemoveNotes() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 4, 0);
    Note n1 = new Note(new Tone(Pitch.A, Octave.EIGHT), 2, 0);
    Note n2 = new Note(new Tone(Pitch.B, Octave.EIGHT), 1, 0);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    notes.add(n1);
    notes.add(n2);
    List<Note> notesmt = new ArrayList<Note>();
    mm.removeAllNotesAt(0);
    assertEquals(notesmt,
            mm.notesAt(0));
  }

  // to test the method removeNotes when there are no notes to remove at the given beat
  @Test
  public void testNoNotesRemoved() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 4, 0);
    Note n1 = new Note(new Tone(Pitch.A, Octave.EIGHT), 2, 0);
    Note n2 = new Note(new Tone(Pitch.B, Octave.EIGHT), 1, 0);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n);
    notes.add(n1);
    notes.add(n2);

    assertFalse(mm.removeAllNotesAt(100));
  }

  // to test the method removeNote if the note is not there
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

  // to test the method removeNote when the notes do not overlap
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

  // to test the exception if the given beat is negative for the method removeAllNotesAt
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAllNotesAtNegative() {
    piece.removeAllNotesAt(-3);
  }

  // to test the method remove when there are no notes in this composition
  @Test
  public void testRemoveAllNotesAtEmpty() {
    assertFalse(piece.removeAllNotesAt(1));
  }

  // to remove notes at a single beat
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

  // to remove Notes at a beat where there are notes that have durations that overlap that beat
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

  // to test the method combine simultaneously when both notes are empty
  @Test
  public void testCombineBothEmpty() {
    Note n = new Note(new Tone(Pitch.A, Octave.EIGHT), 10, 0);
  }

  // to combine a piece with notes with a piece that is empty
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

    assertEquals(NoteMap, piece.combineSimultaneous(piece2).allNotes());
  }

  // to test the method combineSimultaneous when both compositions have notes
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
    assertEquals(NoteMap, piece.combineSimultaneous(piece2).allNotes());
  }

  // to test the method combineConsecutive when one of the pieces is empty
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

    assertEquals(arr1, piece.notesAt(1));
  }

  // to test the method combineConsecutive when both pieces have notes.
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
    arr1.add(g3BeatOne);
    arr1.add(e4BeatOne);
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
    NoteMap.put(10, arr5);
    NoteMap.put(11, arr5);
    NoteMap.put(12, arr6);
    NoteMap.put(13, arr6);
    assertTrue(arr1.containsAll(piece.combineConsecutive(piece2).notesAt(1)));
  }

  // to test the method getRange when there are no notes in this composition
  @Test
  public void testGetRangeNoNotes() {
    List<Note> notes = new ArrayList<Note>();
    assertEquals(notes,
            mm.getRange());
  }

  // to test the method getRange when there are multiple notes in this composition
  @Test
  public void testGetRangeNotes() {
    Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 2, 0);
    Note n1 = new Note(new Tone(Pitch.A, Octave.EIGHT), 3, 1);
    List<Note> notes = new ArrayList<Note>();
    mm.addNote(n);
    mm.addNote(n1);
    notes.add(n);
    notes.add(new Note(new Tone(Pitch.ASHARP, Octave.SEVEN), 1, 0));
    notes.add(new Note(new Tone(Pitch.B, Octave.SEVEN), 1, 0));
    notes.add(new Note(new Tone(Pitch.C, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.CSHARP, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.D, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.DSHARP, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.E, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.F, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.FSHARP, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.G, Octave.EIGHT), 1, 0));
    notes.add(new Note(new Tone(Pitch.GSHARP, Octave.EIGHT), 1, 0));
    notes.add(n1);
    assertEquals(notes.toString(),
            mm.getRange().toString());
  }

}

