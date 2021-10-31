package cs3500.manipulator.image;

/**
 * Interface representing an Image.
 */
public interface IImage {

  /**
   * Getter method for the width of this image.
   *
   * @return width
   */
  public int getWidth();

  /**
   * Getter method for the Height of this image.
   *
   * @return height
   */
  public int getHeight();

  /**
   * Getter method for the max value of this image.
   *
   * @return maxValue
   */
  public int getMaxValue();

  /**
   * Getter method for the pixels in this image.
   *
   * @return copy of 2D array of pixels
   */
  public IPixel[][] getPixels();
}
