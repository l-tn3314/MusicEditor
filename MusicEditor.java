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
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

/**
 * The main method that runs the main Music Editor. It takes in two arguments where the
 * first is the filename and the second is the type of view that the user would like to display
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
      e.printStackTrace();
    }

//    MusicEditorView<Note> view = ViewCreator.create(args[1]);
//    view.initialize();
//    view.display(m);
//    Controller controller = new Controller(m, (GuiView)view);
    GuiViewFrame frame = new GuiViewFrame();
    //frame.initialize();
    MidiViewImpl midi = new MidiViewImpl();
    //midi.initialize();
    GuiView gui = new CompositeView(frame, midi);
    gui.initialize();
    Controller controller = new Controller(m, gui);
    controller.display();

  }
}
