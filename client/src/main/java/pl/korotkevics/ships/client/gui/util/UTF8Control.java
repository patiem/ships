package pl.korotkevics.ships.client.gui.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Used when instantiating ResourceBundle in order
 * to enforce UTF-8 in UI (to make Polish, Russian
 * special characters appear properly).
 * <p>
 * <p> We're thankful to the author of this code.
 * The author copied the default implementation and
 * added "UTF-8" when instantiating InputStreamReader. </p>
 *
 * @author https://stackoverflow.com/questions/4659929/how-to-use-utf-8-in-resource-properties
 * -with-resourcebundle
 * @since 2011-01-11
 */

public class UTF8Control extends Control {
  
  private static final String ENCODING = "UTF-8";
  
  @Override
  public ResourceBundle newBundle(final String baseName, final Locale locale, final String
                                                                                    format, final
  ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
    // The below is a copy of the default implementation.
    String bundleName = toBundleName(baseName, locale);
    String resourceName = toResourceName(bundleName, "properties");
    ResourceBundle bundle = null;
    InputStream stream = null;
    if (reload) {
      URL url = loader.getResource(resourceName);
      if (url != null) {
        URLConnection connection = url.openConnection();
        if (connection != null) {
          connection.setUseCaches(false);
          stream = connection.getInputStream();
        }
      }
    } else {
      stream = loader.getResourceAsStream(resourceName);
    }
    if (stream != null) {
      try {
        // Only this line is changed to make it to read properties files as UTF-8.
        bundle = new PropertyResourceBundle(new InputStreamReader(stream, ENCODING));
      } finally {
        stream.close();
      }
    }
    return bundle;
  }
}
