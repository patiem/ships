package pl.korotkevics.ships.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Server Not Responding window controller.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */

public class ServerNotRespondingController implements Initializable {

  private static final Target logger = new SharedLogger(Client.class);

  @FXML
  private AnchorPane mainAnchorPane;
  private ResourceBundle resourceBundle;

  @FXML
  private void onTryAgainPressed() {
    try {
      final String connectWindowUrl = "/fxml/connectWindow.fxml";
      final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowUrl));
      connectLoader.setResources(this.resourceBundle);
      final Parent connectWindow = connectLoader.load();
      final Pane mainPane = (Pane) mainAnchorPane.getParent();
      mainPane.getChildren().clear();
      mainPane.getChildren().setAll(connectWindow);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    this.resourceBundle = resources;
  }
}
