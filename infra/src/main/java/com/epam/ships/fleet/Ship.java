package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
    @Getter
    private final Set<Mast> masts;

    public static Ship ofMasts(Mast... masts) {
        return new Ship(new HashSet<>(Arrays.asList(masts)));
    }

    public boolean isSank(){
        boolean result = true;
        for(Mast mast : masts){
            if(mast.isHit()) {
                result = false;
            }
        }
        return result;
    }
}
