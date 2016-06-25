package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;

/**
 * Midi View implementation that plays back all of the notes in a composition. Musical Instrument
 * Digital Interface (MIDI) is supported by Java's standard library. A sequencer which extends a
 * MidiDevice is used to play back a Midi sequence that contains lists of time-stamped MIDI data.
 */
public class MidiViewImpl implements MidiView {
  private final Sequencer sequencer;
  private float tempo;
  static final int ticksPerBeat = 10;


  public Sequencer getSequencer() {
    return this.sequencer;
  }

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
   * @param sequencer mock sequencer to be used
   */
  public MidiViewImpl(Sequencer sequencer) {
    try {
      Synthesizer syn = MidiSystem.getSynthesizer();
      syn.loadAllInstruments(syn.getDefaultSoundbank());
      sequencer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.sequencer = sequencer;
  }

  @Override
  public void initialize() {
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

  /**
   * To play the notes in this model
   *
   * @param m model whose Notes is to be displayed
   */
  @Override
  public void display(ReadOnlyModel<Note> m) {
    tempo = m.getTempo();
    sequencer.setTempoInMPQ(tempo);

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
    sequencer.setTickPosition(0);
  }
@Override
  public void moveHome() {
    sequencer.setTickPosition(0);
    sequencer.setTempoInMPQ(tempo);
}

  @Override
  public void moveEnd() {
    sequencer.setTickPosition(sequencer.getTickLength());
    sequencer.stop();
    paused = true;
  }

  private boolean paused = true;
  @Override
  public void pause() {
    if (paused) {
      sequencer.start();
    } else {
      sequencer.stop();
    }
    sequencer.setTempoInMPQ(tempo);
    paused = !paused;
  }

  @Override
  public float getCurBeat() {
    return (sequencer.getTickPosition());// / ticksPerBeat);
  }

  @Override
  public boolean isPaused() {
    if (sequencer.getTickPosition() == sequencer.getTickLength()) {
      paused = true;
    }
    return paused;
  }
}
