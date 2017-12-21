package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Piotr, Sandor
 * @since 2017-12-19
 * @see Ship
 *
 * Mast of a ship.
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Mast {
    private String index;

    public static Mast ofIndex(String index) {
        return new Mast(index);
    }
}
