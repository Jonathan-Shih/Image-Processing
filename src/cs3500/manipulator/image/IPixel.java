package cs3500.manipulator.image;

/**
 * Interface for representing a pixel. Pixels have a red, green and blue channel.
 */
public interface IPixel {

  /**
   * Getter method for the value in the red channel.
   *
   * @return Value of the red channel
   */
  public int getRed();

  /**
   * Getter method for the value in the green channel.
   *
   * @return Value of the green channel
   */
  public int getGreen();

  /**
   * Getter method for the value in the blue channel.
   *
   * @return Value of the blue channel
   */
  public int getBlue();

  /**
   * Getter method for the RGB value of this pixel.
   *
   * @return RGB value
   */
  public int getRGB();
}
