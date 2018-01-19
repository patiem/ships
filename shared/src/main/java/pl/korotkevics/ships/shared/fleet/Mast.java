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
  
  /**
   * Creates a Mast based on an index value.
   *
   * @param index location of a mast within a game board grid.
   * @return Mast
   */
  public static Mast ofIndex(final String index) {
    return new Mast(index);
  }
  
  /**
   * Not a subject to change.
   * @return a String representation of Mast which is its actual index.
   */
  @Override
  public String toString() {
    return this.index;
  }
}
