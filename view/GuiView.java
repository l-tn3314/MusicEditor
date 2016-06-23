package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import cs3500.music.model.Note;

public interface GuiView extends MusicEditorView<Note>{

  void resetFocus();

  void moveHome();

  void moveEnd();

  void pause();

  void updateCurBeat(float i);

  void addKeyListener(KeyListener listener);

  void addActionListener(ActionListener listener);

  void setPaused(boolean isPaused);

  String[] openPopUp(String message);
}
