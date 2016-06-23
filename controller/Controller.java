package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;
import cs3500.music.view.GuiView;
import cs3500.music.view.MusicEditorView;

public class Controller implements ActionListener {
  private MusicEditorModel<Note> model;
  private MusicEditorView<Note> view;

  public Controller(MusicEditorModel m, GuiView v) {
    this.model = m;
    this.view = v;
    if (view instanceof GuiView ) {
      configureKeyBoardListener();
      configureMouseListener();
      ((GuiView) this.view).addActionListener(this);
    }
  }

  private void configureMouseListener() {
    MouseHandler mh = new MouseHandler();
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    GuiView gui = (GuiView)view;

    keyPresses.put(KeyEvent.VK_HOME, new Runnable() {
      public void run() {
        //Runnable function for scrolling right
        gui.moveHome();
      }
    });

    keyPresses.put(KeyEvent.VK_END, new Runnable() {
      public void run() {
        //Runnable function for scrolling right
        gui.moveEnd();
      }
    });

    keyPresses.put(KeyEvent.VK_SPACE, new Runnable() {
      public void run() {
        gui.pause();
      }
    });

    keyPresses.put(KeyEvent.VK_A, new Runnable() {
      public void run () {
        String[] ans = gui.openPopUp(" ");
        //FIX THIS
        if (ans[0] != null || ans[1] != null || ans[2] != null|| ans[3] != null) {
          Pitch pitch = null;
          Octave octave = null;
          Integer duration = Integer.parseInt(ans[2]);
          Integer beat = Integer.parseInt(ans[3]);
          try {
            for (int i = 0; i < Pitch.values().length; i++) {
              if (Pitch.values()[i].toString().equals(ans[0])) {
                pitch = Pitch.values()[i];
              }
            }
            for (int j = 0; j < Octave.values().length; j++) {
              if (Octave.values()[j].toString().equals(ans[1])) {
                octave = Octave.values()[j];
              }
            }
            model.addNote(new Note(new Tone(pitch, octave), duration, beat));
            view.display(model);
          } catch (Exception e) {
            System.out.println("WHOOPS");
          }
        }
      }
            });

    KeyboardHandler kbh = new KeyboardHandler();
    kbh.setKeyPressedMap(keyPresses);
    kbh.setKeyReleasedMap(keyReleases);
    kbh.setKeyTypedMap(keyTypes);

    gui.addKeyListener(kbh);
  }

  public void display() {
    view.display(model);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ((GuiView)view).resetFocus();
  }
}
