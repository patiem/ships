package com.epam.ships.fleet;

import com.epam.ships.infra.communication.api.Attachable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "fleet", access = AccessLevel.PRIVATE)
public class Fleet implements Attachable {
    private final Map<Mast, Ship> fleet;

    public static Fleet ofShips(Ship... ships) {
        Map<Mast, Ship> fleet = new HashMap<>();
        Arrays.stream(ships).forEach(ship -> ship.getMasts().forEach(mast -> fleet.put(mast, ship)));
        return new Fleet(fleet);
    }

    public boolean isEmpty() {
        return this.fleet.isEmpty();
    }
}