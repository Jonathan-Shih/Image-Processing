package cs3500.manipulator.model;

import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.Image;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.processes.ImageProcess;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An implementation of {@code LayerManipulatorModel} that uses a {@code SimpleManipulatorModel} to
 * represent each layer in the model and a list of booleans to represent their visibilities.
 * Constructors and methods enforce the invariant that these lists are of the same length.
 */
public class SimpleLayerManipulator implements LayerManipulatorModel {

  private final List<SimpleManipulatorModel> layers;
  private final List<Boolean> visibilities;
  private final List<String> layerNames;

  /**
   * Default constructor that creates an empty list of layers and of visibilities.
   */
  public SimpleLayerManipulator() {
    layers = new ArrayList<SimpleManipulatorModel>();
    visibilities = new ArrayList<Boolean>();
    layerNames = new ArrayList<String>();
  }

  /**
   * Constructor to initialize the model with a single layer formed from the given image, with
   * visibility set to true.
   *
   * @param img the {@code Image} to create a new layer from.
   */
  public SimpleLayerManipulator(IImage img, String name) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    layers = new ArrayList<SimpleManipulatorModel>();
    layers.add(new SimpleManipulatorModel(img));

    layerNames = new ArrayList<String>();
    layerNames.add(name);

    visibilities = new ArrayList<Boolean>();
    visibilities.add(true);
  }

  @Override
  public void addImage(IImage img, String name) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    layers.add(new SimpleManipulatorModel(img));
    layerNames.add(name);
    visibilities.add(true);
  }

  @Override
  public void insertAtIndex(IImage img, int index, String name) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (index > layers.size() || index < 0) {
      throw new IllegalArgumentException("Layer index out of bounds.");
    }
    layers.add(index, new SimpleManipulatorModel(img));
    layerNames.add(name);
    visibilities.add(index, true);
  }

  @Override
  public void setLayer(IImage img, int index, String name) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (index >= layers.size() || index < 0) {
      throw new IllegalArgumentException("Layer index out of bounds.");
    }
    layers.set(index, new SimpleManipulatorModel(img));
    layerNames.set(index, name);
    visibilities.set(index, true);
  }

  @Override
  public void processAtIndex(ImageProcess process, int index) {
    if (process == null) {
      throw new IllegalArgumentException("Process cannot be null");
    }
    if (index >= layers.size() || index < 0) {
      throw new IllegalArgumentException("Layer index out of bounds.");
    }
    layers.get(index).processImage(process);
  }

  @Override
  public IImage pullFromIndex(int index) {
    if (index >= layers.size() || index < 0) {
      throw new IllegalArgumentException("Layer index out of bounds.");
    }
    return new Image(layers.get(index).pullImage());
  }

  @Override
  public void deleteLayer(int index) {
    if (index >= layers.size() || index < 0) {
      throw new IllegalArgumentException("Layer index out of bounds.");
    }
    layers.remove(index);
    visibilities.remove(index);
    layerNames.remove(index);
  }

  @Override
  public void switchVisibility(int index) {
    if (index >= visibilities.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    boolean old = visibilities.get(index);
    visibilities.set(index, !old);
  }

  @Override
  public boolean getVisibilityAt(int index) {
    if (index >= visibilities.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    return visibilities.get(index);
  }

  @Override
  public Image pullTopVisibleImage() {
    for (int i = visibilities.size() - 1; i >= 0; i--) {
      if (visibilities.get(i)) {
        return new Image(layers.get(i).pullImage());
      }
    }
    return new Image(layers.get(layers.size() - 1).pullImage());
  }

  @Override
  public int pullTopVisibleImageIndex() {
    for (int i = visibilities.size() - 1; i >= 0; i--) {
      if (visibilities.get(i)) {
        return i;
      }
    }
    return layers.size() - 1;
  }

  @Override
  public int numLayers() {
    return layers.size();
  }

  @Override
  public String getName(int index) throws IllegalArgumentException {
    if (index >= layerNames.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    return layerNames.get(index);
  }

  @Override
  public void setName(String name, int index) throws IllegalArgumentException {
    if (index >= layerNames.size() || index < 0) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    layerNames.set(index, name);
  }

  @Override
  public List<String> getLayerNames() {
    return copyList(layerNames);
  }

  @Override
  public List<Boolean> getVisibilities() {
    return copyList(visibilities);
  }

  private <K> List<K> copyList(List<K> list) {
    List<K> copy = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      copy.add(list.get(i));
    }
    return copy;
  }

  @Override
  public void readFile(String filename) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
    while (sc.hasNext()) {
      String in = sc.next();
      IImage img = ImageUtil.readImage(in);
      layers.add(new SimpleManipulatorModel(img));
      layerNames.add(in);
      visibilities.add(true);
    }
  }

  @Override
  public void saveLayers(String filename) {
    if (layers.size() == 0 || visibilities.size() == 0 || layerNames.size() == 0) {
      throw new IllegalArgumentException("This model does not have any layers");
    }
    try {
      BufferedWriter output = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(filename)));
      for (int i = 0; i < layerNames.size(); i++) {
        output.write(layerNames.get(i));
        if (i != layerNames.size() - 1) {
          output.newLine();
        }
      }
      output.flush();
      output.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
