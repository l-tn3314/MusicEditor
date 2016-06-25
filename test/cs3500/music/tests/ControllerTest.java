package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
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

public class ControllerTest {
  StringBuilder out; // = new StringBuilder();
  MusicEditorView<Note> view; // = new TextViewImpl(out);
  MusicEditorModel<Note> model; // = new MusicPiece();
  GuiView guiView;
  MidiView midiView;
  IController controller;

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
  }

  // to test display when different notes are played at the same time
  @Test
  public void testMultipleDiffDurations() {
    this.controller = new Controller(model, view);
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

  @Test
  public void test() {
    this.controller = new Controller(model, guiView);
    Note n = new Note(new Tone(Pitch.A, Octave.FOUR), 2, 2);
    Note n1 = new Note(new Tone(Pitch.D, Octave.FOUR), 3, 3);
    Note n2 = new Note(new Tone(Pitch.DSHARP, Octave.FOUR), 5, 1);
    model.addNote(n);
    model.addNote(n1);
    model.addNote(n2);
    controller.display();
  }
}
