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

public class Controller implements ActionListener {
  private MusicEditorModel<Note> model;
  private GuiView view;

  public Controller(MusicEditorModel m, GuiView v) {
    this.model = m;
    this.view = v;
    configureKeyBoardListener();
    this.view.addActionListener(this);
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_HOME, new Runnable() {
      public void run() {
        //Runnable function for scrolling right
        view.moveHome();
      }
    });

    keyPresses.put(KeyEvent.VK_END, new Runnable() {
      public void run() {
        //Runnable function for scrolling right
        view.moveEnd();
      }
    });

    KeyboardHandler kbh = new KeyboardHandler();
    kbh.setKeyPressedMap(keyPresses);
    kbh.setKeyReleasedMap(keyReleases);
    kbh.setKeyTypedMap(keyTypes);

    view.addKeyListener(kbh);
  }

  public void display() {
    view.display(model);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    view.resetFocus();
  }
}
