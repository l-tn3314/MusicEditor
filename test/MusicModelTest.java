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

// to test all of the public methods in the MusicModel
public class MusicModelTest {
  MusicEditorModel mm = new MusicPiece();

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


  // to test the method stringView is not paddint any beats when the
  // total duration of a piece of music is less than 10.
  @Test
  public void testPaddingOneChar() {
    Note n = new Note(new Tone(Pitch.A, Octave.NINE), 3, 4);
    mm.addNote(n);
    assertEquals("   A9 \n" +
                    "0     \n" +
                    "1     \n" +
                    "2     \n" +
                    "3     \n" +
                    "4  X  \n" +
                    "5  |  \n" +
                    "6  |  \n",
            mm.stringView());
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

  // to test the method getNotes for a list of notes to see if all of the notes have been added
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

//  // to test the method getNotes when no notes have been added to the Music Model
//  @Test
//  public void testGetNotesNoNotes() {
//    ArrayList<Note> notes = new ArrayList<Note>();
//    assertEquals(notes,
//            mm.getNotes());
//  }
//
  // to test the method getNotesAtBeat() when the is one note at the beat
  @Test
  public void testGetNotesAtBeatOne() {
    mm.addNote(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 4, 2));
    List<Note> notes = new ArrayList<Note>();
    notes.add(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 4, 2));
    assertEquals(notes,
            mm.notesAt(4));
  }

  // to test the method getNotesAtBeat() when there are multiple notes at the beat
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

  // to test the method getNotesAtBeat when there are no notes at the beat
  @Test
  public void testGetBeatNotesNoNotes() {
    mm.addNote(new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 2, 4));
    List<Note> notes = new ArrayList<Note>();
    assertEquals(notes,
            mm.notesAt(2));
  }

  // to test the method stringView is padding the beats corresponding
  // to the length of the longest beat in the song when the longest beat is
  // 2 characters long (between 10 - 99)
  @Test
  public void testPaddingTwoChar() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 4, 10);
    mm.addNote(n);
    assertEquals("   A#8 \n" +
                    " 0     \n" +
                    " 1     \n" +
                    " 2     \n" +
                    " 3     \n" +
                    " 4     \n" +
                    " 5     \n" +
                    " 6     \n" +
                    " 7     \n" +
                    " 8     \n" +
                    " 9     \n" +
                    "10  X  \n" +
                    "11  |  \n" +
                    "12  |  \n" +
                    "13  |  \n",
            mm.stringView());
  }

  // to test the method initializeEditor when given a list of only one note
  // with a duration of one
  @Test
  public void testOneNoteDurationOne() {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 1, 0);
    mm.addNote(n);
    assertEquals("  A#8 \n" +
                    "0  X  \n",
            mm.stringView());
  }

  // to test the method addNote for one note and the duration is greater than one
  @Test
  public void testOneNoteDurationBiggerThanOne() {
    Note n = new Note(new Tone(Pitch.C, Octave.FOUR), 3, 4);
    mm.addNote(n);
    assertEquals("   C4 \n" +
                    "0     \n" +
                    "1     \n" +
                    "2     \n" +
                    "3     \n" +
                    "4  X  \n" +
                    "5  |  \n" +
                    "6  |  \n",
            mm.stringView());
  }

  // to test initializeEditor when given a list of multiple notes each with one duration and no
  // overlaps
  @Test
  public void testOneDuration() {
    Note n = new Note(new Tone(Pitch.C, Octave.EIGHT), 1, 0);
    Note n1 = new Note(new Tone(Pitch.F, Octave.EIGHT), 1, 4);
    Note n2 = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 1, 3);
    Note n3 = new Note(new Tone(Pitch.B, Octave.EIGHT), 1, 2);

    mm.addNote(n);
    mm.addNote(n1);
    mm.addNote(n2);
    mm.addNote(n3);

    assertEquals("   C8  C#8   D8  D#8   E8   F8  F#8   G8  G#8   A8  A#8   B8 \n" +
                    "0  X                                                         \n" +
                    "1                                                            \n" +
                    "2                                                         X  \n" +
                    "3                                                    X       \n" +
                    "4                           X                                \n",
            mm.stringView());
  }

  // to test the method initializeEditor when given a list of notes of different
  // durations that do not overlap
  @Test
  public void testMultipleDiffDurations() {
    List<Note> notes = new ArrayList<Note>();
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 5, 1);
    mm.addNote(n);
    mm.addNote(n1);
    mm.addNote(n2);
    assertEquals("   D4  D#4   E4   F4  F#4   G4  G#4   A4 \n" +
                    "0                                        \n" +
                    "1       X                                \n" +
                    "2       |                             X  \n" +
                    "3  X    |                             |  \n" +
                    "4  |    |                                \n" +
                    "5  |    |                                \n",
            mm.stringView());
  }

