package cs3500.manipulator.processes.filters;

/**
 * A sharpening filter extension of the five-sided filter. Intended to make images sharper.
 */
public class Sharpen extends FiveSideKernel {

  /**
   * Create a new Sharpen filter with the predefined blur matrix.
   */
  public Sharpen() {
    super(Sharpen.generate());
  }

  /**
   * Generate a 5 x 5 Sharpen matrix. Used exclusively by the constructor to make the necessary
   * array and ensure the super constructor is the first call.
   * @return  a 5 by 5 Sharpen matrix kernel
   */
  private static double [][] generate() {
    double[][] filter = new double[5][5];

    filter[0][0] = -.125;
    filter[0][1] = -.125;
    filter[0][2] = -.125;
    filter[0][3] = -.125;
    filter[0][4] = -.125;

    filter[1][0] = -.125;
    filter[1][1] = .25;
    filter[1][2] = .25;
    filter[1][3] = .25;
    filter[1][4] = -.125;

    filter[2][0] = -.125;
    filter[2][1] = .25;
    filter[2][2] = 1;
    filter[2][3] = .25;
    filter[2][4] = -.125;

    filter [3][0] = -.125;
    filter [3][1] = .25;
    filter [3][2] = .25;
    filter [3][3] = .25;
    filter [3][4] = -.125;

    filter [4][0] = -.125;
    filter [4][1] = -.125;
    filter [4][2] = -.125;
    filter [4][3] = -.125;
    filter [4][4] = -.125;

    return filter;
  }
}
