package pl.korotkevics.ships.client.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.Getter;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.localization.Locale;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.util.ResourceBundle;

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

  private ResourceBundle resourceBundle;
  
  @FXML
  Button polishButton;
  
  @FXML
  Button englishButton;
  
  /**
   * Initialize main controller.
   * @param client - Instance of Client, which communicate with server.
   */
  @FXML
  public void initialize(final Client client) {
    this.client = client;
    this.prepareLocalizationButtons();
    this.loadView(Locale.ENGLISH);
  }
  
  @FXML
  void triggerPolishVersion(ActionEvent event) {
    this.loadView(Locale.POLISH);
  }
  
  @FXML
  void triggerEnglishVersion(ActionEvent event) {
    this.loadView(Locale.ENGLISH);
  }
  
  private void loadView(Locale locale) {
    final String connectWindowUrl = "/fxml/connectWindow.fxml";
    final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowUrl));
    this.resourceBundle = ResourceBundle.getBundle(locale.toString());
    connectLoader.setResources(this.resourceBundle);
    try {
      final Parent connect = connectLoader.load();
      mainPane.getChildren().clear();
      mainPane.getChildren().add(connect);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
  
  private void prepareLocalizationButtons() {
    this.englishButton.setOnMouseClicked(e -> this.resourceBundle = ResourceBundle.getBundle(Locale.ENGLISH.toString()));
    this.polishButton.setOnMouseClicked(e -> this.resourceBundle = ResourceBundle.getBundle(Locale.POLISH.toString()));
  }
}
