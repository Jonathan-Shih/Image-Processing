package cs3500.manipulator.model;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.processes.ImageProcess;

/**
* Provides a simple implementation of the {@code ImageManipulatorModel} with a stored image
* and two constructors, one to load an initial image.
*/
public class SimpleManipulatorModel implements ImageManipulatorModel {

  private IImage img;

  /**
   * Constructs a model with the provided image.
   * @param img the image to be stored initially
   */
  public SimpleManipulatorModel(IImage img) {
    this.img = img;
  }

  /**
   * Constructs a model without an initial image, setting it to null. Expects the image to
   * be added with storeImage() before processing or pulling.
   */
  public SimpleManipulatorModel() {
    this.img = null;
  }

  @Override
  public void storeImage(IImage img) {
    // keep the current image if the provided is null
    if (img != null) {
      this.img = img;
    }
  }

  @Override
  public void processImage(ImageProcess process) {
    if (img == null) {
      throw new IllegalArgumentException("Cannot process a null image");
    }
    IImage processed = process.accept(img);
    img = processed;
  }

  @Override
  public IImage pullImage() {
    if (img == null) {
      throw new IllegalArgumentException("Cannot pull a null image");
    }
    return img;
  }
}
