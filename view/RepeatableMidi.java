package cs3500.music.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.RepeatableReadOnlyModel;

public class RepeatableMidi extends MidiViewImpl {
  Sequencer sequencer;
  Map<Integer, Integer> repeatedBeats; // beat to tick position
  List<Integer> keys;

  public RepeatableMidi() {
    super();
    repeatedBeats = new HashMap<Integer, Integer>();
    sequencer = super.getSequencer();
    keys = new ArrayList<Integer>();
    readMeta = true;
    sequencer.addMetaEventListener(new RepeatHandler(this));
  }

  @Override
  public void display(ReadOnlyModel<Note> m) {
    super.display(m);
    if (m instanceof RepeatableReadOnlyModel) {
      readMeta = true;
      RepeatableReadOnlyModel<Note> model = (RepeatableReadOnlyModel<Note>)m;
      repeatedBeats = model.getRepeatMap();

      sequencer.stop();

      Sequence s = sequencer.getSequence();
      Track t = s.getTracks()[0];
      keys = new ArrayList<Integer>(repeatedBeats.keySet());

      for (Integer k : repeatedBeats.keySet()) {
        repeatedBeats.put(k, repeatedBeats.get(k) * ticksPerBeat);
      }

      for (Integer i : repeatedBeats.keySet()) {
        MetaMessage meta = new MetaMessage();
        if (repeatedBeats.get(i) != i * ticksPerBeat) {
          MidiEvent rep = new MidiEvent(meta, i * ticksPerBeat);
          t.add(rep);
        }
      }

      List<Integer> altStarts = model.getAltEndStarts();
      for (Integer j : altStarts) {
        repeatedBeats.put(j, j * ticksPerBeat + 1);
        MetaMessage meta = null;
          meta = new MetaMessage();
        MidiEvent rep = new MidiEvent(meta, j * ticksPerBeat);
        t.add(rep);
      }

      sequencer.setTickPosition(0);
      sequencer.start();

    }
  }


  @Override
  public void moveHome() {
    super.moveHome();
    keys = new ArrayList<Integer>(repeatedBeats.keySet());
    readMeta = true;
  }

  @Override
  public void moveEnd() {
    super.moveEnd();
    keys = new ArrayList<Integer>();
    readMeta = false;
  }

  private boolean readMeta;
   void updateNextLoop() {
     if (!keys.isEmpty() && readMeta) {
       sequencer.stop();
       sequencer.setTickPosition(repeatedBeats.get(keys.get(0)));
       sequencer.start();
       super.resetTempo();
       keys.remove(0);
     }
     readMeta = !readMeta;
   }

}
