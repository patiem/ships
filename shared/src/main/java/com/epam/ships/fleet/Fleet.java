package com.epam.ships.fleet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 * @see Ship
 *
 * Fleet of ships.
 */
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "fleet", access = AccessLevel.PRIVATE)
public class Fleet {

    Fleet() {
        this.fleet = Collections.emptyMap();
    }
    
    private final Map<Mast, Ship> fleet;

    /**
     * Static factory method.
     * @param ships
     * @return Fleet
     */
    public static Fleet ofShips(Ship... ships) {
        Map<Mast, Ship> fleet = new HashMap<>();
        Arrays.stream(ships).forEach(ship -> ship.getMasts().forEach(mast -> fleet.put(mast, ship)));
        return new Fleet(fleet);
    }

    /**
     *
     * @return true if a fleet is defeated.
     */
    public boolean isDefeated() {
        return this.fleet.isEmpty();
    }

    /**
     * It handles an attack
     * returning an output of such.
     *
     * @param mast attacked
     * @return Damage caused
     */
    public Damage handleDamage(Mast mast) {
        if (!this.fleet.containsKey(mast)) {
            return Damage.MISSED;
        }
        Ship shipHit = this.fleet.get(mast);
        this.fleet.remove(mast);
        if (!this.fleet.containsValue(shipHit)) {
            return Damage.DESTRUCTED;
        }
        return Damage.HIT;
    }
}