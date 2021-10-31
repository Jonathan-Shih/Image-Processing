import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.manipulator.image.IPixel;
import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the representation of images ({@code Image}) in this program.
 */
public class ImageTest {

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

  @Test(expected = IllegalArgumentException.class)
  public void invalidPixelArrNullValues() {
    Pixel[][] arr = new Pixel[2][2];
    Image test = new Image(2, 2, 2, arr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPixelArrInvalidWidth() {
    Pixel[][] arr = new Pixel[2][2];
    arr[0][0] = new Pixel(255, 255, 255);
    arr[0][1] = new Pixel(255, 255, 255);
    arr[1][0] = new Pixel(255, 255, 255);
    arr[1][1] = new Pixel(255, 255, 255);
    Image test = new Image(3, 2, 255, arr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPixelArrInvalidHeight() {
    Pixel[][] arr = new Pixel[2][2];
    arr[0][0] = new Pixel(255, 255, 255);
    arr[0][1] = new Pixel(255, 255, 255);
    arr[1][0] = new Pixel(255, 255, 255);
    arr[1][1] = new Pixel(255, 255, 255);
    Image test = new Image(2, 3, 255, arr);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPixelArrInvalidMaxValue() {
    Pixel[][] arr = new Pixel[2][2];
    arr[0][0] = new Pixel(255, 255, 255);
    arr[0][1] = new Pixel(255, 255, 255);
    arr[1][0] = new Pixel(255, 255, 255);
    arr[1][1] = new Pixel(255, 255, 255);
    Image test = new Image(2, 2, 2, arr);
  }

  @Test
  public void getWidth() {
    assertEquals(2, image1.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(2, image1.getHeight());
  }

  @Test
  public void getMaxValue() {
    assertEquals(255, image1.getMaxValue());
  }

  @Test
  public void getPixArr() {
    Pixel[][] arr2 = new Pixel[2][2];
    arr2[0][0] = new Pixel(255, 255, 255);
    arr2[0][1] = new Pixel(255, 255, 255);
    arr2[1][0] = new Pixel(255, 255, 255);
    arr2[1][1] = new Pixel(255, 255, 255);

    IPixel[][] og = image1.getPixels();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(arr2[i][j].toString(), og[i][j].toString());
      }
    }
  }

  @Test
  public void getPixArrDeepCopy() {
    IPixel[][] og = image1.getPixels();
    og[0][0] = new Pixel(0, 0, 0);
    og[0][1] = new Pixel(0, 0, 0);
    og[1][0] = new Pixel(0, 0, 0);
    og[1][1] = new Pixel(0, 0, 0);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertNotEquals(og[i][j].toString(), image1.getPixels()[i][j].toString());
      }
    }
  }

  @Test
  public void testToString() {
    assertEquals("2 2 255\n"
        + "255 255 255\n"
        + "255 255 255\n"
        + "255 255 255\n"
        + "255 255 255", image1.toString());
  }
}