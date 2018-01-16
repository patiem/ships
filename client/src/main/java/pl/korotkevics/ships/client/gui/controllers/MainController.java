package pl.korotkevics.ships.client.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.Getter;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.gui.util.LocalizationHandler;
import pl.korotkevics.ships.client.localization.OurLocale;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.util.Locale;

import static pl.korotkevics.ships.client.gui.util.LocalizationHandler.*;

/**
 * Main window (containing title and similar) controller.
 *
 * @author Magdalena Aarsman, Sandor Korotkevics
 * @since 2017-12-14
 */

public class MainController {
  
  private static final Target logger = new SharedLogger(Client.class);
  
  @FXML
  private Pane mainPane;
  
  @Getter
  private Client client;
  
  @FXML
  private Button polishButton;
  
  @FXML
  private Button englishButton;
  
  /**
   * Initialize main controller.
   *
   * @param client
   *     - Instance of Client, which communicate with server.
   */
  @FXML
  public void initialize(final Client client) {
    this.client = client;
    this.loadDefaultView();
  }
  
  private void loadDefaultView() {
    this.reloadView();
  }
  
  private void reloadView() {
    final FXMLLoader fxmlLoader = this.prepareFxmlLoader();
    try {
      this.addFxmlLoaderToMainPane(fxmlLoader.load());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
  
  private FXMLLoader prepareFxmlLoader() {
    final String url = "/fxml/connectWindow.fxml";
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
    return enrichFxmlLoader(fxmlLoader);
  }
  
  private void addFxmlLoaderToMainPane(final Parent parent) {
    this.mainPane.getChildren().clear();
    this.mainPane.getChildren().add(parent);
  }
  
  @FXML
  void triggerPolishVersion(final ActionEvent event) {
    logger.info("The language is set to " + OurLocale.POLISH);
    Locale.setDefault(new Locale(OurLocale.POLISH.toString()));
    this.reloadView();
  }
  
  @FXML
  void triggerEnglishVersion(final ActionEvent event) {
    logger.info("The language is set to " + OurLocale.ENGLISH);
    Locale.setDefault(new Locale(OurLocale.ENGLISH.toString()));
    this.reloadView();
  }
  
  void disableLocalizationButtons() {
    this.hidePolishButton();
    this.hideEnglishButton();
  }
  
  private void hidePolishButton() {
    this.polishButton.setDisable(true);
    this.polishButton.setVisible(false);
  }
  
  private void hideEnglishButton() {
    this.englishButton.setDisable(true);
    this.englishButton.setVisible(false);
  }
}
