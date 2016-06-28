package cs3500.music.util;


public interface RepeatableCompositionBuilder<T> extends CompositionBuilder<T> {
  /**
   *
   * @param start
   * @param end
   * @return
   */
  CompositionBuilder<T> addRepeat(int start, int end);

  CompositionBuilder<T> addPointToSelf(int beat);
}
