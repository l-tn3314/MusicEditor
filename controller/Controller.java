package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;
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
      ((GuiView) this.view).addActionListener(this);
    }
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
