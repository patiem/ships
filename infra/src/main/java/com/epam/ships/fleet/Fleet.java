package com.epam.ships.fleet;

import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Fleet {
    private final BiMap<Mast, Ship> fleet;

    public static Fleet ofShips(Ship... ships) {
        
    }

}