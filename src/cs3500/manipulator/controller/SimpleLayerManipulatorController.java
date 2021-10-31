package cs3500.manipulator.controller;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.model.SimpleLayerManipulator;
import cs3500.manipulator.processes.filters.Blur;
import cs3500.manipulator.processes.filters.Sharpen;
import cs3500.manipulator.processes.transformations.Greyscale;
import cs3500.manipulator.processes.transformations.Sepia;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class representing a controller for {@link SimpleLayerManipulator}. Allows users to perform
 * simple commands like create layers, apply filters, change visibility, export images.
 */
public class SimpleLayerManipulatorController {

  private final SimpleLayerManipulator model;
  private final Scanner sc;

  /**
   * Constructor for SimpleLayerManipularController.
   *
   * @param model LayerManipulatorModel
   * @param rd    Readable
   * @throws IllegalArgumentException if the readable or model is null
   */
  public SimpleLayerManipulatorController(SimpleLayerManipulator model, Readable rd)
      throws IllegalArgumentException {
    if (rd == null || model == null) {
      throw new IllegalArgumentException("Readable and/or Model cannot be null");
    }
    this.model = model;
    this.sc = new Scanner(rd);
  }

  /**
   * Method for controlling a LayerManipulator. This method returns if quit by the user
   * @throws IllegalArgumentException if a command sequence is invalid
   */
  public void startController() throws IllegalArgumentException {
    while (sc.hasNext()) {
      String in = sc.next();
      if (in.equals("quit")) {
        return;
      }
      switch (in) {
        case "create":
          try {
            String next = sc.next();
            if (next.equals("quit")) {
              return;
            }
            if (!next.equals("layer")) {
              throw new IllegalArgumentException("invalid command");
            }
            String imgName = sc.next();
            IImage img = ImageUtil.readImage(imgName);
            model.addImage(img, imgName);
          } catch (NoSuchElementException nsee) {
            throw new IllegalArgumentException("invalid command");
          }
          break;
        case "visibility":
          String target = sc.next();
          if (target.equals("quit")) {
            return;
          }
          if (target.equals("true") || target.equals("false")) {
            String idx = sc.next();
            if (idx.equals("quit")) {
              return;
            }
            String vis = Boolean.toString(model.getVisibilityAt(getInt(idx)));
            // if the targeted visibility does not match the current one
            if (!target.equals(vis)) {
              model.switchVisibility(getInt(idx));
            }
          } else {
            throw new IllegalArgumentException("invalid command");
          }

          break;
        case "save":
          String imgName = sc.next();
          if (imgName.equals("quit")) {
            return;
          }
          Image img = model.pullTopVisibleImage();
          if (imgName.equals("default")) {
            int idx = model.pullTopVisibleImageIndex();
            System.out.println(model.getName(idx));
            ImageUtil.writeImage(img, model.getName(idx));
          } else {
            ImageUtil.writeImage(img, imgName);
          }
          break;
        case "filter":
          String fcn = sc.next();
          if (fcn.equals("quit")) {
            return;
          }
          String idx = sc.next();
          if (idx.equals("quit")) {
            return;
          }
          performFcn(getInt(idx), fcn);
          break;
        case "remove":
          model.deleteLayer(getInt(sc.next()));
          break;
        default:
          throw new IllegalArgumentException("unknown command");

      }
    }
  }


  private int getInt(String idx) {
    int index;
    try {
      index = Integer.valueOf(idx) - 1;
    } catch (NoSuchElementException nsee) {
      throw new IllegalArgumentException("invalid command");
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("invalid command");
    }
    return index;
  }

  private void performFcn(int idx, String fcn) {
    if (idx >= model.numLayers() || idx < 0) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    String suffix = ImageUtil.getSuffix(model.getName(idx));
    String imgName = model.getName(idx)
        .substring(0, model.getName(idx).length() - suffix.length() - 1);
    switch (fcn) {
      case "sepia":
        model.processAtIndex(new Sepia(), idx);
        imgName = "Sepia" + imgName;
        model.setName(imgName + "." + suffix, idx);
        break;
      case "greyscale":
        model.processAtIndex(new Greyscale(), idx);
        imgName = "Greyscale" + imgName;
        model.setName(imgName + "." + suffix, idx);
        break;
      case "blur":
        model.processAtIndex(new Blur(), idx);
        imgName += "Blurred";
        model.setName(imgName + "." + suffix, idx);
        break;
      case "sharpen":
        model.processAtIndex(new Sharpen(), idx);
        imgName += "Sharpened";
        model.setName(imgName + "." + suffix, idx);
        break;
      default:
        throw new IllegalArgumentException("invalid filter");
    }

  }
}
