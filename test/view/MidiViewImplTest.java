package cs3500.music.view;

import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;

import static org.junit.Assert.*;

/**
 * Tests the MidiViewImpl class
 */
public class MidiViewImplTest {
  MusicEditorView<Note> midiView;
  StringBuilder stringBuilder;
  Sequencer mockSequencer;

  MusicEditorModel<Note> piece;
  MusicEditorModel<Note> piece2;
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
    this.stringBuilder = new StringBuilder();
    this.mockSequencer = new MockMidi(this.stringBuilder).getSequencer();
    this.midiView = new MidiViewImpl(this.mockSequencer);
    //stringBuilder.append("oh no");

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
  public void testEmptyModel() {
    midiView.display(new MusicPiece());
    assertEquals("",
            stringBuilder.toString());
  }

  @Test
  public void testSingleNote() {
    piece.addNote(e4BeatOne);
    midiView.display(piece);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("", stringBuilder.toString());
  }
}