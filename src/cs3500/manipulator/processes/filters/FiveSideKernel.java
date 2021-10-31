package cs3500.manipulator.processes.filters;

import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Pixel;

/**
 * Abstract class representing a five sided kernel, which is used to apply certain filters on
 * images.
 */
public abstract class FiveSideKernel extends ImageFilter {

  /**
   * Constructor for a five sided kernel filter.
   *
   * @param filter 5x5 2D array filter matrix
   * @throws IllegalArgumentException if filter is null or has invalid dimentions
   */
  public FiveSideKernel(double[][] filter) throws IllegalArgumentException {
    super(filter);

    if (filter.length != 5 || filter[0].length != 5) {
      throw new IllegalArgumentException("Filter array given is not 5 by 5");
    }
  }

  @Override
  public Pixel calculateValue(IPixel[][] pixels, int x, int y) {

    int rValue = addValues("red", pixels, x, y, 5);
    int gValue = addValues("green", pixels, x, y, 5);
    int bValue = addValues("blue", pixels, x, y, 5);

    return new Pixel(rValue, gValue, bValue);
  }
}
