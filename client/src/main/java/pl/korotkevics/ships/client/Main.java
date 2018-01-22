package pl.korotkevics.ships.client;

import javafx.application.Application;
import pl.korotkevics.ships.client.gui.GuiMain;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Main class of client application.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-14
 */
public class Main {
  private static final Target logger = new SharedLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Client is up and running.");
    new Thread(() -> Application.launch(GuiMain.class)).start();
  }
}

