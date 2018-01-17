package pl.korotkevics.ships.client.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Getter;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.localization.OurLocale;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Main window (containing title and similar) controller.
 *
 * @author Magdalena Aarsman, Sandor Korotkevics
 * @since 2017-12-14
 */

public class MainController {
  
  private static final Target logger = new SharedLogger(Client.class);
  
  private static final String DICTIONARY = "dict";
  
  @FXML
  private Label gameTitle;
  
  @FXML
  private Button russianButton;
  
  @FXML
  private Pane mainPane;
  
  @Getter
  private Client client;
  
  @FXML
  private Button polishButton;
  
  @FXML
  private Button englishButton;
  
  private ResourceBundle resourceBundle;
  
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
    this.gameTitle.setText(this.resourceBundle.getString("gameTitle"));
    try {
      this.addToMainPane(fxmlLoader.load());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
  
  private FXMLLoader prepareFxmlLoader() {
    final String url = "/fxml/connectWindow.fxml";
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
    this.resourceBundle = ResourceBundle.getBundle(DICTIONARY);
    fxmlLoader.setResources(this.resourceBundle);
    return fxmlLoader;
  }
  
  private void addToMainPane(final Parent parent) {
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
  
  @FXML
  void triggerRussianVersion(final ActionEvent event) {
    logger.info("The language is set to " + OurLocale.RUSSIAN);
    Locale.setDefault(new Locale(OurLocale.RUSSIAN.toString()));
    this.reloadView();
  }
  
  void disableLocalizationButtons() {
    this.hideButton(this.polishButton);
    this.hideButton(this.englishButton);
    this.hideButton(this.russianButton);
  }
  
  private void hideButton(Button button) {
    button.setDisable(true);
    button.setVisible(false);
  }
}
