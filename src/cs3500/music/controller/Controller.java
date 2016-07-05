package cs3500.music.controller;


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

/**
 * To represent a MusicEditor Controller that has a MusicEditorModel<Note></Note> and a
 * MusicEditorView<Note></Note> and passes information between the model and view, based off
 * of user input.
 * NOTE** This implementation of a controller specifically uses a GuiView and another
 * implementation can be created to work for other types of views.
 */
public class Controller implements IController<Note> {
  private MusicEditorModel<Note> model; // a MusicEditorModel
  private MusicEditorView<Note> view; // A MusicEditorView

  /**
   * Constructs a controller for a MusicEditor.
   *
   * @param m the model of type MusicEditorModel<Note>
   * @param v the view of type MusicEditorView<Note>
   */
  public Controller(MusicEditorModel<Note> m, MusicEditorView<Note> v) {
    this.model = m;
    this.view = v;
    if (view instanceof GuiView) {
      configureKeyBoardListener();
      configureMouseListener();
    }
  }

  // Convenience constructor for testing
  public Controller(MusicEditorModel<Note> m, GuiView<Note> v,
                    KeyboardHandler keypresses, MouseHandler mouseClicks) {
    this.model = m;
    v.addKeyListener(keypresses);
    v.addMouseListener(mouseClicks);
    this.view = v;
  }

  /**
   * Set the configuration of a mouse listener by mapping each all the mouse events supported by
   * this music editor to a function to be performed by either the guiview or the model. Sets the
   * map created to the map in the MouseListener class.
   */
  private void configureMouseListener() {
    Map<Integer, Runnable> clicked = new HashMap<Integer, Runnable>();
    MouseHandler mh = new MouseHandler();
    GuiView gui = (GuiView) view;

    // removes a note from the model when the user clicks on a note and passes this information
    // to the view to display
    clicked.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        Tone tone = gui.toneAt(mh.returnY());
        int beat = gui.beatAt(mh.returnX());
        if (beat < 0) {
          return;
        }
        List<Note> notes = model.notesAt(beat);
        List<Tone> tones = new ArrayList<Tone>();
        for (Note n : notes) {
          tones.add(n.getTone());
        }
        if (tone != null && beat >= 0 && tones.contains(tone)) {
          Note toRemove = notes.get(tones.indexOf(tone));
          if (gui.removeNotePopUp("Remove " + tone.toString() + " starting on beat " +
                  toRemove.getDownbeat() + "?")) {
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

  /**
   * Configures a KeyBoardListener by mapping all of the KeyEvents that are supported by this Music
   * Editor to a runnable function. Splits the events into three maps: keysTyped, keysPressed,
   * keysReleased to represent different user inputs with keys. Sets the map created to the map in
   * the KeyListener class.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    GuiView gui = (GuiView) view;

    keyPresses.put(KeyEvent.VK_HOME, new Runnable() {
      //Runnable function for moving to the beginning
      public void run() {
        gui.moveHome();
      }
    });

    //Runnable function for moving to the end
    keyPresses.put(KeyEvent.VK_END, new Runnable() {
      public void run() {
        gui.moveEnd();
      }
    });

    // Runnable function to pause the composite view
    keyPresses.put(KeyEvent.VK_SPACE, new Runnable() {
      public void run() {
        gui.pause();
      }
    });

    // Runnable function that adds a note to the model based off of user input in the form of
    // a popup dialog by the user
    keyPresses.put(KeyEvent.VK_A, new Runnable() {
      public void run() {
        runHelp("");
      }

      private void runHelp(String s) {
        String[] ans = gui.addNotePopUp(s);
        if (ans.length < 3) {
          return;
        } else if (ans[0] == null || ans[1] == null || ans[2] == null || ans[3] == null) {
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

  /**
   * Draws this view given this model
   */
  @Override
  public void display() {
    view.display(model);
  }

}
