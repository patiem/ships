package pl.korotkevics.ships.client.client;

import javafx.scene.control.Button;
import lombok.Getter;
import pl.korotkevics.ships.client.reporting.Reporter;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

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
  private Reporter reporter;
  private Button eventButton;
  @Getter
  private boolean endConnectionTriggered;

  /**
   * Creates an instance of MessageHandler and configures all possible triggers types.
   */
  MessageHandler(final Map<Header, EventTrigger> triggerMap, final Reporter reporter) {
    this.triggers = triggerMap;
    this.reporter = reporter;
  }

  Button getCurrentEventButton() {
    return eventButton;
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
    triggers.get(header).fire(eventButton, message);
    new Thread(()-> {
    if (this.reporter!=null) {
        this.reporter.report(triggers.get(header).provideDescription());
      }
    }).start();
    
  }

  private void checkIfEndWillBeTriggered(final Message message) {
    if (Status.END.equals(message.getStatus())
        || Header.WIN.equals(message.getHeader())
        || Header.LOSE.equals(message.getHeader())) {
      endConnectionTriggered = true;
    }
  }
}
