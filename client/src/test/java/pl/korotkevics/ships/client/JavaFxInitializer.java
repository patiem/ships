package pl.korotkevics.ships.client;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.awt.GraphicsEnvironment;

/**
 * Class using to initialize javafx thread to tests.
 *
 * @author Magdalena Aarsman
 * @since 2018-01-01
 */
public class JavaFxInitializer extends Application {

  private final static Object barrier = new Object();
  @Getter
  private static boolean launched = false;
  @Getter
  private static boolean enable = true;

  /**
   * Launch javafx application.
   */
  public static void initialize() {
    if (GraphicsEnvironment.isHeadless()) {
      // non gui mode
      enable = false;
      return;
    }
    Thread t = new Thread("JavaFX Init Thread") {
      public void run() {
        launched = true;
        Application.launch(JavaFxInitializer.class, new String[0]);
      }
    };
    t.setDaemon(true);
    t.start();
    synchronized (barrier) {
      try {
        barrier.wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    if (GraphicsEnvironment.isHeadless()) {
      // non gui mode
      enable = false;
      return;
    }
    synchronized (barrier) {
      barrier.notify();
    }
  }
}
