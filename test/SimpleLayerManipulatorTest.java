import static org.junit.Assert.assertEquals;
import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.model.SimpleLayerManipulator;
import cs3500.manipulator.processes.transformations.Sepia;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests for the SimpleLayerManipulator model.
 */
public class SimpleLayerManipulatorTest {

  @Test
  public void testLayerInsertionAndPullingAndDeleting() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5, 10, 10);
    IImage img2 = ImageUtil.create(10, 5, 5);
    m.insertAtIndex(img1, 0, "Image1.ppm");
    m.insertAtIndex(img2, 0, "Image2.ppm");
    assertEquals(img1.toString(), m.pullFromIndex(1).toString());
    assertEquals(img2.toString(), m.pullFromIndex(0).toString());
    IImage img3 = ImageUtil.create(10, 8, 8);
    m.setLayer(img3, 0, "Image3.ppm");
    assertEquals(img1.toString(), m.pullFromIndex(1).toString());
    assertEquals(img3.toString(), m.pullFromIndex(0).toString());
    m.deleteLayer(0);
    assertEquals(img1.toString(), m.pullFromIndex(0).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageInConstructor() {
    SimpleLayerManipulator m = new SimpleLayerManipulator(null, "test.ppm");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageInInsert() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    m.insertAtIndex(null, 0, "test.jpg");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageInSet() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    m.insertAtIndex(ImageUtil.create(5, 5, 5), 0, "test.jpg");
    m.setLayer(null, 0, "test.jpg");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullImageInProcess() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    m.insertAtIndex(ImageUtil.create(5, 5, 5), 0, "test.jpg");
    m.processAtIndex(null, 0);
  }

  @Test
  public void testProcessAtIndex() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5, 10, 10);
    IImage img2 = ImageUtil.create(10, 5, 5);
    m.insertAtIndex(img1, 0, "image1.jpg");
    m.insertAtIndex(img2, 0, "image2.jpg");
    m.processAtIndex(new Sepia(), 1);
    assertEquals(img2.toString(), m.pullFromIndex(0).toString());
    assertEquals(new Sepia().accept(img1).toString(), m.pullFromIndex(1).toString());
  }

  @Test
  public void testVisibilityAndTopVisibleImage() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5, 10, 10);
    IImage img2 = ImageUtil.create(10, 5, 5);
    IImage img3 = ImageUtil.create(10, 8, 8);
    m.insertAtIndex(img1, 0, "image1.png");
    m.insertAtIndex(img2, 0, "image2.png");
    m.insertAtIndex(img3, 0, "image3.png");

    for (int i = 0; i < 3; i++) {
      assertEquals(true, m.getVisibilityAt(i));
    }

    m.switchVisibility(2);
    assertEquals(false, m.getVisibilityAt(2));
    m.switchVisibility(2);
    assertEquals(true, m.getVisibilityAt(2));

    m.switchVisibility(1);
    m.switchVisibility(2);

    assertEquals(img3.toString(), m.pullTopVisibleImage().toString());
    m.switchVisibility(2);
    assertEquals(img1.toString(), m.pullTopVisibleImage().toString());
  }

  @Test
  public void testNumLayers() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    assertEquals(0, m.numLayers());

    IImage img1 = ImageUtil.create(5, 5, 5);
    m.insertAtIndex(img1, 0, "image1.png");
    assertEquals(1, m.numLayers());

    for (int i = 0; i < 15; i++) {
      m.insertAtIndex(img1, 0, "image1.png");
    }
    assertEquals(16, m.numLayers());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsInsert() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 2, "image1.png");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsSet() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.setLayer(img1, 0, "image1.png");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsProcess() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 0, "image1.png");
    m.processAtIndex(new Sepia(), 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsPull() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 0, "image1.png");
    IImage img2 = m.pullFromIndex(1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsDelete() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 0, "image1.png");
    m.deleteLayer(-2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsToggle() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 0, "image1.png");
    m.switchVisibility(2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOutOfBoundsGetVis() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(5,5,5);
    m.insertAtIndex(img1, 0, "image1.png");
    m.getVisibilityAt(-1);
  }

  @Test
  public void testAddImage() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");
    assertEquals(img1.toString(), m.pullTopVisibleImage().toString());

    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img2, "image2.png");
    assertEquals(img2.toString(), m.pullTopVisibleImage().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageNull() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    m.addImage(null, "test.png");
  }

  @Test
  public void testPullTopVisibleImageIndex() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img1, "image1.png");
    m.addImage(img2, "image2.png");

    assertEquals(1, m.pullTopVisibleImageIndex());
    m.switchVisibility(1);
    assertEquals(0, m.pullTopVisibleImageIndex());
  }

  @Test
  public void testPullTopVisibleImageIndexNoVisible() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img1, "image1.png");
    m.addImage(img2, "image2.png");

    m.switchVisibility(1);
    assertEquals(0, m.pullTopVisibleImageIndex());
    m.switchVisibility(0);
    assertEquals(1, m.pullTopVisibleImageIndex());
  }

  @Test
  public void testGetName() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img1, "image1.png");
    m.addImage(img2, "image2.png");
    assertEquals("image2.png", m.getName(1));
    assertEquals("image1.png", m.getName(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNameOverIdx() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");

    m.getName(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNameUnderIdx() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");

    m.getName(-1);
  }

  @Test
  public void testSetName() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");
    assertEquals("image1.png", m.getName(0));
    m.setName("1image.png", 0);
    assertEquals("1image.png", m.getName(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameOverIdx() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");

    m.setName("test", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameUnderIdx() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    m.addImage(img1, "image1.png");

    m.setName("test", -1);
  }

  @Test
  public void testGetLayerNamesMt() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    assertEquals(new ArrayList(),m.getLayerNames());
  }

  @Test
  public void testGetLayerNames() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img1, "image1.png");
    m.addImage(img2, "image2.png");
    List<String> test = new ArrayList();
    test.add("image1.png");
    test.add("image2.png");
    assertEquals(test, m.getLayerNames());
  }

  @Test
  public void testGetVisibilitiesMt() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    assertEquals(new ArrayList(),m.getVisibilities());
  }

  @Test
  public void testGetVisibilities() {
    SimpleLayerManipulator m = new SimpleLayerManipulator();
    IImage img1 = ImageUtil.create(1, 3, 3);
    IImage img2 = ImageUtil.create(1, 4, 4);
    m.addImage(img1, "image1.png");
    m.addImage(img2, "image2.png");
    m.switchVisibility(1);
    List<Boolean> test = new ArrayList();
    test.add(true);
    test.add(false);
    assertEquals(test, m.getVisibilities());
  }
}