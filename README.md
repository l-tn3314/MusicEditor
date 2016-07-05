# Music Editor
Contributors: Sreeya Sai and Tina Lee

We have designed a basic music editor which reads the text files we have provided above and plays the notes using Java’s MIDI Sound System. 

##How to use our editor
1. Run the editor with the `MusicEditorFinal.jar` file above with the first argument as one of the .txt files provided and the second argument as “composite” to run the composite view with the synchronized MIDI and GUI views.
2. As soon as the program starts, press the space bar to start playing the music

User Inputs:
- Space bar: pause / play the music
- Right Arrow Key: scroll right
- Left Arrow Key: scroll left
- Up Arrow Key: scroll up
- Down Arrow Key: scroll down
- Home key (fn + command + left on mac): go to the beginning of the song
- End key (fn + command + right on the mac): go to the end of a composition
- Key ‘A’: add a note. This will prompt a popup dialog where you can add the pitch, octave, downbeat, and duration of the note you wish to add to the song. You can only do this when the music is paused.
- Click on a Note to remove it. This will prompt a popup dialog asking if you want to remove this note. Can only do this when the music is paused.

##MVC Design
Below, we describe the design of each interface and class in our model, view, and controller.

###Model
####Interface: `ReadOnlyModel<N>`

The read only model allows the user to access copies of fields in this class and is passed into the views to keep the views and models separate. This interface has the functionality of allowing the user to get all the Notes in a composition. The user is also allowed to get all the notes at the given beat. Our MusicEditorModel<N> where N is the type of Note the user wishes to use was created so that only a read only abstract model could be passed into the view.

####Interface: `MusicEditorModel<N>`
This interface represents a music editor which is a piece of music and all of the operations that can be done to a composition where the parameter N represents the type of Note. This interface also extends the functionality of the ReadOnlyModel<N> to allow users to access copies of the contents of the model. The functionality of this interface is represented in six methods. A song, or a list of notes, can be added to a composition for editing. This functionality is allowed in the addNotes(Note…). A composition of music can be built one note at a time with the addNote(Note addition) method. You can also remove music from a piece of music with the removeNote(Note removed) method given that the note is already in the music else the remove method with return false since the operation failed. All notes at one beat can also be removed with removeAllNotesAt(int beat). Two compositions of music can also be combined to create a new one that plays both simultaneously, in the method playSimultaneously(GenericMusicEditor other) or consecutively (one after the other), in the method playConsecutively(GenericMusicEditor other). 

****Changes from HW 5****
Lifted methods to the ReadOnlyModel<N> and added a method to add multiple notes at once.

####Class: MusicModel
This class implements the MusicEditorModel<N> and allows a standard Western style music composition that is audible to the human ear to be edited using the functionality of the GenericMusicEditor. A western style music composition represents notes that have 12 pitches as indicated by the Pitch enum, and 10 octaves which are the only octaves that humans are able to hear. This model allows all of the functionality of the MusicEditorModel where N is a standard Western Note. The class invariant of an invalid beats (or total duration of a piece of music) is that this number would be negative because it is an int. This is prevented in the method initializeEditor() and the class Note which has an instance variable downbeat. The Note class makes sure that each beat and duration have to be greater than zero therefore the total duration of a song can’t be negative. All of the methods implemented from the MusicEditorModel<Note> interface also take care of the invariant that no Notes can be added, removed etc. if they are null or not initialized correctly. This is taken care of by throwing an exception.
**CHANGES FROM HW5**
We used a HashMap, Map<Integer, List<Note>> beatsToNotes, where the keys are the beat and the list of notes are the notes at each beat. The Map never has a key that maps to an empty list. 

####Class: Note
This class represents a Note in Western style music which has a tone, duration, and downbeat. All of these fields are private so they cannot be accessed outside of the class. The class invariants of having an invalid beats, tone, or duration field are also addressed in this class. An invalid Tone is not possible and this is managed in the Tone class where its fields are enums. An invalid beat is taken care of in the constructor where an IllegalArgumentException is thrown if a beat is less than zero. An invalid duration is also taken care of in the constructor where the duration can’t be a number less than one.
***CHANGES FROM HW5****
Also added another constructor for a note that takes in an instrument and a volume.

####Class: Tone
This class represents a Tone in music which is constructed with a Pitch which represents all of the distinct pitches in western Music and an Octave which all the 10 octaves a human can hear. The class invariants of an invalid pitch and invalid octave are prevented because Tone has two instance variables (one for pitch and the other for tone) which are enums. A user is not allowed to put in a value that is not one of the given enums or the code won’t even compile. 

####Enum: Pitch
This enum represents all of the 12 distinct pitches in Western Music. The following notes are the only notes supported by this MusicModel "C C♯ D D♯ E F F♯ G G♯ A A♯ B”, which is a composition for Western Music. The private value field of this class is a string representation of all the Pitches for rendering in displayMusic(). 

####Enum: Octave
This enum represents all of the 10 octaves that are able to be heard by the human ear. The private field value is a string representation of this octave for console rendering in the method displayMusic().

###View
####Interface: MusicEditorView<N>
N represents the type of note in the composition. This interface is a general view type that gets data from the model and displays it in a fashion. The functionality allowed by this interface is initialize and display(ReadOnlyModel<N> m) so the views are only able to get limited access to the model. 

####Class: TextViewImpl
implements MusicEditorView for the type Note and creates a console rendering of all of the notes in a composition.

****CHANGE FROM HW6********
Interface: MidiView
To represent a view with sound that has all of the functionality of the MusicEditor
with additional functionality specific to a view that plays sound.

####Class: MidiViewImpl
Implements MusicEditorView and MidiView for the type Note. Has a sequencer which extends a MidiDevice and is used to play back a Midi sequence that contains lists of time-stamped MIDI data. 

####Interface: GuiView
This is a sub interface of the MusicEditorView that contains view that have a visual
or GUI.

####Class: GuiViewFrame
CHANGE FROM HW 6: implements the GuiView for the type Note and extends Frame. 
Creates a Graphical User Interface to render all of the notes in a composition. Has a ConcreteGuiViewPanel (displayPanel) as a field.

####Class: CompositeView
*****ADDITION FROM HW 6******
Implements the GuiView. This view synchronizes a gui and midi view therefore it 
has a GuiView and a MidiViewImpl as instance variables.

####Class: ConcreteGuiViewPanel 
extends Panel and displays all of the notes stored in the model. 

###Controller
####Interface: IController
To represent a Generic Controller where N represents the type of Note. The implementations of this controller will be able to run the program based off of the functionality of the model while asking for user input.

####Class: Controller
To represent a MusicEditorController that has a MusicEditorModel of type Note and a
MusicEditorView of type Note. This controller handles all views, but if it is a guiview, it adds mouse and key events for user input. 

####Class: KeyBoardHandler
An implementation of the keyboard listener. The controller configures a keyboard listener and passes it into the setKeyPressedMap method. If a key is typed, this class will check if it is in its map and then run the method that corresponds to it.

####Class: MouseHandler
An implementation of the mouse listener. The controller configures a mouse listener
and passes it into the setMouseClickedMap method. If the mouse is clicked, the MouseHandler will check if it is in the map and run the method that corresponds to it.

###Util
Class: ViewCreator
Factory class that has a view creator that when given an input creates that type of view. The string “console” creates a text-based view, “visual” creates a guy view, and “midi” creates a midiView and plays back sound.
*****CHANGE FROM HW 6******
added a new case where if the string is “composite”, it creates a new instance of the composite view that has an instance of the gui and midi view.
