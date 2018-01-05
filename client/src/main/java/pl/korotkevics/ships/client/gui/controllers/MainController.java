package pl.korotkevics.ships.client.gui.controllers;

import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.io.IOException;

/**
 * Main window, which contains title etc. controller.
 * @author Magdalena Aarsman
 * @since 2017-12-14
 */

public class MainController {

  private static final Target logger = new SharedLogger(Client.class);

  @FXML
  private Pane mainPane;

  @Getter
  private Client client;

  /**
   * Initialize main controller.
   * @param client - Instance of Client, which communicate with server.
   */
  @FXML
  public void initialize(final Client client) {
    final String connectWindowUrl = "/fxml/connectWindow.fxml";
    this.client = client;

    try {
      final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowUrl));
      final Parent connect = connectLoader.load();

      mainPane.getChildren().clear();
      mainPane.getChildren().add(connect);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
