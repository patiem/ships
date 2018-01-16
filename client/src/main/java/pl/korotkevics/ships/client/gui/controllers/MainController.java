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
  
  private ResourceBundle resourceBundle;
  
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
    this.prepareLocalizationButtons();
    this.loadDefaultView();
  }
  
  @FXML
  void triggerPolishVersion(final ActionEvent event) {
    this.reloadView(Locale.POLISH);
  }
  
  @FXML
  void triggerEnglishVersion(final ActionEvent event) {
    this.reloadView(Locale.ENGLISH);
  }
  
  private void loadDefaultView() {
    this.reloadView(Locale.ENGLISH);
  }
  
  private void reloadView(final Locale locale) {
    final FXMLLoader fxmlLoader = this.prepareFxmlLoader(locale);
    try {
      this.addFxmlLoaderToMainPane(fxmlLoader.load());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
  
  private void prepareLocalizationButtons() {
    this.prepareEnglishLocalizationButton();
    this.preparePolishLocalizationButton();
  }
  
  private void prepareEnglishLocalizationButton() {
    this.englishButton.setOnMouseClicked(e -> this.resourceBundle = ResourceBundle.getBundle
                                                                                       (Locale
                                                                                            .ENGLISH.toString()));
  }
  
  private void preparePolishLocalizationButton() {
    this.polishButton.setOnMouseClicked(e -> this.resourceBundle = ResourceBundle.getBundle
                                                                                      (Locale
                                                                                           .POLISH.toString()));
  }
  
  private FXMLLoader prepareFxmlLoader(final Locale locale) {
    final String url = "/fxml/connectWindow.fxml";
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
    return this.addResourceBundleToFxmlLoader(fxmlLoader, locale);
  }
  
  private FXMLLoader addResourceBundleToFxmlLoader(final FXMLLoader fxmlLoader, final Locale
                                                                                    locale) {
    this.resourceBundle = ResourceBundle.getBundle(locale.toString());
    fxmlLoader.setResources(this.resourceBundle);
    return fxmlLoader;
  }
  
  private void addFxmlLoaderToMainPane(final Parent parent) {
    this.mainPane.getChildren().clear();
    this.mainPane.getChildren().add(parent);
  }
  
  void disableLocalizationButtons() {
    this.hidePolishButton();
    this.hideEnglishButton();
  }
  
  private void hideEnglishButton() {
    this.englishButton.setDisable(true);
    this.englishButton.setVisible(false);
  }
  
  private void hidePolishButton() {
    this.polishButton.setDisable(true);
    this.polishButton.setVisible(false);
  }
}
