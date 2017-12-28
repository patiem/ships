package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Magda
 * @since 2017-12-16
 */

class MessageHandler {

  private static final Target logger = new SharedLogger(Client.class);
  private final Map<Header, EventTrigger> triggers;
  private Button eventButton = null;
  @Getter
  private boolean endConnectionTriggered;

  MessageHandler() {
    endConnectionTriggered = false;
    this.triggers = new HashMap<>();
    this.triggers.put(Header.OPPONENT_CONNECTED, new OpponentConnectedTrigger());
    this.triggers.put(Header.SHOT, new OpponentShotTrigger());
    this.triggers.put(Header.CONNECTION, new ConnectionEndTrigger());
    this.triggers.put(Header.YOUR_TURN, new TurnTrigger());
    this.triggers.put(Header.MISS, new MissShotTrigger());
    this.triggers.put(Header.HIT, new HitShotTrigger());
    this.triggers.put(Header.SHIP_DESTRUCTED, new HitShotTrigger());
    this.triggers.put(Header.WIN, new WinTrigger());
    this.triggers.put(Header.LOSE, new LoseTrigger());
  }

  void setCurrentEventButton(Button eventButton) {
    this.eventButton = eventButton;
  }

  void handle(Message message) {
    if (eventButton == null) {
      throw new IllegalStateException("there is no object on which there can be fire event on");
    }

    Header header = message.getHeader();

    if (!triggers.containsKey(header)) {
      logger.error("message header: " + header + " is unknown");
      return;
    }
    checkIfEndWillBeTriggered(message);
    triggers.get(header).fire(eventButton, message.getStatement());
  }

  private void checkIfEndWillBeTriggered(Message message) {
    if (Status.END.equals(message.getStatus())) {
      endConnectionTriggered = true;
    }
  }
}
