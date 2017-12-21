package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 * @see Mast
 *
 * Ship consisting of masts.
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Ship {
    @Getter(AccessLevel.PACKAGE)
    private final Set<Mast> masts;
    /**
     * Static factory method.
     *
     * @param masts
     * @return Ship
     */
    public static Ship ofMasts(Mast... masts) {
        return new Ship(new HashSet<>(Arrays.asList(masts)));
    }
}
