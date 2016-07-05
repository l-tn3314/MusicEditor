package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.Controller;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewCreator;
import cs3500.music.view.MusicEditorView;

/**
 * The main method that runs the main Music Editor. It takes in two arguments where the first
 * is the filename and the second is the type of view that the user would like to display
 */
public class MusicEditor {

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    CompositionBuilder<MusicEditorModel> em = new MusicPiece.Builder();
    MusicEditorModel<Note> m = new MusicPiece();
    try {
      // INSERT PATH BELOW
      m = MusicReader.parseFile(new FileReader(System.getProperty("user.dir") + "/" +
              args[0]), em);
    } catch (FileNotFoundException e) {
      System.out.println("Invalid File: Txt file must be in the same directory\n" +
              "Empty Composition Started");
    }MusicEditorView<Note> view = new ViewCreator().create(args[1]);
    view.initialize();
    Controller controller = new Controller(m, view);
    controller.display();
  }
}
