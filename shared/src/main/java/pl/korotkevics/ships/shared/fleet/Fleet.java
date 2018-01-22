package pl.korotkevics.ships.shared.fleet;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fleet of ships.
 *
 * @author Piotr Czyż, Sandor Korotkevics
 * @see Ship
 * @since 2017-12-19
 */
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "fleet", access = AccessLevel.PRIVATE)
@ToString
public class Fleet implements Serializable {

  private final Map<Mast, Ship> fleet;

  private Fleet() {
    this.fleet = Collections.emptyMap();
  }

  /**
   * Static factory method.
   *
   * @param ships - list of ships.
   * @return Fleet.
   */
  public static Fleet ofShips(final List<Ship> ships) {
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
   * Handles an attack
   * returning an output of such.
   *
   * @param mast attacked.
   * @return Damage caused.
   */
  public Damage handleDamage(final Mast mast) {
    if (!this.fleet.containsKey(mast)) {
      return Damage.MISSED;
    }
    Ship shipHit = this.fleet.get(mast);
    this.fleet.remove(mast);
    if (!this.fleet.containsValue(shipHit)) {
      return Damage.DESTROYED;
    }
    return Damage.HIT;
  }

  /**
   * Produces a list representation of Fleet.
   *
   * @return a list containing indices of Masts only.
   */
  public List<Integer> toIntegerList() {
    final List<Integer> list = new ArrayList<>();
    this.fleet.forEach((k, v) -> list.add(Integer.valueOf(k.toString())));
    return list;
  }
}