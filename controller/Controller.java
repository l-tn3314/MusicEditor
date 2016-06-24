package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    Map<Integer, Runnable> clicked = new HashMap<Integer, Runnable>();
    MouseHandler mh = new MouseHandler();
    GuiView gui = (GuiView)view;
    clicked.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        Tone tone = gui.toneAt(mh.returnY());
        int beat = gui.beatAt(mh.returnX());
        List<Note> notes = model.notesAt(beat);
        List<Tone> tones = new ArrayList<Tone>();
        for (Note n : notes) {
          tones.add(n.getTone());
        }
        if (tone != null && beat >= 0 && tones.contains(tone)) {
          Note toRemove = notes.get(tones.indexOf(tone));
          if (gui.removeNotePopUp("Remove " + tone.toString() + " starting on beat " + toRemove.getDownbeat() + "?")) {
            for (Note n : notes) {
              if (n.getTone().equals(tone)) {
                model.removeNote(n);
                view.display(model);
                break;
              }
            }
          }
        }
      }
            });
    mh.setMouseClickedMap(clicked);
    gui.addMouseListener(mh);
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
        runHelp("");
      }
      private void runHelp(String s) {
        String[] ans = gui.addNotePopUp(s);
        if (ans.length < 3) {
          return;
        }
         else if (ans[0] == null || ans[1] == null || ans[2] == null|| ans[3] == null) {
          runHelp("Please fill in all fields");
        } else {
          try {
            Pitch pitch = null;
            Octave octave = null;
            Integer duration = Integer.parseInt(ans[2]);
            Integer beat = Integer.parseInt(ans[3]);

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
            runHelp("Invalid Input!");
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
