package cs3500.manipulator.image;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Reads a PPM image and converts it to an {@link Image}.
   *
   * @param filename Filepath of the desired file
   * @return Provided PPM file converted to an {@link Image}
   * @throws IllegalArgumentException File not found or issue creating pixels
   */
  public static IImage readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    IPixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = new Pixel(r, g, b);
      }
    }
    if (checkPixels(pixels, width, height)) {
      return new Image(width, height, maxValue, pixels);
    } else {
      throw new IllegalArgumentException("Pixels not initialized correctly");
    }
  }

  /**
   * Checks if the given pixel array has any invalid values and if the dimensions match.
   *
   * @param pix    Provided pixel array
   * @param width  Width of the image
   * @param height Height of the image
   * @return Is the given array valid
   */
  public static boolean checkPixels(IPixel[][] pix, int width, int height) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (pix[i].length != width || pix[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Writes the provided image into a PPM file.
   *
   * @param img  image to be exported
   * @param name name of the outputted PPM file
   * @throws IllegalArgumentException if the provided image has illegal pixels
   */
  public static void writePPM(IImage img, String name) throws IllegalArgumentException {
    if (!checkPixels(img.getPixels(), img.getWidth(), img.getHeight())) {
      throw new IllegalArgumentException("Pixels not valid");
    }
    try {
      BufferedWriter output = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(name)));
      // write the header for a PPM image file
      output.write("P3");
      output.newLine();
      output.write(img.getWidth() + " " + img.getHeight());
      output.newLine();
      output.write(img.getMaxValue() + "");
      output.newLine();

      IPixel[][] pixels = img.getPixels();
      // write the rgb values of each pixel
      for (int i = 0; i < img.getHeight(); i++) {
        for (int j = 0; j < img.getWidth(); j++) {
          output.write(pixels[i][j].getRed() + "");
          output.newLine();
          output.write(pixels[i][j].getGreen() + "");
          output.newLine();
          output.write(pixels[i][j].getBlue() + "");
          if (j < img.getWidth() - 1) {
            output.newLine();
          }
        }
        output.newLine();
      }
      output.flush();
      output.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Finds the biggest R/G/B value in the given pixels.
   *
   * @param pixels Pixels of an image
   * @return Biggest value
   */
  public static int findMax(IPixel[][] pixels) {
    int red = findMaxHelp(pixels, "red");
    int green = findMaxHelp(pixels, "green");
    int blue = findMaxHelp(pixels, "blue");
    return Math.max(red, Math.max(green, blue));
  }

  private static int findMaxHelp(IPixel[][] pixels, String channel) {
    int maxValue = 0;
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        int curValue = 0;
        switch (channel) {
          case "red":
            curValue = pixels[i][j].getRed();
            break;
          case "green":
            curValue = pixels[i][j].getGreen();
            break;
          case "blue":
            curValue = pixels[i][j].getBlue();
            break;
          default:
            throw new IllegalArgumentException("Channel not valid");
        }
        if (curValue > maxValue) {
          maxValue = curValue;
        }
      }
    }
    return maxValue;
  }

  /**
   * Creates a checkerboard image of light and dark grey squares.
   *
   * @param checkerSize the size of each square check on the checkerboard image
   * @param width       the number of checks wide the image is
   * @param height      the number of checks tall the image is
   * @return Checkerboard image
   */
  public static IImage create(int checkerSize, int width, int height) {
    Pixel light = new Pixel(224, 224, 224);
    Pixel dark = new Pixel(192, 192, 192);

    IPixel[][] array = new Pixel[height * checkerSize][width * checkerSize];

    for (int i = 0; i < height * checkerSize; i++) {
      for (int j = 0; j < width * checkerSize; j++) {
        int widthBox = j / checkerSize;
        int heightBox = i / checkerSize;

        if ((widthBox + heightBox) % 2 == 0) {
          array[i][j] = light;
        } else {
          array[i][j] = dark;
        }
      }
    }
    return new Image(width * checkerSize, height * checkerSize, 224, array);
  }

  /**
   * Reads an image file and creates the subsequent IImage object.
   * @param filename the path to the image file
   * @return  an image object to represent the image
   */
  public static IImage readImage(String filename) {
    BufferedImage current;
    if (getSuffix(filename).equals("ppm")) {
      return readPPM(filename);
    }
    try {
      current = ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("read failed");
    }

    IPixel[][] array = new Pixel[current.getHeight()][current.getWidth()];

    for (int i = 0; i < current.getHeight(); i++) {
      for (int j = 0; j < current.getWidth(); j++) {
        int p = current.getRGB(j, i);
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        array[i][j] = new Pixel(r, g, b);
      }
    }
    return new Image(current.getWidth(), current.getHeight(), ImageUtil.findMax(array), array);
  }

  /**
   * Writes a given IImage object to the given filename.
   * @param img the image object to write
   * @param filename the destination filename
   */
  public static void writeImage(IImage img, String filename) {
    if (getSuffix(filename).equals("ppm")) {
      writePPM(img, filename);
      return;
    }
    BufferedImage newImage = convertToBi(img);
    File outputFile = new File(filename);
    try {
      ImageIO.write(newImage, ImageUtil.getSuffix(filename), outputFile);
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Unable to write to file");
    }
  }

  /**
   * Convert an image to a {@code BufferedImage}.
   * @param img the IImage to be converted
   * @return the new BufferedImage
   */
  public static BufferedImage convertToBi(IImage img) {
    BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        newImage.setRGB(j, i, img.getPixels()[i][j].getRGB());
      }
    }
    return newImage;
  }

  /**
   * Extracts a suffix from a given filename string.
   * @param fileName the filename
   * @return the suffix of the filename
   */
  public static String getSuffix(String fileName) {
    List<Character> name = new ArrayList();
    for (int i = fileName.length() - 1; i >= 0; i--) {
      if (fileName.charAt(i) == '.') {
        break;
      }
      name.add(fileName.charAt(i));
    }
    Collections.reverse(name);
    String result = "";
    for (Character c : name) {
      result += c;
    }
    return result;
  }
}

