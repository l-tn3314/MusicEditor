package cs3500.music.view;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

/**
 * Emulates a midi device for testing purposes. Only get receiver, get transmitter and, get
 * sequencers are overridden to emulate the messages being sent to a receiver. All other methods in
 * a standard MidiDevice are not supported.
 */
public class MockMidi implements MidiDevice {
  private StringBuilder stringBuilder;
  private Sequencer sequencer;
  private Transmitter transmitter;
  private Receiver mockReceiver;

  /**
   * to construct a MockMidi where the mock receiver is set to be the receiver
   *
   * @param stringBuilder the string builder passed to the MockReceiver to check if it has received
   *                      all of the messages sent to it.
   */
  public MockMidi(StringBuilder stringBuilder) {
    this.stringBuilder = stringBuilder;
    Sequencer seq = null;
    try {
      seq = MidiSystem.getSequencer();
      transmitter = seq.getTransmitter();
      transmitter.setReceiver(new MockReceiver(stringBuilder));
      mockReceiver = transmitter.getReceiver();
      seq.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.sequencer = seq;
  }

  /**
   * @return this sequencer
   */
  public Sequencer getSequencer() {
    return sequencer;
  }

  @Override
  public Info getDeviceInfo() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public void open() throws MidiUnavailableException {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public void close() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public boolean isOpen() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public long getMicrosecondPosition() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public int getMaxReceivers() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  @Override
  public int getMaxTransmitters() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  /**
   * @return an object of the mockReceiver class
   */
  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return mockReceiver;
  }

  @Override
  public List<Receiver> getReceivers() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }

  /**
   * @return this transmitter
   */
  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return transmitter;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    throw new IllegalArgumentException("This method is not supported by MockMidi");
  }
}
