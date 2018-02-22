package pl.korotkevics.ships.shared.transcript;

import pl.korotkevics.ships.shared.fleet.Fleet;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

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

  @OneToMany(mappedBy = "fleet", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private Set<ShipEntity> fleet;

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

  public Set<ShipEntity> getFleet() {
    return fleet;
  }

  public void setFleet(Set<ShipEntity> fleet) {
    fleet.forEach(s -> s.setFleet(this));
    this.fleet = fleet;
  }

  public static FleetEntity build(Fleet fleet, String playerName) {
    FleetEntity fleetEntity = new FleetEntity();

    fleetEntity.setFleet(fleet.extractShips()
        .stream()
        .map(s -> ShipEntity.build(s))
        .collect(Collectors.toSet()));
    fleetEntity.setPlayerName(playerName);
    return fleetEntity;
  }
}
