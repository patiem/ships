package pl.korotkevics.ships.shared.transcript;

import pl.korotkevics.ships.shared.fleet.Fleet;

import javax.persistence.*;

@Entity
@Table(name = "fleets")
public class FleetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fleet_id")
  private int id;

  @ManyToOne
  private GameEntity game;

  @Column(name = "player", nullable = false)
  private String playerName;

  @Column(name = "fleet", nullable = false, columnDefinition = "longtext")
  private String fleet;

  public FleetEntity() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getFleet() {
    return fleet;
  }

  public void setFleet(String fleet) {
    this.fleet = fleet;
  }

  public static FleetEntity build(Fleet fleet, String playerName) {
    FleetEntity fleetEntity = new FleetEntity();
    fleetEntity.setFleet(fleet.toString());
    fleetEntity.setPlayerName(playerName);
    return fleetEntity;
  }

  @Override
  public String toString() {
    return String.format("Fleet for %s: /n %s", playerName, fleet);
  }
}
