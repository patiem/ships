package com.epam.ships.client.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxInitializer extends Application {

  private static Object barrier = new Object();

  @Override
  public void start(Stage primaryStage) throws Exception {
    synchronized(barrier) {
      barrier.notify();
    }
  }

  public static void initialize() throws InterruptedException {
    Thread t = new Thread("JavaFX Init Thread") {
      public void run() {
        Application.launch(JavaFxInitializer.class, new String[0]);
      }
    };
    t.setDaemon(true);
    t.start();
    synchronized(barrier) {
      barrier.wait();
    }
  }
}
