package cs3500.manipulator.controller;

import cs3500.manipulator.GUI.LayerManipulatorGui;
import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.model.LayerManipulatorModel;
import cs3500.manipulator.model.SimpleLayerManipulator;
import cs3500.manipulator.processes.filters.Blur;
import cs3500.manipulator.processes.filters.Sharpen;
import cs3500.manipulator.processes.transformations.Greyscale;
import cs3500.manipulator.processes.transformations.Sepia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controller for the program when run in GUI mode. Provides functionality to GUI.
 */
public class LayerModelController implements ActionListener, ItemListener {

  LayerManipulatorGui view;
  LayerManipulatorModel model;

  /**
   * Initialize the controller with the given view and model.
   * @param view the GUI object to use as the view
   * @param model the layer model to use as the backend application
   */
  public LayerModelController(LayerManipulatorGui view, LayerManipulatorModel model) {
    this.view = view;
    this.model = model;
    view.setListener(this);

    view.display();
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    switch (arg0.getActionCommand()) {
      case "Open file":
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG & PPM Images", "jpg", "png", "ppm");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(view);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          view.updateOpenDisplay(f.getAbsolutePath());
        }
        break;
      case "Load file":
        IImage img = ImageUtil.readImage(view.getOpenDisplay());
        String name = getFileName(view.getOpenDisplay());
        model.addImage(img, name);
        view.updateCheckBox(model.getLayerNames(), model.getVisibilities(), this);
        //view.addCheckBox(name, model.numLayers() - 1, this);
        view.updateComboBox(model.getLayerNames(), this);
        view.setImageIcon(ImageUtil.convertToBi(img));
        break;
      case "Open script":
        final JFileChooser sChooser = new JFileChooser(".");
        FileNameExtensionFilter scriptFilter = new FileNameExtensionFilter(
            "txt files", "txt");
        sChooser.setFileFilter(scriptFilter);
        int scriptRetvalue = sChooser.showOpenDialog(view);
        if (scriptRetvalue == JFileChooser.APPROVE_OPTION) {
          File s = sChooser.getSelectedFile();
          view.updateScriptOpenDisplay(s.getAbsolutePath());
        }
        break;
      case "Load script":
        String scriptFPath = view.getScriptOpenDisplay();
        SimpleLayerManipulatorController loadScript = null;
        try {
          loadScript = new SimpleLayerManipulatorController(
              new SimpleLayerManipulator(), new FileReader(scriptFPath));
        } catch (FileNotFoundException fnfe) {
          fnfe.printStackTrace();
        }
        loadScript.startController();
        break;
      case "Sharpen":
        if (model.numLayers() > 0) {
          performFcn("Sharpen");
        }
        break;
      case "Blur":
        if (model.numLayers() > 0) {
          performFcn("Blur");
        }
        break;
      case "Sepia":
        if (model.numLayers() > 0) {
          performFcn("Sepia");
        }
        break;
      case "Greyscale":
        if (model.numLayers() > 0) {
          performFcn("Greyscale");
        }
        break;
      case "Remove":
        if (model.numLayers() > 0) {
          model.deleteLayer(getCurrentLayer());
          view.updateCheckBox(model.getLayerNames(), model.getVisibilities(), this);
          view.updateComboBox(model.getLayerNames(), this);
          view.setImageIcon(ImageUtil.convertToBi(model.pullTopVisibleImage()));
        }
        break;
      case "Save":
        if (model.numLayers() > 0) {
          img = model.pullTopVisibleImage();
          int idx = model.pullTopVisibleImageIndex();
          String fileName = "";
          if (view.getSaveName().equals("")) {
            fileName = model.getName(idx);
          } else {
            fileName = view.getSaveName();
          }
          ImageUtil.writeImage(img, fileName);
        }
      default :
        // GUI will not have invalid inputs
    }
  }

  private void performFcn(String action) {
    int idx = getCurrentLayer();
    String suffix = ImageUtil.getSuffix(model.getName(idx));
    String imgName = model.getName(idx)
        .substring(0, model.getName(idx).length() - suffix.length() - 1);
    switch (action) {
      case "Sepia":
        model.processAtIndex(new Sepia(), idx);
        imgName = "Sepia" + imgName;
        model.setName(imgName + "." + suffix, idx);
        break;
      case "Greyscale":
        model.processAtIndex(new Greyscale(), idx);
        imgName = "Greyscale" + imgName;
        model.setName(imgName + "." + suffix, idx);
        break;
      case "Blur":
        model.processAtIndex(new Blur(), idx);
        imgName += "Blurred";
        model.setName(imgName + "." + suffix, idx);
        break;
      case "Sharpen":
        model.processAtIndex(new Sharpen(), idx);
        imgName += "Sharpened";
        model.setName(imgName + "." + suffix, idx);
        break;
      default:
        throw new IllegalArgumentException("invalid filter");
    }
    if (idx == model.numLayers() - 1) {
      view.setImageIcon(ImageUtil.convertToBi(model.pullTopVisibleImage()));
    }
  }

  private String getFileName(String path) {
    List<Character> name = new ArrayList();
    for (int i = path.length() - 1; i >= 0; i--) {
      if (path.charAt(i) == '/') {
        break;
      }
      name.add(path.charAt(i));
    }
    Collections.reverse(name);
    String result = "";
    for (Character c : name) {
      result += c;
    }
    return result;
  }

  @Override
  public void itemStateChanged(ItemEvent event) {
    String who = ((JCheckBox) event.getItemSelectable()).getActionCommand();
    int index = Integer.valueOf(who);
    model.switchVisibility(index);
    view.setImageIcon(ImageUtil.convertToBi(model.pullTopVisibleImage()));
  }

  private int getCurrentLayer() {
    String layer = view.getComboBoxItem();
    String num = "";
    for (int i = 0; i < layer.length(); i++) {
      if (layer.charAt(i) == ':') {
        return Integer.valueOf(num) - 1;
      }
      num += layer.charAt(i);
    }
    return Integer.valueOf(num) - 1;
  }

}
