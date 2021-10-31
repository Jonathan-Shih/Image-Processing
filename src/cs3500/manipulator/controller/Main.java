package cs3500.manipulator.controller;

import cs3500.manipulator.GUI.LayerManipulatorGui;
import cs3500.manipulator.model.SimpleLayerManipulator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * The driver class for the compiled JAR file.
 */
public class Main {

  /**
   * Drives the program.
   * @param args the command line arguments given.
   * @throws FileNotFoundException when the script file given is not located
   */
  public static void main(String[] args) throws FileNotFoundException {

    if (args[0].length() > 7 && args[0].substring(0, 7).equals("-script")) {
      SimpleLayerManipulatorController scriptCtrl = new SimpleLayerManipulatorController(
          new SimpleLayerManipulator(), new FileReader(args[0].substring(8)));
      scriptCtrl.startController();
    } else if (args[0].length() >= 5 && args[0].substring(0, 5).equals("-text")) {
      SimpleLayerManipulatorController textCtrl = new SimpleLayerManipulatorController(
          new SimpleLayerManipulator(), new InputStreamReader(System.in));
      textCtrl.startController();
    } else if (args[0].length() >= 12 && args[0].substring(0, 12).equals("-interactive")) {
      LayerModelController guiCtrl = new LayerModelController(new LayerManipulatorGui(),
          new SimpleLayerManipulator());
    } else {
      throw new IllegalArgumentException("Improper argument given to main method.");
    }


  }

}
