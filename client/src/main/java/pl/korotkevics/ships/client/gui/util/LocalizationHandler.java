package pl.korotkevics.ships.client.gui.util;

import javafx.fxml.FXMLLoader;

import java.util.ResourceBundle;

/**
 * A utility allowing to work with localizations.
 *
 * @author Sandor Korotkevics
 * @since 2018-01-16
 */
public class LocalizationHandler {
  
  private LocalizationHandler() {}
  
  private static final String DICTIONARY = "dict";
  
  /**
   * It enriches a given FXMLLoader instance with active ResourceBundle.
   *
   * @param fxmlLoader
   *     to be enriched.
   *
   * @return enriched FXMLLoader.
   */
  public static FXMLLoader enrichFxmlLoader(final FXMLLoader fxmlLoader) {
    fxmlLoader.setResources(resolveActiveResourceBundle());
    return fxmlLoader;
  }
  
  /**
   * It returns a valid translation for a given localization key.
   *
   * @param key
   *     of a localization.
   *
   * @return a valid translation.
   */
  public static String resolveLocalization(final String key) {
    return resolveActiveResourceBundle().getString(key);
  }
  
  private static ResourceBundle resolveActiveResourceBundle() {
    return ResourceBundle.getBundle(DICTIONARY);
  }
}
