package cs3500.music.view;


import java.awt.*;
import java.util.*;
import java.util.List;

import cs3500.music.model.Note;
import cs3500.music.model.RepeatableReadOnlyModel;

import static cs3500.music.view.GuiViewFrame.SCALE;

public class RepeatableGuiViewPanel extends ConcreteGuiViewPanel {

  public RepeatableGuiViewPanel(int width, int height) {
    super(width, height);
  }

  @Override
  protected void drawOther(Graphics g) {
    Graphics2D g2D = (Graphics2D)g;
    List<Note> noteRange = model.getRange();

    if (model instanceof RepeatableReadOnlyModel) {
      Map<Integer, Integer> repeats = ((RepeatableReadOnlyModel<Note>)model).getRepeatMap();
      java.util.List<Integer> keys = new ArrayList<Integer>(repeats.keySet());
      g2D.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
      g2D.setColor(new Color(0, 104, 139));
      for(Integer i: keys) {
        g2D.drawLine((i + 2) * SCALE, SCALE,
                (i + 2) * SCALE, (noteRange.size() + 1) * SCALE);
        Integer start = repeats.get(i);
        g2D.drawLine((start + 2) * SCALE, SCALE,
                (start + 2) * SCALE, (noteRange.size() + 1) * SCALE);
      }
    }

    super.drawOther(g);
  }

}
