package com.epam.ships.client.validators;

/**
 * @author Magda
 * @since 2017-12-16
 */

public class PortValidator {

  public int asInt(String providedPort) {
    int port = Integer.valueOf(providedPort);
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
