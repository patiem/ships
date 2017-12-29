package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Piotr, Sandor
 * @see Ship
 * <p>
 * Mast of a ship.
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
