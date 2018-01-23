package pl.korotkevics.ships.client.reporting;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

class Reporter {
  
  private static final String PATH_SEPARATOR = ".";
  
  private static final String DESTINATION = "destination";
  
  private static final String TARGET = "target";
  
  private static final String OPTIONS = "options";
  
  private static final String FILE = "file";
  
  private static final String NAME = "name";
  
  private final Config config;
  
  Reporter(final String fileName) {
    this.config = ConfigFactory.load(fileName);
  }
  
  String getCurrentDestination() {
    return this.config.getString(DESTINATION + PATH_SEPARATOR + TARGET);
  }
  
  String getDestinationFileName() {
    return this.config.getString(OPTIONS + PATH_SEPARATOR + FILE + PATH_SEPARATOR + NAME);
  }
}
