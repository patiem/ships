package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when opponent withdraw.
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class OpponentWithdrawEvent extends Event {
  public static final EventType<OpponentWithdrawEvent> OPPONENT_WITHDRAW =
      new EventType<>(Event.ANY, "OPPONENT_WITHDRAW");

  public OpponentWithdrawEvent() {
    super(OPPONENT_WITHDRAW);
  }
}
