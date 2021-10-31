package cs3500.manipulator.processes.transformations;

import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Pixel;
import cs3500.manipulator.processes.AbstractImageProcess;

/**
 * Represents an abstract color-based transformation of an image. Derived classes implement the
 * specific color modifications to be made to the image.
 */
public abstract class ColorTransformation extends AbstractImageProcess {

  private final double[][] matrix;

  protected ColorTransformation(double[][] matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Null matrices not allowed");
    }
    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Matrix array given is not 3 by 3");
    }
    this.matrix = matrix;
  }

  @Override
  public IPixel calculateValue(IPixel[][] pixels, int x, int y) {

    int red = (int) (pixels[y][x].getRed() * matrix[0][0])
        + (int) (pixels[y][x].getGreen() * matrix[0][1])
        + (int) (pixels[y][x].getBlue() * matrix[0][2]);

    int green = (int) (pixels[y][x].getRed() * matrix[1][0])
        + (int) (pixels[y][x].getGreen() * matrix[1][1])
        + (int) (pixels[y][x].getBlue() * matrix[1][2]);

    int blue = (int) (pixels[y][x].getRed() * matrix[2][0])
        + (int) (pixels[y][x].getGreen() * matrix[2][1])
        + (int) (pixels[y][x].getBlue() * matrix[2][2]);

    return new Pixel(clampValue(red), clampValue(green), clampValue(blue));
  }
}
