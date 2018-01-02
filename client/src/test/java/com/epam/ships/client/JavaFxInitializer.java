package com.epam.ships.client;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.awt.GraphicsEnvironment;

public class JavaFxInitializer extends Application {

  private final static Object barrier = new Object();
  @Getter
  private static boolean launched = false;
  @Getter
  private static boolean enable = true;

  @Override
  public void start(Stage primaryStage) throws Exception {
    if (GraphicsEnvironment.isHeadless()) {
      // non gui mode
      enable = false;
      return;
    }
    synchronized(barrier) {
      barrier.notify();
    }
  }

  public static void initialize() throws InterruptedException {
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
        barrier.wait();
      }
  }
}
