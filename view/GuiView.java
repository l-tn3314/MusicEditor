package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;
import cs3500.music.model.Tone;

public interface GuiView extends MusicEditorView<Note>{

  void resetFocus();

  void moveHome();

  void moveEnd();

  void pause();

  void updateCurBeat(float i);

  void addKeyListener(KeyListener listener);

  void addMouseListener(MouseListener listener);

  void addActionListener(ActionListener listener);

  void setPaused(boolean isPaused);

  String[] addNotePopUp(String message);

  int beatAt(int x);

  Tone toneAt(int y);

  boolean removeNotePopUp(String removedNote);
}
