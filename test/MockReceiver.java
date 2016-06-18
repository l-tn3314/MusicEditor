package cs3500.music;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Created by Tina on 6/17/2016.
 */
public class MockReceiver implements Receiver {
  StringBuilder stringBuilder;

  public MockReceiver(StringBuilder stringBuilder) {
    this.stringBuilder = stringBuilder;
  }

  @Override
  public void close() {
    //FILLER
  }

  @Override
  public void send(MidiMessage message, long timeStamp){
    //SEND TO A STRINGBUILDER
  }

}
