package cs3500.manipulator.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LayerManipulatorGui extends JFrame {

  private JPanel checkBoxPanel;
  private JPanel viewTest;
  private JPanel controlPanel;

  private JLabel imgLabel;

  private JLabel fileOpenDisplay;
  private JButton fileLoadButton;
  private JButton fileOpenButton;

  private JLabel scriptOpenDisplay;
  private JButton scriptLoadButton;
  private JButton scriptOpenButton;

  private JComboBox<String> comboBox;

  private String topVis;

  private JButton sharpenButton;
  private JButton blurButton;
  private JButton sepiaButton;
  private JButton greyscaleButton;
  private JButton removeButton;
  private JButton saveButton;

  private JTextArea textArea;

  /**
   * Sets up the GUI.
   */
  public LayerManipulatorGui() {
    super();
    setTitle("LayerManipulator");
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //dialog boxes
    JPanel openScriptBox = new JPanel();
    openScriptBox.setBorder(BorderFactory.createTitledBorder("Import Script"));
    openScriptBox.setLayout(new BoxLayout(openScriptBox, BoxLayout.PAGE_AXIS));
    mainPanel.add(openScriptBox);

    //script open
    JPanel scriptopenPanel = new JPanel();
    scriptopenPanel.setLayout(new FlowLayout());
    openScriptBox.add(scriptopenPanel);
    scriptOpenButton = new JButton("Open script");
    scriptOpenButton.setActionCommand("Open script");
    scriptopenPanel.add(scriptOpenButton);
    scriptOpenDisplay = new JLabel("File path will appear here");
    scriptopenPanel.add(scriptOpenDisplay);
    scriptLoadButton = new JButton("Load");
    scriptLoadButton.setActionCommand("Load script");
    scriptopenPanel.add(scriptLoadButton);

    //dialog boxes
    JPanel openFileBox = new JPanel();
    openFileBox.setBorder(BorderFactory.createTitledBorder("Create Layers"));
    openFileBox.setLayout(new BoxLayout(openFileBox, BoxLayout.PAGE_AXIS));
    mainPanel.add(openFileBox);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    openFileBox.add(fileopenPanel);
    fileOpenButton = new JButton("Open file");
    fileOpenButton.setActionCommand("Open file");
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);
    fileLoadButton = new JButton("Load");
    fileLoadButton.setActionCommand("Load file");
    fileopenPanel.add(fileLoadButton);

    viewTest = new JPanel();

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Top most visible layer"));
    //imagePanel.setMaximumSize(null);
    viewTest.add(imagePanel);

    imgLabel = new JLabel();
    JScrollPane imgScroll = new JScrollPane(imgLabel);
    imgLabel.setIcon(new ImageIcon(topVis));
    imgScroll.setPreferredSize(new Dimension(400, 400));
    imagePanel.add(imgScroll);

    checkBoxPanel = new JPanel();
    viewTest.add(checkBoxPanel);

    mainPanel.add(viewTest);

    // layer control panels
    controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());
    controlPanel.setBorder(BorderFactory.createTitledBorder("Layer controls"));
    comboBox = new JComboBox<String>();
    controlPanel.add(comboBox);

    JPanel actionPanel = new JPanel();
    actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
    actionPanel.setBorder(BorderFactory.createTitledBorder("Perform Actions"));

    // setup and add buttons
    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("Sharpen");
    actionPanel.add(sharpenButton);

    blurButton = new JButton("Blur");
    blurButton.setActionCommand("Blur");
    actionPanel.add(blurButton);

    sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("Sepia");
    actionPanel.add(sepiaButton);

    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("Greyscale");
    actionPanel.add(greyscaleButton);

    removeButton = new JButton("Remove");
    removeButton.setActionCommand("Remove");
    actionPanel.add(removeButton);

    controlPanel.add(actionPanel);
    mainPanel.add(controlPanel);

    JPanel savePanel = new JPanel();
    savePanel.setBorder(BorderFactory.createTitledBorder("Save topmost visible image"));
    JLabel saveText = new JLabel(
        "Enter save file name, file will be saved under auto generated name if left empty");
    savePanel.add(saveText);
    textArea = new JTextArea(1, 20);
    textArea.setBorder(BorderFactory.createTitledBorder(
        "Input Name"));
    savePanel.add(textArea);
    saveButton = new JButton("Save");
    saveButton.setActionCommand("Save");
    savePanel.add(saveButton);

    mainPanel.add(savePanel);
  }

  /**
   * Updates the file path to be opened display.
   *
   * @param str the String to update the display with.
   */
  public void updateOpenDisplay(String str) {
    fileOpenDisplay.setText(str);
  }

  /**
   * Get the file path in the file open display.
   *
   * @return the String containing the file path listed in the display.
   */
  public String getOpenDisplay() {
    return fileOpenDisplay.getText();
  }

  /**
   * Initialize every button with the given listener.
   *
   * @param listener the listener to add to all the buttons
   */
  public void setListener(ActionListener listener) {
    fileOpenButton.addActionListener(listener);
    fileLoadButton.addActionListener(listener);
    scriptOpenButton.addActionListener(listener);
    scriptLoadButton.addActionListener(listener);
    sharpenButton.addActionListener(listener);
    blurButton.addActionListener(listener);
    sepiaButton.addActionListener(listener);
    greyscaleButton.addActionListener(listener);
    removeButton.addActionListener(listener);
    saveButton.addActionListener(listener);
  }

  /**
   * Make the GUI visible.
   */
  public void display() {
    setVisible(true);
  }

  /**
   * Update the layer checkboxes.
   *
   * @param name     the list of names of layers
   * @param vis      the list of visibilities of layers
   * @param listener the listener to be used
   */
  public void updateCheckBox(List<String> name, List<Boolean> vis, ItemListener listener) {
    viewTest.remove(checkBoxPanel);
    checkBoxPanel = new JPanel();
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Layer visibility"));
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));
    for (int i = name.size() - 1; i >= 0; i--) {
      JCheckBox cur = new JCheckBox(name.get(i));
      cur.setSelected(vis.get(i));
      cur.setActionCommand(i + "");
      cur.addItemListener(listener);
      // group.add(checkBoxes[i]);
      checkBoxPanel.add(cur);
    }
    viewTest.add(checkBoxPanel);
    viewTest.invalidate();
    viewTest.validate();
    viewTest.repaint();
  }

  /**
   * Update the combo box.
   *
   * @param name     the list of layer names
   * @param listener the listener to be used
   */
  public void updateComboBox(List<String> name, ActionListener listener) {
    controlPanel.remove(comboBox);
    comboBox = new JComboBox<String>();
    for (int i = name.size() - 1; i >= 0; i--) {
      comboBox.addItem((i + 1) + ": " + name.get(i));
    }
    comboBox.setActionCommand("Select layer");
    comboBox.addActionListener(listener);
    controlPanel.add(comboBox, 0);
    controlPanel.invalidate();
    controlPanel.validate();
    controlPanel.repaint();
  }

  /**
   * Update the top visible icon with the given layer name.
   *
   * @param name the name of the layer
   */
  public void setTopVis(String name) {
    topVis = name;
    imgLabel.setIcon(new ImageIcon(topVis));
  }

  /**
   * Update the text display of the script being opened.
   *
   * @param name the name of the script file
   */
  public void updateScriptOpenDisplay(String name) {
    scriptOpenDisplay.setText(name);
  }

  /**
   * Get the script filepath.
   *
   * @return the String containing the filepath to the script.
   */
  public String getScriptOpenDisplay() {
    return scriptOpenDisplay.getText();
  }

  /**
   * Get the item in the combo box.
   *
   * @return the name of the item in the combo box
   */
  public String getComboBoxItem() {
    return (String) comboBox.getSelectedItem();
  }

  /**
   * Get the name to save to.
   *
   * @return the String containing the name for the new saved image.
   */
  public String getSaveName() {
    return textArea.getText();
  }

  /**
   * Set the shown icon the the given image.
   *
   * @param img the BufferedImage to be used.
   */
  public void setImageIcon(BufferedImage img) {
    imgLabel.setIcon(new ImageIcon(img));
  }
}
