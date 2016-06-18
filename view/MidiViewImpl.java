package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;

/**
 * Midi view implementation
 */
public class MidiViewImpl implements MusicEditorView<Note> {
  private final Sequencer sequencer;

  /**
   * Constructs a MidiViewImpl
   */
  public MidiViewImpl() {
    Sequencer seq = null;
    try {
      Synthesizer syn = MidiSystem.getSynthesizer();
      syn.loadAllInstruments(syn.getDefaultSoundbank());
      seq = MidiSystem.getSequencer();
      seq.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.sequencer = seq;
  }

  /**
   * Constructors a MidiViewImpl for testing
   *
   * @param seq mock sequencer to be used
   */
  public MidiViewImpl(Sequencer seq) {
    this.sequencer = seq;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  public void playNote() throws InvalidMidiDataException, InterruptedException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
//    this.receiver.send(start, -1); // timestamp of -1 means responding asap
//    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
//    Thread.sleep(2000);
//    // java util timer or java swing timer to play notes at beats
//    // tempo is how many microseconds per beats
//    this.receiver.close(); // Only call this once you're done playing *all* notes
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

  /**
   * Converts the given tone to a midi pitch
   *
   * @param t Tone to be converted
   * @return midi pitch of given tone
   */
  private int toMidiPitch(Tone t) {
    Octave[] octs = Octave.values();
    Pitch[] pits = Pitch.values();
    return t.getPitch().ordinal() + (t.getOctave().ordinal() * 12) + 24;
  }

  @Override
  public void display(ReadOnlyModel<Note> m) {
    sequencer.setTempoInMPQ(m.getTempo());

    int ticksPerBeat = 10;

    try {
      Sequence s = new Sequence(Sequence.PPQ, ticksPerBeat);
      Track t = s.createTrack();

      Map<Integer, List<Note>> notes = m.allNotes();

      // creates list of unique notes
      List<Note> notesList = new ArrayList<Note>();
      for (Integer i : notes.keySet()) {
        List<Note> tempNotes = notes.get(i);
        for (Note note : tempNotes) {
          if (!notesList.contains(note)) {
            notesList.add(note);
          }
        }
      }

      // adds to the track
      for (Note n : notesList) {
        MidiMessage instr = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0,
                n.getInstrument(), 0);
        MidiEvent ins = new MidiEvent(instr, n.getDownbeat() * ticksPerBeat);

        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0,
                toMidiPitch(n.getTone()), n.getVolume());
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0,
                toMidiPitch(n.getTone()), n.getVolume());

        MidiEvent st = new MidiEvent(start, n.getDownbeat() * ticksPerBeat);
        MidiEvent sp = new MidiEvent(stop, (n.getDownbeat() + n.getDuration()) * ticksPerBeat);

        t.add(ins);
        t.add(st);
        t.add(sp);
      }

      sequencer.setSequence(s);

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.start();
  }

}
