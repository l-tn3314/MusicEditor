package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * The Mock Receiver purpose is to emulate a Receiver in a Midi Device. It has a string builder to
 * keep track of all the midi messages sent to it for notes to be played
 */
public class MockReceiver implements Receiver {
  private StringBuilder stringBuilder;

  public MockReceiver(StringBuilder stringBuilder) {
    this.stringBuilder = stringBuilder;
  }

  @Override
  public void close() {
    throw new IllegalArgumentException("This method is not supported by the MockReceiver");
  }

  /**
   * To send messages to this Mock Receiver
   *
   * @param message   the midi message that is being sent to this receiver.
   * @param timeStamp when the message is sent. This is not supported by our receiver
   */
  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage midimessage = (ShortMessage) message;
    if (midimessage.getCommand() == ShortMessage.PROGRAM_CHANGE) {
      this.stringBuilder.append("Status: PROGRAM_CHANGE" + ", Instrument: " + midimessage.getData1()
              + ", Channel: " + midimessage.getData2() + "\n");
    } else if (midimessage.getCommand() == ShortMessage.NOTE_ON) {
      this.stringBuilder.append("Status: NOTE ON" + ", Tone: " + midimessage.getData1()
              + ", Volume: " + midimessage.getData2() + "\n");
    } else if (midimessage.getCommand() == ShortMessage.NOTE_OFF) {
      this.stringBuilder.append("Status: Note OFF" + ", Tone: " + midimessage.getData1() +
              ", Volume: " + midimessage.getData2() + "\n");
    }
  }

}
