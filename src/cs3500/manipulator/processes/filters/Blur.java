package cs3500.manipulator.processes.filters;

/**
 * A 3 by 3 kernel filter that blurs images.
 */
public class Blur extends ThreeSideKernel {

  /**
   * Create a new Blur filter with the predefined blur matrix.
   */
  public Blur() {
    super(Blur.generate());
  }

  /**
   * Generate a 3 x 3 Blur matrix. Used exclusively by the constructor to make the necessary array
   * and ensure the super constructor is the first call.
   * @return  a 3 by 3 Blur matrix kernel
   */
  private static double [][] generate() {
    double[][] filter = new double[3][3];
    filter[0][0] = 0.0625;
    filter[0][1] = .125;
    filter[0][2] = .0625;
    filter[1][0] = .125;
    filter[1][1] = .25;
    filter[1][2] = .125;
    filter[2][0] = .0625;
    filter[2][1] = .125;
    filter[2][2] = .0625;

    return filter;
  }
}
