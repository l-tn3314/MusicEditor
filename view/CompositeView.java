package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
import cs3500.music.model.Tone;


public class CompositeView implements GuiView {
  GuiView gui;
  MidiView midi;

  public CompositeView(GuiView gui, MidiView midi){
    this.gui = gui;
    this.midi = midi;
  }

  @Override
  public void resetFocus() {
    gui.resetFocus();
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
  public void addMouseListener(MouseListener listener) {
    gui.addMouseListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {

  }

  @Override
  public void initialize() {
    gui.initialize();
    midi.initialize();
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
    gui.setPaused(isPaused);
  }

  @Override
  public String[] addNotePopUp(String message) {
    gui.setPaused(midi.isPaused());
    return gui.addNotePopUp(message);
  }

  @Override
  public int beatAt(int x) {
    return gui.beatAt(x);
  }

  @Override
  public Tone toneAt(int y) {
    return gui.toneAt(y);
  }

  @Override
  public boolean removeNotePopUp(String removedNote) {
    gui.setPaused(midi.isPaused());
    return gui.removeNotePopUp(removedNote);
  }
}

