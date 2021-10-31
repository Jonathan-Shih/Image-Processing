package cs3500.manipulator.processes.transformations;

/**
 * A Greyscale implementation of a color transformation. Intended to convert images to monochrome.
 */
public class Greyscale extends ColorTransformation {

  /**
   * Create a new Greyscale color transformation with the predefined Grayscale matrix.
   */
  public Greyscale() {
    super(Greyscale.generate());
  }

  /**
   * Generate a 3 x 3 Grayscale matrix. Used exclusively by the constructor to make the necessary
   * array and ensure the super constructor is the first call.
   *
   * @return a 3 by 3 Grayscale color matrix
   */
  private static double[][] generate() {
    double[][] matrix = new double[3][3];
    matrix[0][0] = 0.2126;
    matrix[1][0] = 0.2126;
    matrix[2][0] = 0.2126;
    matrix[0][1] = 0.7152;
    matrix[1][1] = 0.7152;
    matrix[2][1] = 0.7152;
    matrix[0][2] = 0.0722;
    matrix[1][2] = 0.0722;
    matrix[2][2] = 0.0722;

    return matrix;
  }
}
