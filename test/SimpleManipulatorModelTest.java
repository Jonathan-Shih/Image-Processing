import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


import cs3500.manipulator.image.IImage;
import cs3500.manipulator.image.ImageUtil;
import cs3500.manipulator.model.SimpleManipulatorModel;
import cs3500.manipulator.processes.transformations.Sepia;
import cs3500.manipulator.processes.filters.Sharpen;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the {@Code SimpleManipulatorModel} implementation of {@code ImageManipulatorModel}.
 */
public class SimpleManipulatorModelTest {

  SimpleManipulatorModel m;
  IImage img;

  @Before
  public void init() {
    m = new SimpleManipulatorModel();
  }

  @Test
  public void testModelProcess() {
    img = ImageUtil.create(20, 5, 5);
    m.storeImage(img);
    // ensure the image pulled from the model is the same image
    assertEquals(img, m.pullImage());

    // store a new image in the model and process it, ensuring it has changed
    IImage img2 = ImageUtil.create(10, 10, 10);
    m.storeImage(img2);
    m.processImage(new Sepia());
    assertNotEquals(img2, m.pullImage());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testProcessNullImage() {
    m.processImage(new Sharpen());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPullNullImage() {
    m.pullImage();
  }
}