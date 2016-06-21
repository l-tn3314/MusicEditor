package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import cs3500.music.model.Note;

public interface GuiView extends MusicEditorView<Note>{

  void moveHorizontal(int distance);

  void resetFocus();

  void moveVertical(int distance);

  void addKeyListener(KeyListener listener);

  void addActionListener(ActionListener listener);
}
