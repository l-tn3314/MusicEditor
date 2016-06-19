package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MockMidi;
import cs3500.music.view.MusicEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MidiViewImpl class
 */
public class MidiViewImplTest {
  MusicEditorView<Note> midiView;
  StringBuilder stringBuilder;
  Sequencer mockSequencer;
  MockMidi mockMidi;
  Receiver mockReceiver;

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
    this.mockMidi = new MockMidi(stringBuilder);
    this.mockSequencer = mockMidi.getSequencer();
    try {
      this.mockReceiver = mockMidi.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.midiView = new MidiViewImpl(this.mockSequencer);


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

   // to test when no notes are added to this piece of music that there are no messages sent
  @Test
  public void testEmptyModel() {
    midiView.display(new MusicPiece());
    assertEquals("",
            stringBuilder.toString());
  }

  // to test when sending a single note to the mockreceiver
  @Test
  public void testSingleNote() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 64, 100), 1);
    assertEquals("Status: NOTE ON, Tone: 64, Volume: 100\n", stringBuilder.toString());
  }

  // to test when a message that a note is to be turned off or there should be a change in
  // instrument is sent
  @Test
  public void testMessageChanges() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_OFF, 0, 49, 90), 1);
    this.mockReceiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, 3), 1);
    assertEquals("Status: Note OFF, Tone: 49, Volume: 90\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 0, Channel: 0\n",
            stringBuilder.toString());
  }

  // to test when adding a single note to the composition allows a message to be sent, therefore
  // playing the song
  @Test
  public void testPlaySingle() {
    piece.addNote(e4BeatOne);
    midiView.display(piece);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
            "Status: NOTE ON, Tone: 64, Volume: 70\n" +
            "Status: Note OFF, Tone: 64, Volume: 70\n" +
            "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n", stringBuilder.toString());
  }


  // to test that the mock receiver gets messages from several notes that do not overlap
  @Test
  public void SendMultipleNotes() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 40, 35), 1);
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 35, 60), 1);
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_OFF, 0, 40, 35), 1);
    assertEquals("Status: NOTE ON, Tone: 40, Volume: 35\n" +
                    "Status: NOTE ON, Tone: 35, Volume: 60\n" +
                    "Status: Note OFF, Tone: 40, Volume: 35\n",
            stringBuilder.toString());
  }

  // to test that when adding multiple notes that don't overlap, sends a message for the notes
  // to be played
  @Test
  public void testPlayMultipleNotes() {
    piece.addNote(e4BeatOne);
    piece.addNote(g3BeatNine);
    midiView.display(piece);
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 64, Volume: 70\n" +
                    "Status: Note OFF, Tone: 64, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 55, Volume: 70\n" +
                    "Status: Note OFF, Tone: 55, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n",
            stringBuilder.toString());
  }

  // to test that when adding multiple notes that have the same downbeat, sends a message for
  // the notes to be played
  @Test
  public void testOneChord() throws InterruptedException {
    Note n = new Note(new Tone(Pitch.C, Octave.FIVE), 1, 0);
    Note n1 = new Note(new Tone(Pitch.A, Octave.TWO), 1, 0);
    piece.addNote(n);
    piece.addNote(n1);
    midiView.display(piece);
    Thread.sleep(2000);
    assertEquals("Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 72, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 45, Volume: 70\n" +
                    "Status: Note OFF, Tone: 72, Volume: 70\n" +
                    "Status: Note OFF, Tone: 45, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n",
            stringBuilder.toString());
  }

  // to test when two notes with different downbeat but overlapping duration have been added and
  // that the messages are sent for the notes to be played
  @Test
  public void testOverlappingDurations() throws InterruptedException {
    Note n = new Note(new Tone(Pitch.ASHARP, Octave.FIVE), 4, 0);
    Note n1 = new Note(new Tone(Pitch.A, Octave.FIVE), 1, 1);
    piece.addNote(n);
    piece.addNote(n1);
    midiView.display(piece);
    Thread.sleep(2000);
    assertEquals("Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 82, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 81, Volume: 70\n" +
                    "Status: Note OFF, Tone: 81, Volume: 70\n" +
                    "Status: Note OFF, Tone: 82, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n",
            stringBuilder.toString());
  }

  // to test that when adding two notes with a huge range between them, sends a message for the
  // note to be played
  @Test
  public void testHugeRange() throws InterruptedException {
    Note n = new Note(new Tone(Pitch.C, Octave.ONE), 1, 0);
    Note n1 = new Note(new Tone(Pitch.B, Octave.EIGHT), 1, 1);
    piece.addNote(n1);
    piece.addNote(n);
    midiView.display(piece);
    Thread.sleep(2000);
    assertEquals("Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 24, Volume: 70\n" +
                    "Status: Note OFF, Tone: 24, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n" +
                    "Status: NOTE ON, Tone: 119, Volume: 70\n" +
                    "Status: Note OFF, Tone: 119, Volume: 70\n" +
                    "Status: PROGRAM_CHANGE, Instrument: 1, Channel: 0\n",
            stringBuilder.toString());
  }

  // to test the exception where an invalid status is given for the midi message
  @Test(expected = InvalidMidiDataException.class)
  public void testInvalidStatus() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(4, 0, 40, 35), 1);
  }

  // to test the exception where the Tone is out of range because its bigger than the max
  // number of octaves supported by midi
  @Test(expected = InvalidMidiDataException.class)
  public void testInvalidTone() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 128, 35), 1);
  }

  // to test the exception where the Tone in the message is negative
  @Test(expected = InvalidMidiDataException.class)
  public void testInvalidNegativeTone() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, -1, 35), 1);
  }

  // to test the exception where the volume is out of range because is bigger than max
  @Test(expected = InvalidMidiDataException.class)
  public void testTooBigDuration() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 30, 140), 1);
  }

  // to test the exception wehre the volume is negative
  @Test(expected = InvalidMidiDataException.class)
  public void testNegativeDuration() throws InvalidMidiDataException {
    this.mockReceiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 90, -1), 1);
  }

}