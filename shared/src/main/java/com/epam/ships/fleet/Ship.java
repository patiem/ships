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
 * Ship consisting of masts.
 * @author Piotr Czyż, Sandor Korotkevics
 * @see Mast
 * @since 2017-12-19
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
   * @param masts - masts of ship
   * @return Ship
   */
  public static Ship ofMasts(Mast... masts) {
    return new Ship(new HashSet<>(Arrays.asList(masts)));
  }
}
