package com.epam.ships.fleet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Piotr,Sandor
 * @since 2017-12-19
 */
class ShipBuilder {

    private Set<Index> indices = new HashSet<>();

    Ship build() {
        return new Ship(indices);
    }

    ShipBuilder withIndices(Index... index) {
        this.indices.addAll(Arrays.asList(index));
        return this;
    }
}
