import static org.junit.Assert.assertEquals;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.image.Pixel;
import cs3500.manipulator.processes.filters.Blur;
import cs3500.manipulator.processes.ImageProcess;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for all cases of a ThreeSideKernel. Current implementation tests for abstract functionality
 * and for the functionality of Blur.
 */
public class ThreeSideKernelTest {

  Pixel[][] test1;

  @Before
  public void init() {
    test1 = new Pixel[3][3];
    test1[0][0] = new Pixel(10, 10, 10);
    test1[0][1] = new Pixel(15, 15, 15);
    test1[0][2] = new Pixel(10, 10, 10);
    test1[1][0] = new Pixel(10, 10, 10);
    test1[1][1] = new Pixel(20, 20, 20);
    test1[1][2] = new Pixel(10, 10, 10);
    test1[2][0] = new Pixel(10, 10, 10);
    test1[2][1] = new Pixel(10, 10, 10);
    test1[2][2] = new Pixel(10, 10, 10);
  }

  @Test
  public void testBlur() {
    ImageProcess blur = new Blur();
    /*
    filter[0][0] = 0.0625;
    filter[0][1] = .125;
    filter[0][2] = .0625;
    filter[1][0] = .125;
    filter[1][1] = .25;
    filter[1][2] = .125;
    filter[2][0] = .0625;
    filter[2][1] = .125;
    filter[2][2] = .0625;
     */
    int testBlur =
        (int) (10 * 0.0625) + (int) (10 * .125) + (int) (10 * .0625) + (int) (15 * .125) + (int) (20
            * .25) + (int) (10 * 0.0625) + (int) (10 * .125) + (int) (10 * .0625) + (int) (10
            * .125);
    assertEquals(new Pixel(testBlur, testBlur, testBlur).toString(),
        blur.calculateValue(test1, 1, 1).toString());
  }

  @Test
  public void testBlurOutOfBounds() {
    ImageProcess blur = new Blur();
    int testBlur =
        (int) (10 * .125) + (int) (15 * .25) + (int) (10 * 0.0625) + (int) (20 * .125) + (int) (10
            * .0625) + (int) (10 * 0.0625);
    assertEquals(new Pixel(testBlur, testBlur, testBlur).toString(),
        blur.calculateValue(test1, 0, 1).toString());
  }


  @Test
  public void acceptBlur() {
    ImageProcess blur = new Blur();

    // create has been changed
    IImage checkerBoard = ImageUtil.create(1, 4, 3);

    assertEquals("4 3 208\n"
        + "118 118 118\n"
        + "156 156 156\n"
        + "156 156 156\n"
        + "116 116 116\n"
        + "156 156 156\n"
        + "208 208 208\n"
        + "208 208 208\n"
        + "156 156 156\n"
        + "118 118 118\n"
        + "156 156 156\n"
        + "156 156 156\n"
        + "116 116 116", blur.accept(checkerBoard).toString());
  }

  @Test
  public void acceptBlurSmallerImg() {
    ImageProcess blur = new Blur();

    // create has been changed
    IImage checkerBoard = ImageUtil.create(1, 2, 2);
    Pixel[][] blurred = new Pixel[2][2];
    blurred[0][0] = new Pixel(118, 118, 118);
    blurred[0][1] = new Pixel(116, 116, 116);
    blurred[1][0] = new Pixel(116, 116, 116);
    blurred[1][1] = new Pixel(118, 118, 118);

    Image blurredImg = new Image(2, 2, 118, blurred);

    assertEquals(blurredImg.toString(), blur.accept(checkerBoard).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageBlur() {
    ImageProcess blur = new Blur();
    blur.accept(null);
  }

  @Test
  public void clampMinBlur() {
    ImageProcess blur = new Blur();
    assertEquals(0, blur.clampValue(-5));
  }

  @Test
  public void clampMaxBlur() {
    ImageProcess blur = new Blur();
    assertEquals(255, blur.clampValue(300));
  }
}