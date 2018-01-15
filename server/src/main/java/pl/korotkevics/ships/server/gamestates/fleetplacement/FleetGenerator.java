package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;

import java.util.*;
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
  private Map<Integer, FieldState> gameBoard;

  FleetGenerator() {
    this.gameBoard = new HashMap<>();
    int firstFieldIndex = 0;
    int lastFieldIndex = 100;
    IntStream.range(firstFieldIndex, lastFieldIndex)
        .forEach(i -> gameBoard.put(i, FieldState.EMPTY));
  }

  Fleet generateFleet() {
    List<Integer> shipsToPlace = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
    List<Ship> ships = new ArrayList<>();
    shipsToPlace.forEach(i -> ships.add(this.generateShip(i)));
    return Fleet.ofShips(ships);
  }

  private Ship generateShip(final int shipLength) {
    List<Integer> offeredShip = this.offerShipAsIndices(shipLength);
    if (this.canBePut(offeredShip)) {
      this.markAreaAsOccupied(offeredShip);
      return Ship.ofMasts(this.convertToMasts(offeredShip));
    } else {
      return this.generateShip(shipLength);
    }
  }

  private List<Integer> offerShipAsIndices(final int shipLength) {
    int startIndex = this.generateStartIndex();
    List<Integer> shipAsIndices;
    boolean verticalOrientation = this.isVerticalOrientation();
    if (verticalOrientation) {
      shipAsIndices = this.offerIndices(shipLength, startIndex, BOARD_WIDTH);
      if (!isWithinBoardVertical(shipAsIndices)) {
        return offerShipAsIndices(shipLength);
      }
    } else {
      shipAsIndices = this.offerIndices(shipLength, startIndex, 1);
      if (!isWithinBoardHorizontal(shipAsIndices)) {
        return offerShipAsIndices(shipLength);
      }
    }
    System.out.println(shipAsIndices);
    return shipAsIndices;
  }

  private List<Integer> offerIndices(int length, int startIndex, int step) {
    List<Integer> indices = new ArrayList<>();
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

  private boolean isWithinBoardHorizontal(final List<Integer> offeredShip) {
    int firstShipIndex = offeredShip.get(0) / BOARD_WIDTH;
    return offeredShip
        .stream()
        .allMatch(i -> i < 100 && i / BOARD_WIDTH == firstShipIndex);
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
