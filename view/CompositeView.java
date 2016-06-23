package cs3500.music.view;

import java.util.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;

/**
 * Created by Tina on 6/22/2016.
 */
public class CompositeView implements GuiView {
  GuiView gui;
  MidiView midi;

  public CompositeView(GuiView gui, MidiView midi){
    this.gui = gui;
    this.midi = midi;
  }

  @Override
  public void resetFocus() {

  }

  @Override
  public void moveHome() {
    gui.moveHome();
    midi.moveHome();
    gui.setPaused(midi.isPaused());
  }

  @Override
  public void pause() {
    gui.pause();
    midi.pause();
    gui.setPaused(midi.isPaused());
  }

  @Override
  public void updateCurBeat(float i) {
    gui.updateCurBeat(midi.getCurBeat());
  }

  @Override
  public void moveEnd() {
    gui.moveEnd();
    midi.moveEnd();
    gui.setPaused(midi.isPaused());
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    gui.addKeyListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {

  }

  @Override
  public void initialize() {

  }

  @Override
  public void display(ReadOnlyModel<Note> m) {
    TimerTask timerTask = new TimerTask() {
      public void run() {
        updateCurBeat(midi.getCurBeat());
      }
    };
    Timer t = new Timer();
    t.schedule(timerTask, 0, 10);

    gui.display(m);
    midi.display(m);
  }

  @Override
  public void setPaused(boolean isPaused) {

  }
}

