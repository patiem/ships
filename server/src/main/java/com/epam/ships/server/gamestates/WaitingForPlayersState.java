package com.epam.ships.server.gamestates;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;

import java.io.IOException;

public class WaitingForPlayersState implements GameState {
  private final Target logger = new SharedLogger(WaitingForPlayersState.class);

  @Override
  public GameState process() {
    CommunicationBus communicationBus = null;
    try {
      communicationBus = new CommunicationBus();
      communicationBus.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(Header.OPPONENT_CONNECTED);

    return new FleetPlacementState(communicationBus);
  }
}
