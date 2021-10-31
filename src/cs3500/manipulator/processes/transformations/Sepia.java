package cs3500.manipulator.processes.transformations;

/**
 * A Sepia implementation of a color transformation. Intended to convert images to a light-brown
 * monochromatic image.
 */
public class Sepia extends ColorTransformation {

  /**
   * Create a new Sepia color transformation with the predefined Sepia matrix.
   */
  public Sepia() {
    super(Sepia.generate());
  }

  /**
   * Generate a 3 x 3 Sepia matrix. Used exclusively by the constructor to make the necessary
   * array and ensure the super constructor is the first call.
   *
   * @return a 3 by 3 Sepia color matrix
   */
  private static double[][] generate() {
    double[][] matrix = new double[3][3];
    matrix[0][0] = 0.393;
    matrix[0][1] = 0.769;
    matrix[0][2] = 0.189;
    matrix[1][0] = 0.349;
    matrix[1][1] = 0.686;
    matrix[1][2] = 0.168;
    matrix[2][0] = 0.272;
    matrix[2][1] = 0.534;
    matrix[2][2] = 0.131;

    return matrix;
  }
}
