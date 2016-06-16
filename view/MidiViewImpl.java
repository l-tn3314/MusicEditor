package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.*;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements MusicEditorView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final Sequencer sequencer;
  private final Transmitter seqTrans;

  public MidiViewImpl() {
    Synthesizer s = null;
    Receiver r = null;
    Sequencer seq = null;
    Transmitter t = null;
    try {
      seq = MidiSystem.getSequencer();
      t = seq.getTransmitter();
      s = MidiSystem.getSynthesizer();
      r = s.getReceiver();
      t.setReceiver(r);
      s.open();
      seq.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.synth = s;
    this.receiver = r;
    this.sequencer = seq;
    this.seqTrans = t;
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote() throws InvalidMidiDataException, InterruptedException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1); // timestamp of -1 means responding asap
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    Thread.sleep(2000);
    // java util timer or java swing timer to play notes at beats
    // tempo is how many microseconds per beats
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void initialize() {
    try {
      this.playNote();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

//  private Tone toTone(int pitch) {
//    Octave[] octs = Octave.values();
//    Pitch[] pits = Pitch.values();
//    return new Tone(pits[pitch % 12], octs[((pitch - 24) / 12)]);
//  }

  private int toMidiPitch(Tone t) {
    Octave[] octs = Octave.values();
    Pitch[] pits = Pitch.values();
    return t.getPitch().ordinal() + (t.getOctave().ordinal() * 12) + 24;
  }

  @Override
  public void display(MusicEditorModel m) {
    sequencer.setTempoInMPQ(m.getTempo());
    //Sequence s = null;

    int ticksPerBeat = 200000;

    try {
      Sequence s = new Sequence(Sequence.PPQ, ticksPerBeat);
      Track t = s.createTrack();

      Map<Integer, List<Note>> notes = m.allNotes();

      List<Note> notesList = new ArrayList<Note>();

      for (Integer i : notes.keySet()) {
        List<Note> tempNotes = notes.get(i);
        for (Note note : tempNotes) {
          if (!notesList.contains(note)) {
            notesList.add(note);
          }
        }
      }
        //List<Note> notesList = notes.get(i);
        for (Note n : notesList) {
          MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0,
                  toMidiPitch(n.getTone()), n.getVolume());
          MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0,
                  toMidiPitch(n.getTone()), n.getVolume());
          t.add(new MidiEvent(start, n.getDownbeat() * ticksPerBeat));
          t.add(new MidiEvent(stop, (n.getDownbeat() + n.getDuration()) * ticksPerBeat));
        }
      //}

      sequencer.setSequence(s);

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.start();
    //sequencer.close();
    //receiver.close();

//    try {
//      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
//      MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
//      this.receiver.send(start, -1); // timestamp of -1 means responding asap
//      this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
//
//      Thread.sleep(2000);
//      // java util timer or java swing timer to play notes at beats
//      // tempo is how many microseconds per beats
//      this.receiver.close(); // Only call this once you're done playing *all* notes
//    } catch (InvalidMidiDataException e) {
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }
}
