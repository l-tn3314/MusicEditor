package cs3500.music;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tone;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.TextViewImpl;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicEditorView t = new TextViewImpl();
    MusicEditorModel piece;
    Note e4BeatOne;
    Note d4BeatThree;
    Note c4BeatFive;
    Note d4BeatSeven;
    Note e4BeatNine;
    Note e4BeatEleven;
    Note e4BeatThirteen;

    Note g3BeatOne;
    Note g3BeatNine;

    Tone e4 = new Tone
            (Pitch.E, Octave.FOUR);
    Tone d4 = new Tone
            (Pitch.D, Octave.FOUR);
    Tone g3 = new Tone
            (Pitch.G, Octave.THREE);

    piece = new MusicPiece();
    e4BeatOne = new Note(e4, 2, 1);
    d4BeatThree = new Note(d4, 2, 3);
    c4BeatFive = new Note(new Tone
            (Pitch.C, Octave.FOUR), 2, 5);
    d4BeatSeven = new Note(d4, 2, 7);
    e4BeatNine = new Note(e4, 2, 9);
    e4BeatEleven = new Note(e4, 2, 11);
    e4BeatThirteen = new Note(e4, 2, 13);
    g3BeatOne = new Note(g3, 7, 1);
    g3BeatNine = new Note(g3, 7, 9);
    piece.addNote(e4BeatOne);
    piece.addNote(d4BeatThree);
    piece.addNote(c4BeatFive);
    piece.addNote(d4BeatSeven);
    piece.addNote(e4BeatNine);
    piece.addNote(e4BeatEleven);
    piece.addNote(e4BeatThirteen);
    piece.addNote(g3BeatOne);
    piece.addNote(g3BeatNine);

    //t.display(piece);
    MusicEditorView view = new GuiViewFrame();
    MusicEditorView midiView = new MidiViewImpl();
    view.initialize();
    view.display(piece);
    // You probably need to connect these views to your model, too...
  }
}