//
//  // to test initializeEditor when two notes in the given list are
//  // the same tone, have the same downbeat, and have the same duration
//  @Test
//  public void testOverlapSameDuration() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
//    Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
//    notes.add(n);
//    notes.add(n1);
//    mm.initializeEditor(notes);
//    assertEquals("  A7 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3     \n" +
//                    "4  X  \n" +
//                    "5  |  \n" +
//                    "6  |  \n" +
//                    "7  |  \n",
//            mm.stringView());
//  }
//
//  // to test initializeEditor when two notes have the same tone and the same
//  // downbeat but one of the durations is shorter
//  @Test
//  public void testOverlayDifferentDuration() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
//    Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 2);
//    notes.add(n);
//    notes.add(n1);
//    mm.initializeEditor(notes);
//    assertEquals("  A7 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3     \n" +
//                    "4  X  \n" +
//                    "5  |  \n" +
//                    "6  |  \n" +
//                    "7  |  \n",
//            mm.stringView());
//  }
//
//  // to test initializeEditor when two notes have the same tone and their downbeats
//  // are right after each other and their durations overlap
//  @Test
//  public void testConsecutiveDownbeat() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
//    Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 5, 2);
//    notes.add(n);
//    notes.add(n1);
//    mm.initializeEditor(notes);
//    assertEquals("  A7 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3     \n" +
//                    "4  X  \n" +
//                    "5  X  \n" +
//                    "6  |  \n" +
//                    "7  |  \n",
//            mm.stringView());
//  }
//
//  // to test initializeEditor when two notes have the same tone and their downbeats
//  // are not right after eachother but their durations overlap.
//  @Test
//  public void testNotConsecutiveDownbeat() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.TWO), 4, 4);
//    Note n1 = new Note(new Tone(Pitch.A, Octave.TWO), 6, 2);
//    notes.add(n);
//    notes.add(n1);
//    mm.initializeEditor(notes);
//    assertEquals("  A2 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3     \n" +
//                    "4  X  \n" +
//                    "5  |  \n" +
//                    "6  X  \n" +
//                    "7  |  \n",
//            mm.stringView());
//  }
//
//  // to test the method initializeEditor when multiple notes are played one
//  // after another
//  @Test
//  public void testRightAfterEachOther() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.C, Octave.FOUR), 0, 2);
//    Note n1 = new Note(new Tone(Pitch.C, Octave.FOUR), 2, 4);
//    Note n2 = new Note(new Tone(Pitch.CSHARP, Octave.FOUR), 0, 3);
//    Note n3 = new Note(new Tone(Pitch.CSHARP, Octave.FOUR), 3, 2);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    notes.add(n3);
//    mm.initializeEditor(notes);
//    assertEquals("  C4  C♯4 \n" +
//                    "0  X    X  \n" +
//                    "1  |    |  \n" +
//                    "2  X    |  \n" +
//                    "3  |    X  \n" +
//                    "4  |    |  \n" +
//                    "5  |       \n",
//            mm.stringView());
//  }
//
//  // to test the method initializeEditor when two notes overlap but another one does not
//  @Test
//  public void testOverlapMultipleNotes() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.TWO), 5, 4);
//    Note n1 = new Note(new Tone(Pitch.B, Octave.THREE), 3, 9);
//    Note n2 = new Note(new Tone(Pitch.B, Octave.THREE), 4, 3);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes); // NOTE: had to adjust spacing for line limit
//    assertEquals("   A2  A♯2   B2   C3  C♯3   D3  D♯3   E3   F3  F♯3   G3 " +
//                    " G♯3   A3  A♯3   B3 \n" +
//                    " 0                                                          " +
//                    "                 \n" +
//                    " 1                                                            " +
//                    "               \n" +
//                    " 2                                                              " +
//                    "             \n" +
//                    " 3                                                                 " +
//                    "       X  \n" +
//                    " 4                                                              " +
//                    "          X  \n" +
//                    " 5  X                                                              " +
//                    "       |  \n" +
//                    " 6  |                                                             " +
//                    "        |  \n" +
//                    " 7  |                                                          " +
//                    "           |  \n" +
//                    " 8  |                                                          " +
//                    "           |  \n" +
//                    " 9                                                              " +
//                    "          |  \n" +
//                    "10                                                                " +
//                    "        |  \n" +
//                    "11                                                                 " +
//                    "       |  \n",
//            mm.stringView());
//  }
//
//  // to test the method initializeEditor when there are chords added to the editor (notes played
//  // at the same time but have different tones);
//  @Test
//  public void testChords() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.SIX), 0, 6);
//    Note n1 = new Note(new Tone(Pitch.B, Octave.SIX), 1, 2);
//    Note n2 = new Note(new Tone(Pitch.B, Octave.SIX), 2, 6);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    assertEquals("  A6  A♯6   B6 \n" +
//                    "0  X            \n" +
//                    "1  |         X  \n" +
//                    "2  |         X  \n" +
//                    "3  |         |  \n" +
//                    "4  |         |  \n" +
//                    "5  |         |  \n" +
//                    "6            |  \n" +
//                    "7            |  \n",
//            mm.stringView());
//  }
//
//  // to test the method initializeEditor when the method is called twice on the same Music Model
//  // object
//  @Test
//  public void initializeTwice() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.B, Octave.SIX), 3, 4);
//    notes.add(n);
//    mm.initializeEditor(notes);
//    assertEquals("  B6 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3  X  \n" +
//                    "4  |  \n" +
//                    "5  |  \n" +
//                    "6  |  \n",
//            mm.stringView());
//
//    List<Note> notes1 = new ArrayList<Note>();
//    Note n1 = new Note(new Tone(Pitch.C, Octave.SIX), 3, 4);
//    notes1.add(n1);
//    mm.initializeEditor(notes1);
//    assertEquals("  C6 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3  X  \n" +
//                    "4  |  \n" +
//                    "5  |  \n" +
//                    "6  |  \n",
//            mm.stringView());
//  }
//
//  // to test initialize editor when an empty list is passed into it
//  @Test
//  public void testInitializeEmptyEditor() {
//    List<Note> notes = new ArrayList<Note>();
//    mm.initializeEditor(notes);
//    assertEquals("Add Notes to Start Editing Music!",
//            mm.stringView());
//  }
//
//  // to test the method addNote if this note is already within the current range of
//  // the editor and its beats are within the current range
//  @Test
//  public void testAddNoteWithInRange() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    mm.addNote(new Note(new Tone(Pitch.G, Octave.FOUR), 2, 2));
//    assertEquals("  D4  D♯4   E4   F4  F♯4   G4  G♯4   A4 \n" +
//                    "0                                        \n" +
//                    "1       X                                \n" +
//                    "2       |                   X         X  \n" +
//                    "3  X    |                   |         |  \n" +
//                    "4  |    |                                \n" +
//                    "5  |    |                                \n",
//            mm.stringView());
//  }
//
//  // to test the method addNote if the given note's beat is larger than the original
//  // range of beats
//  @Test
//  public void testAddNoteLargerThanRange() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//
//    mm.addNote(new Note(new Tone(Pitch.B, Octave.FOUR), 4, 4));
//    assertEquals("  D4  D♯4   E4   F4  F♯4   G4  G♯4   A4  A♯4   B4 \n" +
//                    "0                                                  \n" +
//                    "1       X                                          \n" +
//                    "2       |                             X            \n" +
//                    "3  X    |                             |            \n" +
//                    "4  |    |                                       X  \n" +
//                    "5  |    |                                       |  \n" +
//                    "6                                               |  \n" +
//                    "7                                               |  \n",
//            mm.stringView());
//
//  }
//
//  // to test the method removeNote when the given Note is in the editor and
//  // its duration is one
//  @Test
//  public void testRemoveNoteOnEditor() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 1);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    assertEquals("  D4  D♯4   E4   F4  F♯4   G4  G♯4   A4 \n" +
//                    "0                                        \n" +
//                    "1       X                                \n" +
//                    "2       |                             X  \n" +
//                    "3  X    |                                \n" +
//                    "4  |    |                                \n" +
//                    "5  |    |                                \n",
//            mm.stringView());
//    mm.removeNote(n);
//    assertEquals("  D4  D♯4 \n" +
//                    "0          \n" +
//                    "1       X  \n" +
//                    "2       |  \n" +
//                    "3  X    |  \n" +
//                    "4  |    |  \n" +
//                    "5  |    |  \n",
//            mm.stringView());
//
//  }
//
//  // to test the method remove if the removed duration is longer than 1
//  @Test
//  public void testRemoveNoteWithDuration() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 1);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    mm.removeNote(new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3));
//    assertEquals(" D♯4   E4   F4  F♯4   G4  G♯4   A4 \n" +
//                    "0                                   \n" +
//                    "1  X                                \n" +
//                    "2  |                             X  \n" +
//                    "3  |                                \n" +
//                    "4  |                                \n" +
//                    "5  |                                \n",
//            mm.stringView());
//  }

  // to test the method removeNote when the editor only has one note
  @Test
  public void testRemoveOne() {
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 8, 4);
    mm.addNote(n);
    assertEquals("    A4 \n" +
                    " 0     \n" +
                    " 1     \n" +
                    " 2     \n" +
                    " 3     \n" +
                    " 4  X  \n" +
                    " 5  |  \n" +
                    " 6  |  \n" +
                    " 7  |  \n" +
                    " 8  |  \n" +
                    " 9  |  \n" +
                    "10  |  \n" +
                    "11  |  \n",
            mm.stringView());
    mm.removeNote(n);
    assertEquals("Add Notes to Start Editing Music!",
            mm.stringView());
  }

  // to test the method removeNote if the Music Model has several Notes
  @Test
  public void testRemoveSeveral() {
    Note n = new Note(new Tone(Pitch.C, Octave.FOUR), 5, 4);
    Note n1 = new Note(new Tone(Pitch.F, Octave.FOUR), 12, 2);
    Note n2 = new Note(new Tone(Pitch.D, Octave.FOUR), 6, 3);
    mm.addNote(n);
    mm.addNote(n1);
    mm.addNote(n2);
    assertEquals("    C4  C#4   D4  D#4   E4   F4 \n" +
                    " 0                              \n" +
                    " 1                              \n" +
                    " 2                           X  \n" +
                    " 3            X              |  \n" +
                    " 4  X         |              |  \n" +
                    " 5  |         |              |  \n" +
                    " 6  |         |              |  \n" +
                    " 7  |         |              |  \n" +
                    " 8  |         |              |  \n" +
                    " 9                           |  \n" +
                    "10                           |  \n" +
                    "11                           |  \n" +
                    "12                           |  \n" +
                    "13                           |  \n",
            mm.stringView());
    mm.removeNote(n);
    assertEquals("    D4  D#4   E4   F4 \n" +
                    " 0                    \n" +
                    " 1                    \n" +
                    " 2                 X  \n" +
                    " 3  X              |  \n" +
                    " 4  |              |  \n" +
                    " 5  |              |  \n" +
                    " 6  |              |  \n" +
                    " 7  |              |  \n" +
                    " 8  |              |  \n" +
                    " 9                 |  \n" +
                    "10                 |  \n" +
                    "11                 |  \n" +
                    "12                 |  \n" +
                    "13                 |  \n",
            mm.stringView());
  }

  // to test when two of the same notes are in the Music Model and then removeNote is called
  @Test
  public void testDuplicatesRemove() {
    Note n = new Note(new Tone(Pitch.A, Octave.EIGHT), 5, 4);
    mm.addNote(n);
    mm.addNote(n);
    assertEquals("   A8 \n" +
                    "0     \n" +
                    "1     \n" +
                    "2     \n" +
                    "3     \n" +
                    "4  X  \n" +
                    "5  |  \n" +
                    "6  |  \n" +
                    "7  |  \n" +
                    "8  |  \n",
            mm.stringView());

    mm.removeNote(n);
    assertEquals("   A8 \n" +
                    "0     \n" +
                    "1     \n" +
                    "2     \n" +
                    "3     \n" +
                    "4  X  \n" +
                    "5  |  \n" +
                    "6  |  \n" +
                    "7  |  \n" +
                    "8  |  \n",
            mm.stringView());

    mm.removeNote(n);
    assertEquals("Add Notes to Start Editing Music!",
            mm.stringView());
  }

