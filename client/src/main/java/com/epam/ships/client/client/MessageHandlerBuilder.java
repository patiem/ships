package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.message.Header;

import java.util.EnumMap;
import java.util.Map;

public class MessageHandlerBuilder {
  private Map<Header, EventTrigger> triggers;

  public MessageHandlerBuilder withEnumMap() {
    triggers = new EnumMap<>(Header.class);
    return this;
  }

  public MessageHandlerBuilder withDefouldSetsOfTriggers() {
    triggers.put(Header.OPPONENT_CONNECTED, new OpponentConnectedTrigger());
    triggers.put(Header.SHOT, new OpponentShotTrigger());
    triggers.put(Header.CONNECTION, new ConnectionEndTrigger());
    triggers.put(Header.YOUR_TURN, new TurnTrigger());
    triggers.put(Header.MISS, new MissShotTrigger());
    triggers.put(Header.HIT, new HitShotTrigger());
    triggers.put(Header.SHIP_DESTRUCTED, new HitShotTrigger());
    triggers.put(Header.WIN, new WinTrigger());
    triggers.put(Header.LOSE, new LoseTrigger());
    return this;
  }

  public MessageHandler build() {
    return new MessageHandler(triggers);
  }
}
