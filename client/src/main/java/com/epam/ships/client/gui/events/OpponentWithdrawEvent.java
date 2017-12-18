package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class OpponentWithdrawEvent extends Event {
    public static final EventType<OpponentWithdrawEvent> OPPONENT_WITHDRAW =
            new EventType<>(Event.ANY, "OPPONENT_WITHDRAW");

    public OpponentWithdrawEvent() {
        super(OPPONENT_WITHDRAW);
    }
}
