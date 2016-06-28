package cs3500.music.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequencer;

public class RepeatHandler implements MetaEventListener {
  Sequencer sequencer;
  Map<Integer, Integer> repeats;
  List<Integer> keys;
  RepeatableMidi midi;

  public RepeatHandler(RepeatableMidi m) {
    this.midi = m;
  }

  public RepeatHandler(Sequencer s, Map<Integer, Integer> repeats) {
    this.sequencer = s;
    this.repeats = repeats;
    this.keys = new ArrayList<Integer>(repeats.keySet());
    Collections.sort(this.keys);
  }

  @Override
  public void meta(MetaMessage meta) {
    midi.updateNextLoop();
  }
}
