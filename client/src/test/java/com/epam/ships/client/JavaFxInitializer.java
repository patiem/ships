package com.epam.ships.client;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFxInitializer extends Application {

  private final static Object barrier = new Object();
  public static boolean launched = false;
  public static boolean enable = true;

  @Override
  public void start(Stage primaryStage) throws Exception {
    synchronized(barrier) {
      barrier.notify();
    }
  }

  public static void initialize() throws InterruptedException {
    if(Platform.isSupported(ConditionalFeature.GRAPHICS)) {
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
    } else {
      enable = false;
    }
  }
}
