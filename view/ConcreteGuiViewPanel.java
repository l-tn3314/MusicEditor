package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;

/**
 * A dummy view that simply draws a string 
 */
public class ConcreteGuiViewPanel extends JPanel {
  private MusicEditorModel model;

  ConcreteGuiViewPanel() {
    // Filler
  }

  void setModel(MusicEditorModel m) {
    this.model = m;
    this.revalidate();
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);


    // Measure lines

    g.setColor(new Color(8, 15, 107));

    if (model != null) {
      List<Tone> toneRange = model.getRange();
      Map<Integer, List<Note>> notes = model.allNotes();
      int lastBeat = Collections.max(notes.keySet());

      List<String> orderedTones = new ArrayList<String>();

      // To render the pitches
      int counter = 0;
      for (int i = toneRange.size() - 1; i >= 0; i--) {
        g.drawString(toneRange.get(counter).toString(), 0, (((i + 1) * GuiViewFrame.SCALE)
                + (3 * GuiViewFrame.SCALE / 4)));
        counter++;
        orderedTones.add(toneRange.get(i).toString());
      }


      // Notes
      for(int l : notes.keySet()) {
        List<Note> beatNotes = notes.get(l);
        for(Note n: beatNotes) {
          if(n.getDownbeat() == l) {
            g.setColor(Color.BLACK);
            }
          else {
            g.setColor(Color.CYAN);
          }
          g.fillRect((l + 2) * GuiViewFrame.SCALE, (1 + orderedTones.indexOf(n.toString())) * GuiViewFrame.SCALE,
                  GuiViewFrame.SCALE, GuiViewFrame.SCALE);

        }
      }

      g.setColor(new Color(8, 15, 107));

      // Measure lines
      Graphics2D g2D = (Graphics2D)g;
      g2D.setStroke(new BasicStroke(2));
      for (int j = 0; j <= lastBeat + 1; j += 4) { // CHANGE THIS LATER OKMANG
        g2D.drawLine((j + 2) * GuiViewFrame.SCALE, GuiViewFrame.SCALE,
                (j + 2) * GuiViewFrame.SCALE, (toneRange.size() + 1) * GuiViewFrame.SCALE);
      }

      // Bar lines
      int original = toneRange.size();
      g2D.drawLine(2 * GuiViewFrame.SCALE, (original + 1) * GuiViewFrame.SCALE,
              (2 + lastBeat + (4 - (lastBeat % 4))) * GuiViewFrame.SCALE, (original + 1) * GuiViewFrame.SCALE);



      for(int k = 0; k < toneRange.size(); k++) {
          if (orderedTones.get(k).toString().contains("B")) {
            g2D.setStroke(new BasicStroke(4));

          } else {
            g2D.setStroke(new BasicStroke(2));

          }
          g2D.drawLine(2 * GuiViewFrame.SCALE, (k + 1) * GuiViewFrame.SCALE,
                  (2 + lastBeat + (4 - (lastBeat % 4))) * GuiViewFrame.SCALE, (k + 1) * GuiViewFrame.SCALE);
        }
      }
    }
  }

