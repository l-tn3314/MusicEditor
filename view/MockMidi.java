package cs3500.music.view;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

import cs3500.music.view.MockReceiver;


public class MockMidi implements MidiDevice {
  private StringBuilder stringBuilder;
  private Sequencer sequencer;
  private Transmitter transmitter;
  private Receiver mockReceiver;

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

  public Sequencer getSequencer() {
    return sequencer;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {

  }

  @Override
  public void close() {

  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return mockReceiver;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return transmitter;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }
}
