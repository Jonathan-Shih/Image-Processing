package cs3500.manipulator.processes.filters;

import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Pixel;

/**
 * An abstract representation of any 3 by 3 kernel filtering operation.
 */
public abstract class ThreeSideKernel extends ImageFilter {

  /**
   * Constructor for a three-sided filter kernel. Passes the filter on to {@code ImageFilter}.
   *
   * @param filter the 3x3 2D array filter
   */
  public ThreeSideKernel(double[][] filter) {
    super(filter);

    if (filter.length != 3 || filter[0].length != 3) {
      throw new IllegalArgumentException("Filter array given is not 3 by 3");
    }
  }

  @Override
  public IPixel calculateValue(IPixel[][] pixels, int x, int y) {

    int rValue = addValues("red", pixels, x, y, 3);
    int gValue = addValues("green", pixels, x, y, 3);
    int bValue = addValues("blue", pixels, x, y, 3);

    return new Pixel(rValue, gValue, bValue);
  }

}
