package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.TextViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * To test that the controller takes in user inputs from the gui to manipulate the model
 */
public class ControllerTest {
  StringBuilder out; // = new StringBuilder();
  MusicEditorView<Note> view; // = new TextViewImpl(out);
  MusicEditorModel<Note> model; // = new MusicPiece();
  GuiView guiView;
  MidiView midiView;
  IController controller;
  KeyboardHandler kh;
  MouseHandler mh;
  IController mockController;

  @Before
  public void init() {
    this.out = new StringBuilder();
    this.view = new TextViewImpl(out);
    this.midiView = new MidiViewImpl();
    GuiView gui = new GuiViewFrame();
    this.guiView = new CompositeView(gui, midiView);
    guiView.initialize();
    this.model = new MusicPiece();
    this.controller = new Controller(model, view);
    kh = new KeyboardHandler();
    mh = new MouseHandler();
    this.mockController = new Controller(model, guiView, kh, mh);


  }

  // to test display when different notes are played at the same time
  @Test
  public void testMultipleDiffDurations() {
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 5, 1);
    model.addNote(n);
    model.addNote(n1);
    model.addNote(n2);
    controller.display();
    assertEquals("   D4  D#4   E4   F4  F#4   G4  G#4   A4 \n" +
                    "0                                        \n" +
                    "1       X                                \n" +
                    "2       |                             X  \n" +
                    "3  X    |                             |  \n" +
                    "4  |    |                                \n" +
                    "5  |    |                                \n",
            out.toString());
  }

  // to test that the model is manipulated to add a note when the 'A' key is pressed
  @Test
  public void testAddNote() {
    Map<Integer, Runnable> testmap = new HashMap<Integer, Runnable>();
    testmap.put(KeyEvent.VK_A, new Runnable() {
      public void run() {
        runHelp("");
      }

      private void runHelp(String s) {
        model.addNote(new Note(new Tone(Pitch.B, Octave.FOUR), 4, 2));
      }
    });
    kh.setKeyPressedMap(testmap);
    KeyEvent event = new KeyEvent(new JFrame(), 1, 0, 0, KeyEvent.VK_A, 'A');
    kh.keyPressed(event);
    List<Note> notes = new ArrayList<Note>();
    notes.add(new Note(new Tone(Pitch.B, Octave.FOUR), 4, 2));
    assertEquals(notes,
            model.notesAt(2));
  }

  // to test that the model is manipulated to remove a note when the mouse is clicked
  @Test
  public void testRemoveNote() {
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 5, 1);
    model.addNote(n1);
    model.addNote(n);
    model.addNote(n2);
    Map<Integer, Runnable> testMouse = new HashMap<Integer, Runnable>();
    testMouse.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        model.removeNote(n);
      }
    });
    mh.setMouseClickedMap(testMouse);
    MouseEvent event = new MouseEvent(new JFrame(),
            MouseEvent.MOUSE_CLICKED, 1, 0, 10, 10, 1, true);
    mh.mouseClicked(event);
    List<Note> notes = new ArrayList<Note>();
    notes.add(n2);
    assertEquals(notes,
            model.notesAt(2));
  }

}