//  // to test that an exception is thrown for the method removeNote if
//  // the note is not in the editor
//  @Test(expected = IllegalArgumentException.class)
//  public void testNoteDoesntExist() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 1);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    mm.removeNote(new Note(new Tone(Pitch.B, Octave.FOUR), 2, 1));
//  }
//
//  // to test the exception thrown if the note wished to be removed is in the editor but its
//  // downbeat is wrong
//  @Test(expected = IllegalArgumentException.class)
//  public void testWrongDownBeat() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 1);
//    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
//    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 1, 5);
//    notes.add(n);
//    notes.add(n1);
//    notes.add(n2);
//    mm.initializeEditor(notes);
//    mm.removeNote(new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2));
//  }

  //GenericMusicEditor mmOther = MusicModelCreator.create(); // for use when this editor


//  // to test the method playSimultaneously when each piece of music has only one note
//  @Test
//  public void testCombineTwoOneNoteOnly() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 5, 2);
//    notes.add(n);
//    mm.initializeEditor(notes);
//    assertEquals("  A4 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2     \n" +
//                    "3     \n" +
//                    "4     \n" +
//                    "5  X  \n" +
//                    "6  |  \n",
//            mm.stringView());
//
//    List<Note> notesother = new ArrayList<Note>();
//    Note n1 = new Note(new Tone(Pitch.C, Octave.FIVE), 2, 2);
//    notesother.add(n1);
//    mmOther.initializeEditor(notesother);
//    assertEquals("  C5 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2  X  \n" +
//                    "3  |  \n",
//            mmOther.stringView());
//
//    assertEquals("  A4  A♯4   B4   C5 \n" +
//                    "0                    \n" +
//                    "1                    \n" +
//                    "2                 X  \n" +
//                    "3                 |  \n" +
//                    "4                    \n" +
//                    "5  X                 \n" +
//                    "6  |                 \n",
//            mm.playSimultaneously(mmOther).stringView());
//  }
//
//  // to test the method playSimultaneously when both models have the same music
//  @Test
//  public void testPlaySameMusicSimul() {
//    Note n = new Note(new Tone(Pitch.CSHARP, Octave.NINE), 4, 3);
//    Note n1 = new Note(new Tone(Pitch.CSHARP, Octave.NINE), 2, 3);
//    Note n2 = new Note(new Tone(Pitch.D, Octave.NINE), 3, 4);
//    mm.addNote(n);
//    mm.addNote(n1);
//    mm.addNote(n2);
//    assertEquals(" C♯9   D9 \n" +
//                    "0          \n" +
//                    "1          \n" +
//                    "2  X       \n" +
//                    "3  |    X  \n" +
//                    "4  |    |  \n" +
//                    "5  |    |  \n" +
//                    "6  |    |  \n",
//            mm.stringView());
//
//    mmOther.addNote(n);
//    mmOther.addNote(n1);
//    mmOther.addNote(n2);
//    assertEquals(" C♯9   D9 \n" +
//                    "0          \n" +
//                    "1          \n" +
//                    "2  X       \n" +
//                    "3  |    X  \n" +
//                    "4  |    |  \n" +
//                    "5  |    |  \n" +
//                    "6  |    |  \n",
//            mmOther.stringView());
//    GenericMusicEditor combine = mm.playSimultaneously(mmOther);
//    assertEquals(" C♯9   D9 \n" +
//                    "0          \n" +
//                    "1          \n" +
//                    "2  X       \n" +
//                    "3  |    X  \n" +
//                    "4  |    |  \n" +
//                    "5  |    |  \n" +
//                    "6  |    |  \n",
//            combine.stringView());
//
//    combine.removeNote(n);
//    combine.removeNote(n1);
//    combine.removeNote(n2);
//    assertEquals(" C♯9   D9 \n" +
//                    "0          \n" +
//                    "1          \n" +
//                    "2  X       \n" +
//                    "3  |    X  \n" +
//                    "4  |    |  \n" +
//                    "5  |    |  \n" +
//                    "6  |    |  \n",
//            combine.stringView());
//
//  }
//
//  // to test the method playConsecutively if each piece of music has one note
//  // and they are of the same tone and the other music model's first notes's downbeat starts at 0
//  @Test
//  public void testPlayCosecSameTone() {
//    List<Note> notes = new ArrayList<Note>();
//    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
//    notes.add(n);
//    mm.initializeEditor(notes);
//
//    List<Note> notesother = new ArrayList<Note>();
//    Note n1 = new Note(new Tone(Pitch.A, Octave.FOUR), 0, 2);
//    notesother.add(n1);
//    mmOther.initializeEditor(notesother);
//
//    assertEquals("  A4 \n" +
//                    "0     \n" +
//                    "1     \n" +
//                    "2  X  \n" +
//                    "3  |  \n" +
//                    "4  X  \n" +
//                    "5  |  \n",
//            mm.playConsecutively(mmOther).stringView());
//  }
//
//  // to test the method playConsecutively if two pieces of music with the same notes are to be
//  // played consecutively
//  @Test
//  public void testPlayConsecSameSong() {
//    Note n = new Note(new Tone(Pitch.A, Octave.SIX), 4, 3);
//    Note n1 = new Note(new Tone(Pitch.A, Octave.SIX), 0, 2);
//    Note n2 = new Note(new Tone(Pitch.A, Octave.SIX), 4, 3);
//    mm.addNote(n);
//    mm.addNote(n1);
//    mmOther.addNote(n2);
//    GenericMusicEditor together = mm.playConsecutively(mmOther);
//    assertEquals("   A6 \n" +
//                    " 0  X  \n" +
//                    " 1  |  \n" +
//                    " 2     \n" +
//                    " 3     \n" +
//                    " 4  X  \n" +
//                    " 5  |  \n" +
//                    " 6  |  \n" +
//                    " 7     \n" +
//                    " 8     \n" +
//                    " 9     \n" +
//                    "10     \n" +
//                    "11  X  \n" +
//                    "12  |  \n" +
//                    "13  |  \n",
//            together.stringView());
//  }
//
//  // to test the exception that remove is called before any notes have been added
//  // to this Music Model
//  @Test(expected = IllegalArgumentException.class)
//  public void testRemoveBeforeInit() {
//    mm.removeNote(new Note(new Tone(Pitch.A, Octave.TWO), 0, 3));
//  }
//
//  // to test the exception that playConsecutively is called before any notes have been added
//  // to this Music Model
//  @Test(expected = IllegalArgumentException.class)
//  public void testPlayConsecutivelyNoNotes() {
//    mm.playConsecutively(mmOther);
//  }
//
//  // to test the exception that playConsecutively is called before any notes have been added
//  // to the given Music Model
//  @Test(expected = IllegalArgumentException.class)
//  public void testNoGivenNotes() {
//    mm.addNote(new Note(new Tone(Pitch.A, Octave.TWO), 0, 3));
//    mm.playConsecutively(mmOther);
//  }
//
//  // to test the exception that playSimultaneously is called before any notes have been added to
//  // this Music Model
//  @Test(expected = IllegalArgumentException.class)
//  public void testPlaySimultanNoNotes() {
//    mm.playSimultaneously(mmOther);
//  }
//
//  // to test the exception that playSimultaneously is called before any notes have been added to
//  // the given Music Model
//  @Test(expected = IllegalArgumentException.class)
//  public void testNoGivenNotesSimultan() {
//    mm.addNote(new Note(new Tone(Pitch.A, Octave.TWO), 0, 1));
//    mm.playSimultaneously(mmOther);
//  }
//
//  // to test the exception in the method addNote where the given note is null
//  @Test(expected = IllegalArgumentException.class)
//  public void testAddNoteNull() {
//    mm.addNote(null);
//  }
//
//  // to test the exception in the method addNote where the given Tone is null
//  @Test(expected = IllegalArgumentException.class)
//  public void testAddToneNull() {
//    mm.addNote(new Note(null, 0, 1));
//  }
//
//  // to test the exception in the method removeNote where the given note is null
//  @Test(expected = IllegalArgumentException.class)
//  public void testRemoveNoteNull() {
//    mm.addNote(new Note(new Tone(Pitch.A, Octave.TWO), 0, 1));
//    mm.removeNote(null);
//  }
//
//  // to test the exception in the method removeNote where the given note is null
//  @Test(expected = IllegalArgumentException.class)
//  public void testRemoveToneNull() {
//    mm.addNote(new Note(new Tone(Pitch.A, Octave.TWO), 0, 1));
//    mm.removeNote(new Note(null, 0, 1));
//  }
}

