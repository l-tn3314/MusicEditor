package cs3500.music.model;

import java.util.List;

/**
 * This is an editor for a general music editor and all of the functionality that can be applied to
 * a piece of music. An implementation of this interface will allow the user to add a song to the
 * editor, add music one note at a time, remove a note, combine two pieces of music to play
 * simultaneous, play two pieces of music consecutively, and access the notes from a piece of
 * music.
 */
public interface GenericMusicEditor {

  /**
   * To initialize the music editor with a song that a user might wish to edit. This song could be
   * either one note or more. This method first initializes the range of pitches in this song and
   * calculates the total duration of the song. The notes of this song are then added to the editor
   * so that the various operations below can be preformed on them.
   *
   * @param song A list of notes or a song that the user wishes to edit
   */
  void initializeEditor(List<Note> song);

  /**
   * Prints a string representation of all the notes and durations in this piece of music. The range
   * of notes printed at the top only represent the range of notes in this piece from the lowest
   * note to the highest note played. The right column represents how this model keeps track of the
   * tempo or beat of the song.
   *
   * @return A string representing the state of the editor and all of the notes and the durations of
   * the notes
   */
  String displayMusic();

  /**
   * Get all of the notes from this piece of Music to be accessed outside of a specific model
   * implementation
   */
  List<Note> getNotes();

  /**
   * To get all the notes at a specified beat
   *
   * @param downbeat the beat in which the user wants to access all the notes from
   * @return a list of notes at the given beat and no notes if there are no notes at the beat
   */
  List<Note> getNotesAtBeat(int downbeat);

  /**
   * to add a note to this piece of music one note at a time
   *
   * @param addition the note which to be added to this piece of music
   * @throws IllegalArgumentException if the given note or its fields are null
   */
  void addNote(Note addition) throws IllegalArgumentException;

  /**
   * to remove a note from this piece of music
   *
   * @param removed the note that the user wishes to be removed
   * @throws IllegalArgumentException if the given note does not exist; if the given note is not of
   *                                  the same tone, downbeat, and/or duration. Users may not remove
   *                                  part of the duration of a note.
   */
  void removeNote(Note removed) throws IllegalArgumentException;

  /**
   * to combine two pieces of music and return a new piece of music that the user may wish to edit
   *
   * @param other the piece of music that a user might wish to combine with this.
   * @return A new Editor that plays this and the other notes simultaneously
   * @throws IllegalArgumentException if this music editor or that music editor has no notes.
   */
  GenericMusicEditor playSimultaneously(GenericMusicEditor other) throws IllegalArgumentException;

  /**
   * to create a new piece of music where the other music is played after this music
   *
   * @param other piece of music the user wishes to play after this one
   * @return A new Editor that plays this music and the other music right after.
   * @throws IllegalArgumentException if this music editor that music editor has no notes
   */
  GenericMusicEditor playConsecutively(GenericMusicEditor other) throws IllegalArgumentException;
}