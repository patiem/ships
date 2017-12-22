package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class WinEvent extends Event {
    public static final EventType<WinEvent> GAME_WIN =
            new EventType<>(Event.ANY, "GAME_WIN");

    public WinEvent() {
        super(GAME_WIN);
    }
}
