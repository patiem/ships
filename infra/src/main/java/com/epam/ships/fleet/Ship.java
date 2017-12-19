package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ship {
    private final Set<Mast> masts;

    public static Ship ofMasts(Mast... masts) {
        return new Ship(new HashSet<>(Arrays.asList(masts)));
    }
}
