import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.Pixel;
import cs3500.manipulator.processes.transformations.Greyscale;
import cs3500.manipulator.processes.transformations.Sepia;
import cs3500.manipulator.processes.ImageProcess;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for greyscale and sepia color transformations.
 */
public class ColorTransformationTest {

  Pixel[][] arr1;
  Image image1;

  @Before
  public void initValues() {
    arr1 = new Pixel[2][2];
    arr1[0][0] = new Pixel(255, 255, 255);
    arr1[0][1] = new Pixel(255, 255, 255);
    arr1[1][0] = new Pixel(255, 255, 255);
    arr1[1][1] = new Pixel(255, 255, 255);
    image1 = new Image(2, 2, 255, arr1);
  }

  @Test
  public void greyScaleCalcValue() {
    Pixel[][] test1;
    test1 = new Pixel[3][3];
    test1[0][0] = new Pixel(1, 7, 10);
    test1[0][1] = new Pixel(2, 62, 15);
    test1[0][2] = new Pixel(5, 3, 10);
    test1[1][0] = new Pixel(3, 5, 6);
    test1[1][1] = new Pixel(7, 6, 20);
    test1[1][2] = new Pixel(2, 1, 10);
    test1[2][0] = new Pixel(13, 3, 2);
    test1[2][1] = new Pixel(4, 4, 3);
    test1[2][2] = new Pixel(2, 6, 1);
    ImageProcess greyScale = new Greyscale();
    int color = (int) (7 * 0.2126) + (int) (6 * 0.7152) + (int) (20 * 0.0722);
    assertEquals(new Pixel(color, color, color).toString(),
        greyScale.calculateValue(test1, 1, 1).toString());
  }

  @Test
  public void greyScaleCalcValueClampMax() {
    Pixel[][] test1;
    test1 = new Pixel[3][3];
    test1[0][0] = new Pixel(1, 7, 10);
    test1[0][1] = new Pixel(2, 62, 15);
    test1[0][2] = new Pixel(5, 3, 10);
    test1[1][0] = new Pixel(3, 5, 6);
    test1[1][1] = new Pixel(255, 255, 255);
    test1[1][2] = new Pixel(2, 1, 10);
    test1[2][0] = new Pixel(13, 3, 2);
    test1[2][1] = new Pixel(4, 4, 3);
    test1[2][2] = new Pixel(2, 6, 1);
    ImageProcess greyScale = new Greyscale();
    int color = (int) (255 * 0.2126) + (int) (255 * 0.7152) + (int) (255 * 0.0722);
    if (color > 255) {
      assertEquals(255, greyScale.calculateValue(test1, 1, 1).getRed());
    } else {
      assertEquals(color, greyScale.calculateValue(test1, 1, 1).getRed());
    }
  }

  @Test
  public void greyscaleAccept() {
    ImageProcess greyscale = new Greyscale();
    assertEquals("2 2 254\n"
        + "254 254 254\n"
        + "254 254 254\n"
        + "254 254 254\n"
        + "254 254 254", greyscale.accept(image1).toString());
  }


  @Test
  public void sepiaCalculateValues() {
    Pixel[][] test1;
    test1 = new Pixel[3][3];
    test1[0][0] = new Pixel(1, 7, 10);
    test1[0][1] = new Pixel(2, 62, 15);
    test1[0][2] = new Pixel(5, 3, 10);
    test1[1][0] = new Pixel(3, 5, 6);
    test1[1][1] = new Pixel(7, 6, 20);
    test1[1][2] = new Pixel(2, 1, 10);
    test1[2][0] = new Pixel(13, 3, 2);
    test1[2][1] = new Pixel(4, 4, 3);
    test1[2][2] = new Pixel(2, 6, 1);

    int red = (int) (7 * 0.393) + (int) (6 * 0.769) + (int) (20 * 0.189);
    int green = (int) (7 * 0.349) + (int) (6 * 0.686) + (int) (20 * 0.168);
    int blue = (int) (7 * 0.272) + (int) (6 * 0.534) + (int) (20 * 0.131);
    ImageProcess sepia = new Sepia();
    assertEquals(new Pixel(red, green, blue).toString(),
        sepia.calculateValue(test1, 1, 1).toString());

  }

  @Test
  public void sepiaClampMax() {
    Pixel[][] test1;
    test1 = new Pixel[3][3];
    test1[0][0] = new Pixel(1, 7, 10);
    test1[0][1] = new Pixel(2, 62, 15);
    test1[0][2] = new Pixel(5, 3, 10);
    test1[1][0] = new Pixel(3, 5, 6);
    test1[1][1] = new Pixel(255, 255, 20);
    test1[1][2] = new Pixel(2, 1, 10);
    test1[2][0] = new Pixel(13, 3, 2);
    test1[2][1] = new Pixel(4, 4, 3);
    test1[2][2] = new Pixel(2, 6, 1);

    // goes over 255
    int red = (int) (255 * 0.393) + (int) (255 * 0.769) + (int) (20 * 0.189);
    ImageProcess sepia = new Sepia();
    assertNotEquals(red,
        sepia.calculateValue(test1, 1, 1).getRed());
    // test if it clamped the value to 255 max
    assertEquals(255,
        sepia.calculateValue(test1, 1, 1).getRed());
  }

  @Test
  public void sepiaAccept() {
    ImageProcess sepia = new Sepia();
    assertEquals("2 2 255\n"
        + "255 255 238\n"
        + "255 255 238\n"
        + "255 255 238\n"
        + "255 255 238", sepia.accept(image1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void sepiaAcceptNullImg() {
    ImageProcess sepia = new Sepia();
    sepia.accept(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void greyscaleAcceptNullImg() {
    ImageProcess greyscale = new Greyscale();
    greyscale.accept(null);
  }

  @Test
  public void clampMinGreyscale() {
    ImageProcess greyscale = new Greyscale();
    assertEquals(0, greyscale.clampValue(-5));
  }

  @Test
  public void clampMaxGreyscale() {
    ImageProcess greyscale = new Greyscale();
    assertEquals(255, greyscale.clampValue(300));
  }

  @Test
  public void clampMinSepia() {
    ImageProcess sepia = new Sepia();
    assertEquals(0, sepia.clampValue(-5));
  }

  @Test
  public void clampMaxSepia() {
    ImageProcess sepia = new Sepia();
    assertEquals(255, sepia.clampValue(300));
  }
}