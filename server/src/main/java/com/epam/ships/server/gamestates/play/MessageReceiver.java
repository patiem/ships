package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;

public class MessageReceiver {
  private CommunicationBus communicationBus;
  private final Target logger = new SharedLogger(MessageReceiver.class);
  private Message lastMessage;

  public MessageReceiver(CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  void receive(WrappedClient player) {
    lastMessage = communicationBus.receive(player);
    logger.info(lastMessage);
  }

  public boolean isAShot() {
    return Header.SHOT.equals(lastMessage.getHeader());
  }

  public Message getMessage() {
    return lastMessage;
  }
}
