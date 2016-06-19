package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * A mock receiver
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
    byte[] data = message.getMessage();
    stringBuilder.append("\n" + new String(data) + " " + timeStamp);
  }

}
