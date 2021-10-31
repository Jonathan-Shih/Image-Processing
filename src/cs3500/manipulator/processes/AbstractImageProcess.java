package cs3500.manipulator.processes;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.ImageUtil;
import java.util.Arrays;

/**
 * Abstract class representing an ImageProcess.
 */
public abstract class AbstractImageProcess implements ImageProcess {

  /**
   * Accepts the current image and applies the designated filter.
   *
   * @param img the image to be processed
   * @return filtered image
   * @throws IllegalArgumentException if the given image is null
   */
  public IImage accept(IImage img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    IPixel[][] pixels = Arrays.copyOf(img.getPixels(), img.getHeight());
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = calculateValue(img.getPixels(), j, i);
      }
    }
    return new Image(img.getWidth(), img.getHeight(), ImageUtil.findMax(pixels), pixels);
  }

  /**
   * Clamps the given value so that it will stay within the boundaries. If value is within the
   * boundaries, the original value will be returned.
   *
   * @param value given value
   * @return clamped value
   */
  public int clampValue(int value) {
    int max = 255;
    int min = 0;
    if (value < min) {
      return min;
    }
    if (value > max) {
      return max;
    }
    return value;
  }
}
