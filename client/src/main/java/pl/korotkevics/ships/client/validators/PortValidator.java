package pl.korotkevics.ships.client.validators;

/**
 * Enables validation of a port number.
 * @author Magdalena Aarsman
 * @since 2017-12-16
 */
public class PortValidator {

  /**
   * Validate the port number, returns port as Integer when it is valid.
   * @param providedPort - port provided by user as a String
   * @return - port number
   * @throws IllegalArgumentException if port is invalid.
   */
  public int asInt(String providedPort) {
    int port = Integer.parseInt(providedPort);
    checkRange(port);
    return port;
  }

  private void checkRange(int port) {
    final int maxValidPort = 0xFFFF;
    final int minValidPort = 1024;
    if (port <= minValidPort || port > maxValidPort) {
      throw new IllegalArgumentException("port value is out of range");
    }
  }
}
