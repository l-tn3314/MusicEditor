package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyModel;

import static cs3500.music.view.GuiViewFrame.SCALE;

/**
 * JPanel that displays the notes stored in a model
 */
class ConcreteGuiViewPanel extends JPanel {
  private ReadOnlyModel<Note> model; // read only model where the gui gets the information from
  private int width; // the width of the panel
  private int height; // the height of the panel

  /**
   * Constructs a ConcreteGuiViewPanel with the given width and height
   *
   * @param width  width of this panel
   * @param height height of this panel
   */
  ConcreteGuiViewPanel(int width, int height) {
    super();
    this.width = width;
    this.height = height;
  }

  /**
   * Sets the model for this panel
   *
   * @param m model to be displayed
   */
  void setModel(ReadOnlyModel<Note> m) {
    this.model = m;
    this.revalidate();
    this.repaint();
  }

  /**
   * Renders a composition of music. Downbeats are rendered in black and their durations
   * if they are greater than one are rendered in cyan. Measure lines are drawn every four beats
   * and a thick bar line is drawn to separate between octaves. Beats are also rendered and the
   * top and pitches rendered to the left.
   *
   * @param g the graphics that are to be painted
   */
  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    g.setColor(new Color(8, 15, 107));

    if (model != null) {
      List<Note> noteRange = model.getRange();
      Map<Integer, List<Note>> notes = model.allNotes();
      int lastBeat = Collections.max(notes.keySet());

      List<String> orderedTones = new ArrayList<String>();

      // To render the pitches
      int counter = 0;
      for (int i = noteRange.size() - 1; i >= 0; i--) {
        g.drawString(noteRange.get(counter).toString(), 0, (((i + 1) * SCALE)
                + (3 * SCALE / 4)));
        counter++;
        orderedTones.add(noteRange.get(i).toString());
      }

      // Notes
      for (int l : notes.keySet()) {
        List<Note> beatNotes = notes.get(l);
        for (Note n : beatNotes) {
          if (n.getDownbeat() == l) {
            g.setColor(Color.BLACK);
          } else {
            g.setColor(Color.CYAN);
          }
          g.fillRect((l + 2) * SCALE, (1 + orderedTones.indexOf(n.toString())) * SCALE,
                  SCALE, SCALE);

        }
      }

      g.setColor(new Color(8, 15, 107));

      // Measure lines
      Graphics2D g2D = (Graphics2D) g;
      g2D.setStroke(new BasicStroke(2));
      for (int j = 0; j <= lastBeat + 1; j += 4) {
        g.drawString(Integer.toString(j), ((j + 2) * SCALE) -
                (2 * Integer.toString(j).length()), (3 * SCALE) / 4);
        g2D.drawLine((j + 2) * SCALE, SCALE,
                (j + 2) * SCALE, (noteRange.size() + 1) * SCALE);
      }
      this.width = (lastBeat + 4) * SCALE;
      this.height = (noteRange.size() + 2) * SCALE;

      // Bar lines
      int original = noteRange.size();
      g2D.drawLine(2 * SCALE, (original + 1) * SCALE,
              (2 + lastBeat + (4 - (lastBeat % 4))) * SCALE, (original + 1) * SCALE);

      for (int k = 0; k < noteRange.size(); k++) {
        if (orderedTones.get(k).toString().contains("B")) {
          g2D.setStroke(new BasicStroke(4));

        } else {
          g2D.setStroke(new BasicStroke(2));

        }
        g2D.drawLine(2 * SCALE, (k + 1) * SCALE,
                (2 + lastBeat + (4 - (lastBeat % 4))) * SCALE, (k + 1) * SCALE);
      }
    }
    setPreferredSize(new Dimension(width, height));
  }
}

