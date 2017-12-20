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
    private String index;

    public static Mast ofIndex(String index) {
        return new Mast(index);
    }
}
