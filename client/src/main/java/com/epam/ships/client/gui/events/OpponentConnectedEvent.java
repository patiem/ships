package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class OpponentConnectedEvent extends Event{
    public static final EventType<OpponentConnectedEvent> OPPONENT_CONNECTED =
            new EventType<>(Event.ANY, "OPPONENT_CONNECTED");

    public OpponentConnectedEvent() {
        super(OPPONENT_CONNECTED);
    }
}
