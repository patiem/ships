package com.epam.ships.fleet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
//@RequiredArgsConstructor(staticName = "fleet", access = AccessLevel.PRIVATE)
public class Fleet {
    
    Fleet(final Map<Mast, Ship> fleet) {
        this.fleet = fleet;
    }
    
    Fleet() {
        this.fleet = Collections.emptyMap();
    }
    
    private final Map<Mast, Ship> fleet;
    
    public static Fleet ofShips(Ship... ships) {
        Map<Mast, Ship> fleet = new HashMap<>();
        Arrays.stream(ships).forEach(ship -> ship.getMasts().forEach(mast -> fleet.put(mast, ship)));
        return new Fleet(fleet);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.fleet.isEmpty();
    }
    
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