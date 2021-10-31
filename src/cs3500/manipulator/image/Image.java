package cs3500.manipulator.image;

/**
 * Class representing an Image. An image has a width and height dimensions as well as a 2D array of
 * pixels that make up the image.
 */
public class Image implements IImage {

  private int width;
  private int height;
  private int maxValue;
  private IPixel[][] pixels;

  /**
   * Constructor for an Image.
   *
   * @param width    Width of the image
   * @param height   Height of the image
   * @param maxValue Highest value in the image
   * @param pixels   Pixels that make up the image
   * @throws IllegalArgumentException if provided width or height doesnt match pixel dimensions if
   *                                  the width, height, maxValue are invalid if the provided pixel
   *                                  array is invalid if the maxValue is incorrect
   */
  public Image(int width, int height, int maxValue, IPixel[][] pixels)
      throws IllegalArgumentException {
    if (width != pixels[0].length || height != pixels.length) {
      throw new IllegalArgumentException("Provided width and height must match pixel dimensions");
    }
    if (width <= 0 || height <= 0 || maxValue < 0) {
      throw new IllegalArgumentException("Provided image values are invalid");
    }
    if (!ImageUtil.checkPixels(pixels, width, height)) {
      throw new IllegalArgumentException("Provided pixel array is invalid");
    }
    if (maxValue != ImageUtil.findMax(pixels) && maxValue != 255) {
      throw new IllegalArgumentException("Provided maxValue is incorrect");
    }
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  /**
   * Creates a copy of the provided image.
   *
   * @param that image to be copied
   * @throws IllegalArgumentException if the provided image is null
   */
  public Image(IImage that) throws IllegalArgumentException {
    if (that == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    this.width = that.getWidth();
    this.height = that.getHeight();
    this.maxValue = that.getMaxValue();
    this.pixels = that.getPixels();
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getMaxValue() {
    return maxValue;
  }

  @Override
  public IPixel[][] getPixels() {
    IPixel[][] copy = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copy[i][j] = new Pixel(pixels[i][j].getRed(), pixels[i][j].getGreen(),
            pixels[i][j].getBlue());
      }
    }
    return copy;
  }

  @Override
  public String toString() {
    return width + " " + height + " " + maxValue + "\n" + printArray();
  }

  private String printArray() {
    String arr = "";
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        arr += pixels[i][j].toString();
        if (i + j != (height - 1) + (width - 1)) {
          arr += "\n";
        }
      }
    }
    return arr;
  }
}
