import static org.junit.Assert.assertEquals;

import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.Pixel;
import cs3500.manipulator.processes.filters.Sharpen;
import cs3500.manipulator.processes.ImageProcess;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for five-sided filter kernels, namely sharpen.
 */
public class FiveSideKernelTest {

  Pixel[][] test1;

  @Before
  public void init() {
    /*
    Pixel[] pixarr = new Pixel[5];
    Arrays.fill(pixarr, new Pixel(10, 10, 10));
    test1 = new Pixel[5][5];
    Arrays.fill(test1, pixarr);
    test1[2][2] = new Pixel(20, 20, 20);
    */
    test1 = new Pixel[5][5];
    test1[0][0] = new Pixel(10, 10, 10);
    test1[0][1] = new Pixel(10, 10, 10);
    test1[0][2] = new Pixel(10, 10, 10);
    test1[0][3] = new Pixel(10, 10, 10);
    test1[0][4] = new Pixel(10, 10, 10);
    test1[1][0] = new Pixel(10, 10, 10);
    test1[1][1] = new Pixel(10, 10, 10);
    test1[1][2] = new Pixel(10, 10, 10);
    test1[1][3] = new Pixel(10, 10, 10);
    test1[1][4] = new Pixel(10, 10, 10);
    test1[2][0] = new Pixel(10, 10, 10);
    test1[2][1] = new Pixel(10, 10, 10);
    test1[2][2] = new Pixel(20, 20, 20);
    test1[2][3] = new Pixel(10, 10, 10);
    test1[2][4] = new Pixel(10, 10, 10);
    test1[3][0] = new Pixel(10, 10, 10);
    test1[3][1] = new Pixel(10, 10, 10);
    test1[3][2] = new Pixel(10, 10, 10);
    test1[3][3] = new Pixel(10, 10, 10);
    test1[3][4] = new Pixel(10, 10, 10);
    test1[4][0] = new Pixel(10, 10, 10);
    test1[4][1] = new Pixel(10, 10, 10);
    test1[4][2] = new Pixel(10, 10, 10);
    test1[4][3] = new Pixel(10, 10, 10);
    test1[4][4] = new Pixel(10, 10, 10);
  }

  @Test
  public void testCalculateValueSharpen() {
    ImageProcess sharpen = new Sharpen();
    sharpen.calculateValue(test1, 2, 2);
    int testSharpen =
        20 + (int) (10 * 0.25) + (int) (10 * 0.25) + (int) (10 * 0.25) + (int) (10 * 0.25)
            + (int) (10 * 0.25) + (int) (10 * 0.25) + (int) (10 * 0.25) + (int) (10 * 0.25)
            + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125)
            + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125)
            + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125)
            + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125) + (int) (10 * -.125);
    assertEquals(new Pixel(testSharpen, testSharpen, testSharpen).toString(),
        sharpen.calculateValue(test1, 2, 2).toString());
  }

  @Test
  public void sharpenAccept() {
    ImageProcess sharpen = new Sharpen();
    Image image1 = new Image(5, 5, 20, test1);
    assertEquals("5 5 22\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "22 22 22\n"
        + "18 18 18\n"
        + "22 22 22\n"
        + "13 13 13\n"
        + "10 10 10\n"
        + "18 18 18\n"
        + "20 20 20\n"
        + "18 18 18\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "22 22 22\n"
        + "18 18 18\n"
        + "22 22 22\n"
        + "13 13 13\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "10 10 10\n"
        + "13 13 13\n"
        + "10 10 10", sharpen.accept(image1).toString());
  }

  @Test
  public void sharpenAcceptSmallerImg() {
    Pixel[][] imgp = new Pixel[3][4];
    imgp[0][0] = new Pixel(118, 118, 118);
    imgp[0][1] = new Pixel(142, 142, 142);
    imgp[0][2] = new Pixel(149, 149, 149);
    imgp[0][3] = new Pixel(106, 106, 106);
    imgp[1][0] = new Pixel(138, 138, 138);
    imgp[1][1] = new Pixel(182, 182, 182);
    imgp[1][2] = new Pixel(182, 182, 182);
    imgp[1][3] = new Pixel(138, 138, 138);
    imgp[2][0] = new Pixel(108, 108, 108);
    imgp[2][1] = new Pixel(130, 130, 130);
    imgp[2][2] = new Pixel(137, 137, 137);
    imgp[2][3] = new Pixel(93, 93, 93);

    Image original = new Image(4, 3, 182, imgp);

    ImageProcess sharpen = new Sharpen();
    assertEquals("4 3 255\n"
        + "146 146 146\n"
        + "245 245 245\n"
        + "246 246 246\n"
        + "139 139 139\n"
        + "249 249 249\n"
        + "255 255 255\n"
        + "255 255 255\n"
        + "248 248 248\n"
        + "131 131 131\n"
        + "225 225 225\n"
        + "224 224 224\n"
        + "120 120 120", sharpen.accept(original).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageSharpen() {
    ImageProcess sharpen = new Sharpen();
    sharpen.accept(null);
  }

  @Test
  public void clampMinSharpen() {
    ImageProcess sharpen = new Sharpen();
    assertEquals(0, sharpen.clampValue(-5));
  }

  @Test
  public void clampMaxSharpen() {
    ImageProcess sharpen = new Sharpen();
    assertEquals(255, sharpen.clampValue(300));
  }
}