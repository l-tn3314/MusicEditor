package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.util.ViewCreator;
import cs3500.music.view.MusicEditorView;

/**
 * Music editor
 */
public class MusicEditor {

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    CompositionBuilder<MusicEditorModel> em = new MusicPiece.Builder();
    MusicEditorModel m = new MusicPiece();
    try {
      // INSERT PATH BELOW
      //m = MusicReader.parseFile(new FileReader("C:\\Users\\Tina\\Documents\\college\\su16\\ood\\intellij\\src\\cs3500\\music\\util\\mystery-1.txt"), em);
      m = MusicReader.parseFile(new FileReader("C:\\Users\\Tina\\Documents\\college\\su16\\ood\\intellij\\src\\cs3500\\music\\util\\" + args[0]), em);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    MusicEditorView<Note> view = ViewCreator.create(args[1]);
    view.initialize();
    view.display(m);
  }
}
