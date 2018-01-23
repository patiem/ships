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
  
  private static final String DESTINATION = "destination";
  
  private static final String TARGET = "target";
  
  private static final String OPTIONS = "options";
  
  private static final String FILE = "file";
  
  private static final String NAME = "name";
  
  private static final String SOCKET = "socket";
  
  private static final String HOST = "host";
  
  private static final String PORT = "port";
  
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
  
  public void report(final String message) {
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
    }
  }
  
  String getDestinationHost() {
    return this.config.getString(OPTIONS + PATH_SEPARATOR + SOCKET + PATH_SEPARATOR + HOST);
  }
  
  int getDestinationPort() {
    return this.config.getInt(OPTIONS + PATH_SEPARATOR + SOCKET + PATH_SEPARATOR + PORT);
  }
}
