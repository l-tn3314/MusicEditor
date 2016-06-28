package cs3500.music.model;


import java.util.List;
import java.util.Map;

public interface RepeatableReadOnlyModel<N> extends ReadOnlyModel<N> {
  Map<Integer, Integer> getRepeatMap();

  List<Integer> getAltEndStarts();
}
