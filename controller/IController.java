package cs3500.music.controller;

/**
 * to represent a controller for a music editor where N represents the type of note used. It will
 * run the program based off of the functionality of the model while asking for user input.
 */
public interface IController<N> {
  /**
   * displays the view type given a model.
   */
  void display();
}
