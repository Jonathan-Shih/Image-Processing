package cs3500.manipulator.processes.filters;

import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Pixel;
import cs3500.manipulator.processes.AbstractImageProcess;

/**
 * Represents an abstract kernel filtering operation on an image. Derived classes will implement
 * specific kernels for specific operations.
 */
public abstract class ImageFilter extends AbstractImageProcess {

  private final double[][] filter;

  protected ImageFilter(double[][] filter) {
    this.filter = filter;

    if (filter == null) {
      throw new IllegalArgumentException("Null filters not allowed");
    }
  }

  /**
   * Computes the new value of the designated pixel of the desired channel.
   *
   * @param channel "red", "green" or "blue" channel
   * @param pixels  the pixels of the given image
   * @param x       x index of the designated pixel
   * @param y       y index of the designated pixel
   * @param kerSize size of the filter kernel
   * @return new value of the desired channel of the designated pixel
   */
  public int addValues(String channel, IPixel[][] pixels, int x, int y, int kerSize) {
    int curValue = 0;
    int addedValue = 0;
    int midPoint = (kerSize - 1) / 2;
    IPixel curPixel;
    for (int i = 0; i < kerSize; i++) {
      int yOffset = i - midPoint;
      for (int j = 0; j < kerSize; j++) {
        int xOffset = j - midPoint;
        try {
          curPixel = pixels[y + yOffset][x + xOffset];
        } catch (ArrayIndexOutOfBoundsException obe) {
          curPixel = new Pixel(0, 0, 0);
        }
        switch (channel) {
          case "red":
            addedValue = curPixel.getRed();
            break;
          case "green":
            addedValue = curPixel.getGreen();
            break;
          case "blue":
            addedValue = curPixel.getBlue();
            break;
          default:
            throw new IllegalArgumentException("Channel invalid");
        }
        curValue += (int) (filter[i][j] * addedValue);
      }
    }
    return clampValue(curValue);
  }

}
