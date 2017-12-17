package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class OpponentConnectedEvent extends Event{
    public static final EventType<OpponentConnectedEvent> OPONENT_CONNECTED =
            new EventType<>(Event.ANY, "OPONENT_CONNECTED");

    public OpponentConnectedEvent() {
        super(OPONENT_CONNECTED);
    }
}
