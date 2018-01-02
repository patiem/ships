package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;
import lombok.Getter;

import java.util.Map;

/**
 * Handling message, enable to react in proper way to server messages.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-16
 */
class MessageHandler {

  private static final Target logger = new SharedLogger(Client.class);
  private final Map<Header, EventTrigger> triggers;
  private Button eventButton;
  @Getter
  private boolean endConnectionTriggered;

  /**
   * Creates an instance of MessageHandler and configures all possible triggers types.
   */
  MessageHandler(final Map<Header, EventTrigger> triggerMap) {
    this.triggers = triggerMap;
  }

  void setCurrentEventButton(Button eventButton) {
    this.eventButton = eventButton;
  }

  Button getCurrentEventButton() {
    return eventButton;
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
