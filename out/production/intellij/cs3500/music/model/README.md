/*******************************************************/
                        PITCHES
 The twelve distince pitches are represented by an enum.

/*******************************************************/


/*******************************************************/
                         NOTES
 A Note has a pitch and an octave. The default octave
 is 4. A Note's Pitch or octave can be changed through 
 setter methods (and is visible with getter methods).

/*******************************************************/


/*******************************************************/
                      PLAYEDNOTES
 A PlayedNote has a Note, a duration(which is positive),
 and a beat(which is positive because beats are counted
 from one). Similar to a Note, a PlayedNote's fields
 can be changed or viewed with setter and getter 
 methods, respectively. 
 - The difference between a Note and a PlayedNote is 
   that a PlayedNote represents the Note actually being
   played for a certain amount of time starting on a 
   certain beat.
/*******************************************************/


/*******************************************************/
                    MUSICEDITORMODEL
 A MusicEditorModel guarantees the following 
 functionalities:
 - Adding a Note
 - Removing a Note
 - Removing all Notes on a given beat
 - Returning a List of all Notes on a given beat
 - Returning a Map of all the notes in the model
   (keys are Integers, which represent a beat and
    values are Lists of PlayedNote, which represent the
    notes that should be played on that beat)
 - Adding a given model's notes with this model's 
   notes
 - Adding a given model's notes with this model's 
   notes such that the notes of the given model come 
   after the notes of this model
 - Returning a String representation of this model

/*******************************************************/


/*******************************************************/
                      MUSICPIECE
 A MusicPiece is an implementation of the 
 MusicEditorModel. A MusicPiece has a beatsInMeasure, 
 a bpm(beats per minute), and a Map of its notes. The
 default beatsInMeasure is 4, and the default bpm 
 is 120. The Map never has a key that maps to an empty
 list.

/*******************************************************/
