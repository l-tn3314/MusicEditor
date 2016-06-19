package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.TextViewImpl;

import static org.junit.Assert.assertEquals;

// to implement all the public methods in the TextViewImpl
public class TextViewImplTest {
  StringBuilder out; // = new StringBuilder();
  MusicEditorView<Note> view; // = new TextViewImpl(out);
  MusicEditorModel<Note> model; // = new MusicPiece();

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
    this.out = new StringBuilder();
    this.view = new TextViewImpl(out);
    this.model = new MusicPiece();

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

  // to test display when no notes have been added
  @Test
  public void testEmpty() {
    view.display(model);
    assertEquals("Add Notes to Start Editing Music!", out.toString());
  }

  // to test display when one note has been added
  @Test
  public void testView() {
    Note n = new Note(new Tone(Pitch.A, Octave.TEN), 3, 0);
    model.addNote(n);
    view.display(model);
    assertEquals("  A10 \n" +
                    "0  X  \n" +
                    "1  |  \n" +
                    "2  |  \n",
            out.toString());
  }

  // to test display when different notes are played at the same time
  @Test
  public void testMultipleDiffDurations() {
    List<Note> notes = new ArrayList<Note>();
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 5, 1);
    model.addNote(n);
    model.addNote(n1);
    model.addNote(n2);
    view.display(model);
    assertEquals("   D4  D#4   E4   F4  F#4   G4  G#4   A4 \n" +
                    "0                                        \n" +
                    "1       X                                \n" +
                    "2       |                             X  \n" +
                    "3  X    |                             |  \n" +
                    "4  |    |                                \n" +
                    "5  |    |                                \n",
            out.toString());
  }

  // to test when a set of notes are being played at the same time
  @Test
  public void testChords() {
    List<Note> notes = new ArrayList<Note>();
    Note n = new Note(new Tone(Pitch.A, Octave.SIX), 6, 0);
    Note n1 = new Note(new Tone(Pitch.B, Octave.SIX), 2, 1);
    Note n2 = new Note(new Tone(Pitch.B, Octave.SIX), 6, 2);
    notes.add(n);
    notes.add(n1);
    notes.add(n2);
    model.addNote(n);
    model.addNote(n1);
    model.addNote(n2);
    view.display(model);
    assertEquals("   A6  A#6   B6 \n" +
                    "0  X            \n" +
                    "1  |         X  \n" +
                    "2  |         X  \n" +
                    "3  |         |  \n" +
                    "4  |         |  \n" +
                    "5  |         |  \n" +
                    "6            |  \n" +
                    "7            |  \n",
            out.toString());
  }

  // to test display when notes have no duration
  @Test
  public void testOneDuration() {
    Note n = new Note(new Tone(Pitch.C, Octave.EIGHT), 1, 0);
    Note n1 = new Note(new Tone(Pitch.F, Octave.EIGHT), 1, 4);
    Note n2 = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 1, 3);
    Note n3 = new Note(new Tone(Pitch.B, Octave.EIGHT), 1, 2);

    model.addNote(n);
    model.addNote(n1);
    model.addNote(n2);
    model.addNote(n3);

    view.display(model);
    assertEquals("   C8  C#8   D8  D#8   E8   F8  F#8   G8  G#8   A8  A#8   B8 \n" +
                    "0  X                                                         \n" +
                    "1                                                            \n" +
                    "2                                                         X  \n" +
                    "3                                                    X       \n" +
                    "4                           X                                \n",
            out.toString());
  }

