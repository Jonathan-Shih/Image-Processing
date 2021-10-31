package cs3500.manipulator.processes;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.IPixel;

/**
 * A generic interface representing a process to be performed on an image. Every image process must
 * support the following operations.
 */
public interface ImageProcess {

  /**
   * Accept an image and apply this process to it, returning the new image.
   *
   * @param img the image to be processed
   * @return the newly processed image
   */
  public IImage accept(IImage img);

  /**
   * Calculates the value of the given pixel, relative to the image.
   *
   * @param pixels 2D array of pixels an image
   * @param x      x index of the targeted pixel
   * @param y      y index of the targeted pixel
   * @return updated Pixel
   */
  public IPixel calculateValue(IPixel[][] pixels, int x, int y);

  /**
   * Clamps the given value so that it will stay within the boundaries. If value is within the
   * boundaries, the original value will be returned.
   *
   * @param value given value
   * @return clamped value
   */
  public int clampValue(int value);
}
