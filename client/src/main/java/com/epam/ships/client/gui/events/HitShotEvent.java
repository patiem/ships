package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class HitShotEvent extends Event {
    public static final EventType<HitShotEvent> HIT_SHOT =
            new EventType<>(Event.ANY, "HIT_SHOT");

    public HitShotEvent() {
        super(HIT_SHOT);
    }
}
