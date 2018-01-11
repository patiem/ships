package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generates Fleet of ships.
 * @author Piotr Czyż
 * @since 2018-01-10
 */
class FleetGenerator {

  private Map<Integer, Boolean> gameBoard;

  FleetGenerator() {
    gameBoard = new HashMap<>();
    IntStream.range(0, 100).forEach(index -> gameBoard.put(index, true));
  }

  Fleet generateFleet() {
    List<Integer> shipsToPlace = Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
    List<Ship> ships = new ArrayList<>();
    shipsToPlace.forEach(i -> ships.add(generateShip(i)));
    return Fleet.ofShips(ships);
  }

  private Ship generateShip(final int shipLength) {
    List<Integer> ship = new ArrayList<>();
    boolean isVerticalOrientation = generateOrientation();
    int startIndex = generateStartIndex();
    if (isVerticalOrientation) {
      IntStream.range(0, shipLength).forEach(i -> ship.add(startIndex + i * 10));
    } else {
      IntStream.range(0, shipLength).forEach(i -> ship.add(startIndex + i));
    }
    if (isWithinBoard(ship) && canBePut(ship)) {
      markShip(ship);
      return Ship.ofMasts(convertToMasts(ship));
    } else {
      return generateShip(shipLength);
    }
  }

  private Mast[] convertToMasts(final List<Integer> ship) {
    return ship
        .stream()
        .map(obj -> Mast.ofIndex(String.valueOf(obj)))
        .collect(Collectors.toList())
        .toArray(new Mast[ship.size()]);
  }

  private void markShip(final List<Integer> ship) {
    Set<Integer> placeToMark = countNecessaryPlace(ship);
    placeToMark
        .stream()
        .filter(idx -> gameBoard.containsKey(idx))
        .forEach(idx -> gameBoard.put(idx, false));
  }

  private boolean isWithinBoard(final List<Integer> ship) {
    return ship
        .stream()
        .allMatch(integer -> integer > 0 && integer < 100);
  }

  private boolean canBePut(final List<Integer> ship) {
    return ship
        .stream()
        .allMatch(idx -> gameBoard.get(idx));
  }

  private Set<Integer> countNecessaryPlace(final List<Integer> ship) {
    Set<Integer> place = new HashSet<>();
    ship.forEach(index -> place.addAll(addIndexAndNeighbours(index)));
    return place;
  }

  private List<Integer> addIndexAndNeighbours(final Integer index) {
    List<Integer> place = new ArrayList<>();
    place.add(index - 11);
    place.add(index - 10);
    place.add(index - 9);
    place.add(index - 1);
    place.add(index);
    place.add(index + 1);
    place.add(index + 9);
    place.add(index + 10);
    place.add(index + 11);
    return place;
  }

  private int generateStartIndex() {
    List<Integer> emptyIndexes = gameBoard
        .entrySet()
        .stream()
        .filter(Map.Entry::getValue)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

    int index = new Random().nextInt(emptyIndexes.size());
    return emptyIndexes.get(index);
  }

  private boolean generateOrientation() {
    Random random = new Random();
    return random.nextBoolean();
  }
}
