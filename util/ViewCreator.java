package cs3500.music.util;

import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorView;
import cs3500.music.view.TextViewImpl;

/**
 * Factory that creates views
 */
public class ViewCreator {

  /**
   * Creates a View based on the given String ("console", "visual", "midi")
   *
   * @param type of View to be created
   * @return created View
   */
  public static MusicEditorView create(String type) {
    switch (type) {
      case "console":
        return new TextViewImpl();

      case "visual":
        return new GuiViewFrame();

      case "midi":
        return new MidiViewImpl();
      default:
        throw new IllegalArgumentException("This view type does not exist");
    }
  }
}
