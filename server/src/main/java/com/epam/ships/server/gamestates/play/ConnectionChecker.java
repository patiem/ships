package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;

class ConnectionChecker {

  boolean isConnected(final Message messageReceived) {
    boolean isClientConnected = true;
    if (Header.CONNECTION.equals(messageReceived.getHeader())
        && Status.END.equals(messageReceived.getStatus())) {
      isClientConnected = false;
    }
    return isClientConnected;
  }
}
