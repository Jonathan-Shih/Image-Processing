import static org.junit.Assert.assertEquals;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.image.Pixel;
import org.junit.Test;

/**
 * Tests for the utilities class that allows for reading, writing, and creation of images.
 */
public class ImageUtilTest {

  @Test
  public void findMaxTest() {
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

    assertEquals(62, ImageUtil.findMax(test1));
  }

  @Test
  public void readWriteTest() {
    // assumes that creating an image, writing it to a file, re-reading it, and confirming
    // it is the same image means reading and writing both work.
    IImage img = ImageUtil.create(5, 2, 2);
    ImageUtil.writePPM(img, "img.ppm");
    IImage img2 = ImageUtil.readPPM("img.ppm");
    assertEquals(img.toString(), img2.toString());
  }

  @Test
  public void createCheckerboardTest() {
    IImage checkerboard = ImageUtil.create(1, 2, 2);
    assertEquals("2 2 224\n"
        + "224 224 224\n"
        + "192 192 192\n"
        + "192 192 192\n"
        + "224 224 224", checkerboard.toString());
  }

  @Test
  public void getSuffixTest() {
    assertEquals("ppm", ImageUtil.getSuffix("Koala.ppm"));
  }

  @Test
  public void getSuffixTestNoPeriod() {
    assertEquals("", ImageUtil.getSuffix("Koalappm"));
  }

  @Test
  public void getSuffixTestAbnormalSuffix() {
    assertEquals("abcabc", ImageUtil.getSuffix("Koala.abcabc"));
  }
}