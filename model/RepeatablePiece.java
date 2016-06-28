package cs3500.music.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.RepeatableCompositionBuilder;

public class RepeatablePiece extends MusicPiece implements RepeatableReadOnlyModel<Note> {
  private LinkedHashMap<Integer, Integer> endtoBeginning;
  private List<Integer> altEndStarts;

  public RepeatablePiece() {
    super();
    endtoBeginning = new LinkedHashMap<Integer, Integer>();
    altEndStarts = new ArrayList<Integer>();
  }

  public RepeatablePiece(float tempo) {
    super(tempo);
    endtoBeginning = new LinkedHashMap<Integer, Integer>();
    altEndStarts = new ArrayList<Integer>();
  }

  public RepeatablePiece(float tempo, LinkedHashMap<Integer, Integer> map) {
    super(tempo);
    endtoBeginning = map;
    altEndStarts = new ArrayList<Integer>();
  }

  public void addAltEndStart(int altStart) {
    this.altEndStarts.add(altStart);
  }

  @Override
  public List<Integer> getAltEndStarts() {
    return this.altEndStarts;
  }

  /**
   * Returns a copy of the endtoBeginning map
   */
  @Override
  public Map<Integer, Integer> getRepeatMap() {
    return new LinkedHashMap<Integer, Integer>(endtoBeginning);
  }

  public static CompositionBuilder<MusicEditorModel> builder() {
    return new RepeatablePiece.Builder();
  }


  public static final class Builder implements RepeatableCompositionBuilder<MusicEditorModel> {
    float tempo;
    List<Note> notes;
    LinkedHashMap<Integer, Integer> map;
    List<Integer> altStarts;

    /**
     * Constructs a Builder with a default tempo of 200000 microseconds per beat
     */
    public Builder() {
      tempo = 200000;
      notes = new LinkedList<Note>();
      map = new LinkedHashMap<Integer, Integer>();
      altStarts = new ArrayList<Integer>();
    }

    @Override
    public MusicEditorModel build() {
      RepeatablePiece m = new RepeatablePiece(tempo, map);
      for (Note n : notes) {
        m.addNote(n);
      }
      for (Integer i : altStarts) {
        m.addAltEndStart(i);
      }
      return m;
    }

    /**
     * updates the tempo to the given
     *
     * @param tempo The speed, in microseconds per beat
     * @return a new compositionBuilder with an updated tempo
     */
    @Override
    public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    /**
     * Converts the given midi pitch to a Tone
     *
     * @param pitch midi pitch to be converted
     * @return Tone of the given midi pitch
     */
    private Tone toTone(int pitch) {
      Octave[] octs = Octave.values();
      Pitch[] pits = Pitch.values();
      return new Tone(pits[pitch % 12], octs[((pitch - 24) / 12)]);
    }

    /**
     * Adds a note from the given midi text files to this piece of composition
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
     *                   piano)
     * @param volume     The volume (in the range [0, 127])
     * @return a new CompositionBuilder with a new note added to it with the given parameters
     */
    @Override
    public CompositionBuilder<MusicEditorModel> addNote(int start, int end, int instrument,
                                                        int pitch, int volume) {
      notes.add(new Note(toTone(pitch), end - start, start, instrument, volume));
      return this;
    }

    @Override
    public CompositionBuilder<MusicEditorModel> addRepeat(int start, int end) {
      map.put(end, start);
      return this;
    }

    @Override
    public CompositionBuilder<MusicEditorModel> addPointToSelf(int beat) {
      map.put(beat, beat);
      altStarts.add(beat);
      return this;
    }
  }

}
