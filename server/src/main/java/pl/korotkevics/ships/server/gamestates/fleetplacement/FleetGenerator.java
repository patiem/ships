package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generates Fleet of ships.
 *
 * @author Piotr Czyż
 * @since 2018-01-10
 */
class FleetGenerator {

  private static final int BOARD_WIDTH = 10;
  private static final int FOUR_MAST_SHIP = 4;
  private static final int THREE_MAST_SHIP = 3;
  private static final int TWO_MAST_SHIP = 2;
  private static final int ONE_MAST_SHIP = 1;
  private static final List<Integer> SHIPS_TO_PLACE = Arrays.asList(FOUR_MAST_SHIP, THREE_MAST_SHIP,
      THREE_MAST_SHIP, TWO_MAST_SHIP, TWO_MAST_SHIP, TWO_MAST_SHIP, ONE_MAST_SHIP, ONE_MAST_SHIP,
      ONE_MAST_SHIP, ONE_MAST_SHIP);
  private Map<Integer, FieldState> gameBoard;
  private static final int FIRST_FIELD_INDEX = 0;
  private static final int LAST_FIELD_INDEX = 100;

  FleetGenerator() {
    this.gameBoard = new HashMap<>();
  }

  Fleet generateFleet() {
    this.fillGameBoard();
    final List<Ship> ships = new ArrayList<>();
    SHIPS_TO_PLACE.forEach(i -> ships.add(this.generateShip(i)));
    return Fleet.ofShips(ships);
  }

  private void fillGameBoard() {
    IntStream.range(FIRST_FIELD_INDEX, LAST_FIELD_INDEX)
        .forEach(i -> gameBoard.put(i, FieldState.EMPTY));
  }

  private Ship generateShip(final int shipLength) {
    List<Integer> offeredShip = this.offerShipAsIndices(shipLength);
    if (this.canBePut(offeredShip)) {
      this.markAreaAsOccupied(offeredShip);
      return Ship.ofMasts(this.convertToMasts(offeredShip));
    }
    return this.generateShip(shipLength);
  }

  private List<Integer> offerShipAsIndices(final int shipLength) {
    final List<Integer> shipAsIndices;
    boolean verticalOrientation = this.isVerticalOrientation();
    if (verticalOrientation) {
      shipAsIndices = this.offerIndices(shipLength, this.generateStartIndex(), BOARD_WIDTH);
      if (!this.isWithinBoardVertical(shipAsIndices)) {
        return this.offerShipAsIndices(shipLength);
      }
    } else {
      shipAsIndices = this.offerIndices(shipLength, this.generateStartIndex(), 1);
      if (!this.isWithinBoardAndSingleRow(shipAsIndices)) {
        return this.offerShipAsIndices(shipLength);
      }
    }
    return shipAsIndices;
  }

  private List<Integer> offerIndices(final int length, final int startIndex, final int step) {
    final List<Integer> indices = new ArrayList<>();
    IntStream.range(0, length)
        .forEach(i -> indices.add(startIndex + i * step));
    return indices;
  }

  private Mast[] convertToMasts(final List<Integer> offeredShip) {
    return offeredShip
        .stream()
        .map(obj -> Mast.ofIndex(String.valueOf(obj)))
        .collect(Collectors.toList())
        .toArray(new Mast[offeredShip.size()]);
  }

  private void markAreaAsOccupied(final List<Integer> offeredShip) {
    Set<Integer> areaToMark = this.determineAreaForShip(offeredShip);
    areaToMark
        .stream()
        .filter(i -> gameBoard.containsKey(i))
        .forEach(i -> gameBoard.put(i, FieldState.OCCUPIED));
  }

  private boolean isWithinBoardAndSingleRow(final List<Integer> offeredShip) {
    final int rowNumberOfFirstShipIndex = offeredShip.get(0) / BOARD_WIDTH;
    return offeredShip
        .stream()
        .allMatch(i -> i < 100 && (i / BOARD_WIDTH == rowNumberOfFirstShipIndex));
  }

  private boolean isWithinBoardVertical(final List<Integer> offeredShip) {
    return offeredShip
        .stream()
        .allMatch(i -> i > 0 && i < 100);
  }

  private boolean canBePut(final List<Integer> offeredShip) {
    return offeredShip
        .stream()
        .allMatch(i -> FieldState.EMPTY.equals(gameBoard.get(i)));
  }

  private Set<Integer> determineAreaForShip(final List<Integer> offeredShip) {
    Set<Integer> area = new HashSet<>();
    offeredShip.forEach(index -> area.addAll(this.addFieldAndNeighbours(index)));
    return area;
  }

  private List<Integer> addFieldAndNeighbours(final int index) {
    List<Integer> area = new ArrayList<>();
    List<Integer> neighbours = Arrays.asList(-11, -10, -9, 0, -1, 1, 9, 10, 11);
    neighbours.forEach(i -> area.add(index + i));
    return area;
  }

  private int generateStartIndex() {
    List<Integer> emptyIndices = gameBoard
        .entrySet()
        .stream()
        .filter(i -> FieldState.EMPTY.equals(i.getValue()))
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    int index = new Random().nextInt(emptyIndices.size());
    return emptyIndices.get(index);
  }

  private boolean isVerticalOrientation() {
    return new Random().nextBoolean();
  }

}
