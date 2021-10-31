import static org.junit.Assert.assertEquals;

import cs3500.manipulator.controller.SimpleLayerManipulatorController;
import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.model.SimpleLayerManipulator;
import cs3500.manipulator.processes.ImageProcess;
import cs3500.manipulator.processes.filters.Blur;
import cs3500.manipulator.processes.filters.Sharpen;
import cs3500.manipulator.processes.transformations.Greyscale;
import cs3500.manipulator.processes.transformations.Sepia;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link SimpleLayerManipulatorController}. Confirming if the inputs work as intended
 */
public class SimpleLayerManipulatorControllerTest {

  SimpleLayerManipulator model;
  IImage testImg;
  IImage img1;
  IImage img2;
  ImageProcess blur;
  ImageProcess sharpen;
  ImageProcess greyscale;
  ImageProcess sepia;

  @Before
  public void init() {
    model = new SimpleLayerManipulator();
    img1 = ImageUtil.create(1, 3, 3);
    img2 = ImageUtil.create(1, 4, 4);
    model.addImage(img1, "image1.ppm");
    model.addImage(img2, "image2.ppm");

    testImg = ImageUtil.create(1, 3, 3);
    blur = new Blur();
    sharpen = new Sharpen();
    greyscale = new Greyscale();
    sepia = new Sepia();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommand() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("test"));
    control.startController();
  }

  @Test
  public void testIQuit() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("quit"));
    control.startController();
    assertEquals(img1.toString(), model.pullFromIndex(0).toString());
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test
  public void testBlurFilter() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter blur 1 quit"));
    control.startController();
    assertEquals(blur.accept(testImg).toString(), model.pullFromIndex(0).toString());
    assertEquals("image1Blurred.ppm", model.getName(0));
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test
  public void testSharpenFilter() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter sharpen 1 quit"));
    control.startController();
    assertEquals(sharpen.accept(testImg).toString(), model.pullFromIndex(0).toString());
    assertEquals("image1Sharpened.ppm", model.getName(0));
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test
  public void testGreyscaleFilter() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter greyscale 1 quit"));
    control.startController();
    assertEquals(greyscale.accept(testImg).toString(), model.pullFromIndex(0).toString());
    assertEquals("Greyscaleimage1.ppm", model.getName(0));
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test
  public void testSepiaFilter() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter sepia 1 quit"));
    control.startController();
    assertEquals(sepia.accept(testImg).toString(), model.pullFromIndex(0).toString());
    assertEquals("Sepiaimage1.ppm", model.getName(0));
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterErrIndex() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter sepia 3 quit"));
    control.startController();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterErrIndexStr() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter sepia test quit"));
    control.startController();
  }

  @Test
  public void testFilterCmdQuit() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter quit sepia 3"));
    control.startController();
    assertEquals(img1.toString(), model.pullFromIndex(0).toString());
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test
  public void testFilterIdxQuit() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter sepia quit 3"));
    control.startController();
    assertEquals(img1.toString(), model.pullFromIndex(0).toString());
    assertEquals(img2.toString(), model.pullFromIndex(1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterErrCmd() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("filter command 3 quit"));
    control.startController();
  }

  @Test
  public void testVisibility() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility false 2 quit"));
    control.startController();
    assertEquals(img1.toString(), model.pullTopVisibleImage().toString());
  }

  @Test
  public void testVisibilitySame() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility true 2 quit"));
    control.startController();
    assertEquals(img2.toString(), model.pullTopVisibleImage().toString());
  }

  @Test
  public void testVisibilityQuitCmd() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility quit false 2"));
    control.startController();
    assertEquals(img2.toString(), model.pullTopVisibleImage().toString());
  }

  @Test
  public void testVisibilityQuitIdx() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility true quit 2"));
    control.startController();
    assertEquals(img2.toString(), model.pullTopVisibleImage().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVisibilityInvalidToggle() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility test 2 quit"));
    control.startController();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVisibilityInvalidIndexOver() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility test 3 quit"));
    control.startController();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVisibilityInvalidIndexString() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("visibility true test quit"));
    control.startController();
  }

  @Test
  public void testRemove() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("remove 2 quit"));
    control.startController();
    assertEquals(img1.toString(), model.pullTopVisibleImage().toString());
    assertEquals(1, model.numLayers());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveErrIdx() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("remove 3 quit"));
    control.startController();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveErrStr() {
    SimpleLayerManipulatorController control = new SimpleLayerManipulatorController(model,
        new StringReader("remove test quit"));
    control.startController();
  }
}