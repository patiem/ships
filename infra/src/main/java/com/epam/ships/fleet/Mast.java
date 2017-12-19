package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Piotr, Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mast {
    private int index;

    @Getter
    private boolean isHit;

    public static Mast ofIndex(int index) {
        return new Mast(index, false);
    }

    public void markHit() {
        this.isHit = true;
    }
}
