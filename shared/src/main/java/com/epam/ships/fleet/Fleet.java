package com.epam.ships.fleet;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Piotr, Sandor
 * @see Ship
 * <p>
 * Fleet of ships.
 * @since 2017-12-19
 */
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "fleet", access = AccessLevel.PRIVATE)
@ToString
public class Fleet {

  private final Map<Mast, Ship> fleet;

  private Fleet() {
    this.fleet = Collections.emptyMap();
  }

  /**
   * Static factory method.
   *
   * @param ships
   * @return Fleet
   */
  public static Fleet ofShips(List<Ship> ships) {
    Map<Mast, Ship> fleet = new HashMap<>();
    ships.forEach(ship -> ship.getMasts().forEach(mast -> fleet.put(mast, ship)));
    return new Fleet(fleet);
  }

  public static Fleet empty() {
    return new Fleet();
  }

  /**
   * @return true if a fleet is defeated.
   */
  public boolean isDefeated() {
    return this.fleet.isEmpty();
  }

  /**
   * It handles an attack
   * returning an output of such.
   *
   * @param mast attacked
   * @return Damage caused
   */
  public Damage handleDamage(Mast mast) {
    if (!this.fleet.containsKey(mast)) {
      return Damage.MISSED;
    }
    Ship shipHit = this.fleet.get(mast);
    this.fleet.remove(mast);
    if (!this.fleet.containsValue(shipHit)) {
      return Damage.DESTRUCTED;
    }
    return Damage.HIT;
  }
}