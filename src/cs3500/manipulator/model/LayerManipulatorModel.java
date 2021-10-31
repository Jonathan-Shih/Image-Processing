package cs3500.manipulator.model;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.processes.ImageProcess;
import java.util.List;

/**
 * A model for an image manipulator that supports layers. It provides functionality to add, set,
 * and delete layers, as well as process specific layers and retrieve specific layers.
 */
public interface LayerManipulatorModel {

  /**
   * Adds the given image as the top most layer.
   * @param img the image to make a new layer from
   * @param name name of the image
   * @throws IllegalArgumentException if provided image is null
   */
  public void addImage(IImage img, String name) throws IllegalArgumentException;

  /**
   * Insert an image as a new layer at a specific location among the layers of the model.
   * @param img the image to make a new layer from
   * @param index the index of the new layer in the set of layers
   * @param name name of the image
   */
  public void insertAtIndex(IImage img, int index, String name) throws IllegalArgumentException;

  /**
   * Set the layer at the given index to be a layer containing the given image. Differs from insert
   * as it will reset any previous layer at the index rather than shifting.
   * @param img the image for the layer to be made from
   * @param index the index of the layer to set
   * @param name name of the image
   */
  public void setLayer(IImage img, int index, String name) throws IllegalArgumentException;

  /**
   * Run the given {@code ImageProcess} on the indicated layer.
   * @param process the ImageProcess to run
   * @param index the index of the layer to be processed
   */
  public void processAtIndex(ImageProcess process, int index) throws IllegalArgumentException;

  /**
   * Retrieve the image located at a specific layer.
   * @param index the location of the image to be retrieved
   * @return the {@code Image} on the layer
   */
  public IImage pullFromIndex(int index) throws IllegalArgumentException;

  /**
   * Remove a specific layer from the layer stack.
   * @param index the location of the layer to be deleted
   */
  public void deleteLayer(int index) throws IllegalArgumentException;

  /**
   * Toggles whether the image at the given layer location is visible or invisible.
   * @param index the location of the layer to be toggled
   */
  public void switchVisibility(int index);

  /**
   * Retrieve the visibility status of the layer at the given index.
   * @param index the location of the layer
   * @return whether the layer is currently visible
   */
  public boolean getVisibilityAt(int index);

  /**
   * Pulls the topmost visible image layer in the stack. If no layer is visible, returns the top
   * layer's image.
   * @return the image at the top visible layer
   */
  public IImage pullTopVisibleImage();

  /**
   * Pulls the index of the topmost visible image layer in the stack. If no layer is visible,
   * returns the top layer's index
   * @return index of the image at the top visible layer
   */
  public int pullTopVisibleImageIndex();

  /**
   * Returns the number of layers in the model.
   * @return number of layers
   */
  public int numLayers();

  /**
   * getter function for the name of the file at the given index.
   * @param index layer index
   * @return name of the file at the given layer index.
   * @throws IllegalArgumentException if index is invalid
   */
  public String getName(int index) throws IllegalArgumentException;

  /**
   * sets the name of the image at the given layer index.
   * @param name new name of the layer
   * @param index layer index
   * @throws IllegalArgumentException if index is invalid
   */
  public void setName(String name, int index) throws IllegalArgumentException;

  /**
   * Getter function for the current list of layer names.
   * @return the current list of layer names
   */
  public List<String> getLayerNames();

  /**
   * Getter function for the current list of layer visibilities.
   * @return the current list of layer visibilities
   */
  public List<Boolean> getVisibilities();

  /**
   * reads the provided file.
   * @param filename name of the file to be read
   * @throws IllegalArgumentException if the file cannotbe found
   */
  public void readFile(String filename) throws IllegalArgumentException;

  /**
   * saves the current layers in a file.
   * @param filename name of the outputted file
   */
  public void saveLayers(String filename);
}
