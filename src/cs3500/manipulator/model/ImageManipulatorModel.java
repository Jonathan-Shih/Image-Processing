package cs3500.manipulator.model;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.processes.ImageProcess;

/**
 * The set of operations required for all image manipulators. An image manipulator is able to store
 * an image, take in {@code ImageProcess} objects and process the image with them, and return the
 * image once the processes have been done.
 */
public interface ImageManipulatorModel {

  /**
   * Stores the given image to the model.
   * @param img the image to be stored
   */
  public void storeImage(IImage img);

  /**
   * Processes the image stored with the given {@code ImageProcess}.
   * @param process the {@code ImageProcess} to process with
   */
  public void processImage(ImageProcess process);

  /**
   * Pulls the current image from the model.
   * @return the current image
   */
  public IImage pullImage();
}
