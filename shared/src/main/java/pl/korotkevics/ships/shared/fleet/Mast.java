package pl.korotkevics.ships.shared.fleet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Mast of a ship.
 * @author Piotr Czyż, Sandor Korotkevics
 * @see Ship
 * @since 2017-12-19
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mast {
  private String index;
  
  public static Mast ofIndex(String index) {
    return new Mast(index);
  }
  
  @Override
  public String toString() {
    return this.index;
  }
}
