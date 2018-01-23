package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.HitShotEvent;
import pl.korotkevics.ships.client.gui.events.LossEvent;
import pl.korotkevics.ships.client.gui.events.MissShotEvent;
import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import pl.korotkevics.ships.client.gui.events.OpponentWithdrawEvent;
import pl.korotkevics.ships.client.gui.events.ShipDestroyedEvent;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.client.gui.events.WinEvent;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;

import java.util.EnumMap;
import java.util.Map;

/**
 * Helper class to build MessageHandler.
 *
 * @author Magdalena Aarsman
 * @since 2018-01-02
 */

public class MessageHandlerBuilder {
  private Map<Header, EventTrigger> triggers;

  /**
   * Set triggers map to be enum map with Header as key.
   *
   * @return MessageHandlerBuilder
   */
  public MessageHandlerBuilder withEnumMap() {
    triggers = new EnumMap<>(Header.class);
    return this;
  }

  /**
   * Set default set of triggers and put it into map.
   *
   * @return MessageHandlerBuilder
   */
  public MessageHandlerBuilder withDefaultSetsOfTriggers() {
    triggers.put(Header.OPPONENT_CONNECTED, new OpponentConnectedTrigger(new OpponentConnectedEvent()));
    triggers.put(Header.SHOT, new OpponentShotTrigger());
    triggers.put(Header.CONNECTION, new ConnectionEndTrigger(new OpponentWithdrawEvent()));
    triggers.put(Header.YOUR_TURN, new TurnTrigger(new TurnChangeEvent()));
    triggers.put(Header.MISS, new MissShotTrigger(new MissShotEvent()));
    triggers.put(Header.HIT, new HitShotTrigger(new HitShotEvent()));
    triggers.put(Header.SHIP_DESTROYED, new ShipDestroyedTrigger(new ShipDestroyedEvent()));
    triggers.put(Header.WIN, new WinTrigger(new WinEvent()));
    triggers.put(Header.LOSE, new LoseTrigger(new LossEvent()));
    triggers.put(Header.RANDOM_PLACEMENT, new RandomPlacementTrigger());
    return this;
  }

  /**
   * Build MessageHandler.
   *
   * @return MessageHandler
   */
  public MessageHandler build() {
    return new MessageHandler(triggers, new DispatcherAdapter());
  }
}