//       to test the method display is padding the beats corresponding
//             to the length of the longest beat in the song when the longest beat is
//             2 characters long (between 10 - 99)
    @Test
    public void testPaddingTwoChar() {
      Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 4, 10);
      model.addNote(n);
      view.display(model);
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
              out.toString());
    }

    // to test the method display when given a list of only one note
    // with a duration of one
    @Test
    public void testOneNoteDurationOne() {
      Note n = new Note(new Tone(Pitch.ASHARP, Octave.EIGHT), 1, 0);
      model.addNote(n);
      view.display(model);
      assertEquals("  A#8 \n" +
                      "0  X  \n",
              out.toString());
    }

    // to test the method display for one note and the duration is greater than one
    @Test
    public void testOneNoteDurationBiggerThanOne() {
      Note n = new Note(new Tone(Pitch.C, Octave.FOUR), 3, 4);
      model.addNote(n);
      view.display(model);
      assertEquals("   C4 \n" +
                      "0     \n" +
                      "1     \n" +
                      "2     \n" +
                      "3     \n" +
                      "4  X  \n" +
                      "5  |  \n" +
                      "6  |  \n",
              out.toString());
    }

    // to test display when two notes in the given list are
    // the same tone, have the same downbeat, and have the same duration
    @Test
    public void testOverlapSameDuration() {
      Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
      Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
      model.addNote(n);
      model.addNote(n1);
      view.display(model);
      assertEquals("   A7 \n" +
                      "0     \n" +
                      "1     \n" +
                      "2     \n" +
                      "3     \n" +
                      "4  X  \n" +
                      "5  |  \n" +
                      "6  |  \n" +
                      "7  |  \n",
              out.toString());
    }

    // to test display when two notes have the same tone and the same
    // downbeat but one of the durations is shorter
    @Test
    public void testOverlayDifferentDuration() {
      Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
      Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 2, 4);
      model.addNote(n);
      model.addNote(n1);
      view.display(model);
      assertEquals("   A7 \n" +
                      "0     \n" +
                      "1     \n" +
                      "2     \n" +
                      "3     \n" +
                      "4  X  \n" +
                      "5  |  \n" +
                      "6  |  \n" +
                      "7  |  \n",
              out.toString());
    }

    // to test display when two notes have the same tone and their downbeats
    // are right after each other and their durations overlap
    @Test
    public void testConsecutiveDownbeat() {
      Note n = new Note(new Tone(Pitch.A, Octave.SEVEN), 4, 4);
      Note n1 = new Note(new Tone(Pitch.A, Octave.SEVEN), 2, 5);
      model.addNote(n);
      model.addNote(n1);
      view.display(model);
      assertEquals("   A7 \n" +
                      "0     \n" +
                      "1     \n" +
                      "2     \n" +
                      "3     \n" +
                      "4  X  \n" +
                      "5  X  \n" +
                      "6  |  \n" +
                      "7  |  \n",
              out.toString());
    }

    // to test display when two notes have the same tone and their downbeats
    // are not right after eachother but their durations overlap.
    @Test
    public void testNotConsecutiveDownbeat() {
      Note n = new Note(new Tone(Pitch.A, Octave.TWO), 4, 4);
      Note n1 = new Note(new Tone(Pitch.A, Octave.TWO), 2, 6);
      model.addNote(n);
      model.addNote(n1);
      view.display(model);
      assertEquals("   A2 \n" +
                      "0     \n" +
                      "1     \n" +
                      "2     \n" +
                      "3     \n" +
                      "4  X  \n" +
                      "5  |  \n" +
                      "6  X  \n" +
                      "7  |  \n",
              out.toString());
    }

    // to test the method display when multiple notes are played one
    // after another
    @Test
    public void testRightAfterEachOther() {
      Note n = new Note(new Tone(Pitch.C, Octave.FOUR), 2, 0);
      Note n1 = new Note(new Tone(Pitch.C, Octave.FOUR), 4, 2);
      Note n2 = new Note(new Tone(Pitch.CSHARP, Octave.FOUR), 3, 0);
      Note n3 = new Note(new Tone(Pitch.CSHARP, Octave.FOUR), 2, 3);
      model.addNote(n);
      model.addNote(n1);
      model.addNote(n2);
      model.addNote(n3);
      view.display(model);
      assertEquals("   C4  C#4 \n" +
                      "0  X    X  \n" +
                      "1  |    |  \n" +
                      "2  X    |  \n" +
                      "3  |    X  \n" +
                      "4  |    |  \n" +
                      "5  |       \n",
              out.toString());
    }


  // to render a few notes of Mary has a little Lamb
    @Test
  public void testStringView() {
      model.addNote(e4BeatOne);
    model.addNote(d4BeatThree);
    model.addNote(c4BeatFive);
    model.addNote(d4BeatSeven);
    model.addNote(e4BeatNine);
    model.addNote(e4BeatEleven);
    model.addNote(e4BeatThirteen);
    model.addNote(g3BeatOne);
    model.addNote(g3BeatNine);
      view.display(model);
    assertEquals("    G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 \n" +
                    " 0                                                  \n" +
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
                    "15  |                                               \n",
            out.toString());

  }

}
