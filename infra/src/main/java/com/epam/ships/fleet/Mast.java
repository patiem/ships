package com.epam.ships.fleet;

import lombok.*;

/**
 * @author Piotr, Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Mast {
    private int index;

    public static Mast ofIndex(int index) {
        return new Mast(index);
    }
}
