package com.epam.ships.fleet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor
class Ship {
    private final Set<Index> indices;
}
