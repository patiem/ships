package pl.korotkevics.ships.client.reporting;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

class Reporter {
  
  private static final Target logger = new SharedLogger(Reporter.class);
  
  private static final String PATH_SEPARATOR = ".";
  
  private static final String TARGET = "target";
  
  private static final String OPTIONS = "options";
  
  private static final String DESTINATION = "destination";
  
  private static final String LOGGER_OPTION = "logger";
  
  private static final String SOCKET_OPTION = "socket";
  
  private static final String FILE_OPTION = "file";
  
  private static final String WINDOW_OPTION = "window";
  
  private static final String NAME = "name";
  
  private static final String HOST = "host";
  
  private static final String PORT = "port";
  
  private static final String TITLE = "title";
  
  private static final String HEIGHT = "height";
  
  private static final String WIDTH = "width";
  
  private final Config config;
  
  public void report(final String message) {
    //TODO refactor to Visitor or a map based switch
    if (this.getCurrentDestination().equals(ReportingOption.FILE.toString())) {
      try {
        Files.write(Paths.get(this.getDestinationFileName()), StringUtils.defaultIfEmpty(message,
            StringUtils.EMPTY).getBytes());
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    } else if (this.getCurrentDestination().equals(ReportingOption.SOCKET.toString())) {
      try (Socket socket = new Socket()) {
        final InetAddress inetAddress = InetAddress.getByName(this.getDestinationHost());
        socket.connect(new InetSocketAddress(inetAddress, getDestinationPort()));
        socket.getOutputStream().write(message.getBytes());
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    } else if (this.getCurrentDestination().equals(ReportingOption.LOGGER.toString())) {
      logger.report(message, this.getDestinationLoggerName());
    }
  }
  
  Reporter(final String fileName) {
    this.config = ConfigFactory.load(fileName);
  }
  
  String getCurrentDestination() {
    return this.config.getString(DESTINATION + PATH_SEPARATOR + TARGET);
  }
  
  String getDestinationFileName() {
    return this.config.getString(this.getOptionsPath() + FILE_OPTION + PATH_SEPARATOR + NAME);
  }
  
  String getDestinationHost() {
    return this.config.getString(this.getOptionsPath() + SOCKET_OPTION + PATH_SEPARATOR + HOST);
  }
  
  int getDestinationPort() {
    return this.config.getInt(this.getOptionsPath() + SOCKET_OPTION + PATH_SEPARATOR + PORT);
  }
  
  String getDestinationLoggerName() {
    return this.config.getString(this.getOptionsPath() + LOGGER_OPTION + PATH_SEPARATOR + NAME);
  }
  
  String getDestinationWindowTitle() {
    return this.config.getString(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + TITLE);
  }
  
  int getDestinationWindowHeight() {
    return this.config.getInt(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + HEIGHT);
  }
  
  int getDestinationWindowWidth() {
    return this.config.getInt(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + WIDTH);
  }
  
  private String getOptionsPath() {
    return OPTIONS + PATH_SEPARATOR;
  }
}
