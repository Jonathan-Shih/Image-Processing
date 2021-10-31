package cs3500.manipulator.image;

import java.awt.Color;

/**
 * Class representing a pixel. Pixels are used to create images and have R/G/B values.
 */
public class Pixel implements IPixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructor for a pixel.
   *
   * @param r Red value
   * @param g Green value
   * @param b Blue value
   */
  public Pixel(int r, int g, int b) {
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public int getBlue() {
    return blue;
  }

  @Override
  public int getRGB() {
    Color cur = new Color(red, green, blue);
    return cur.getRGB();
  }

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue;
  }
}
