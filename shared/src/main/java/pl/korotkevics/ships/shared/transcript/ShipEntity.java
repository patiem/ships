package pl.korotkevics.ships.shared.transcript;


import pl.korotkevics.ships.shared.fleet.Ship;

import javax.persistence.*;

@Entity
@Table(name = "ships")
public class ShipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ship_id")
  private int id;

  @ManyToOne
  private FleetEntity fleet;

  @Column(name = "ship", nullable = false)
  private String ship;

  public ShipEntity() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public FleetEntity getFleet() {
    return fleet;
  }

  public void setFleet(FleetEntity fleet) {
    this.fleet = fleet;
  }

  public String getShip() {
    return ship;
  }

  public void setShip(String ship) {
    this.ship = ship;
  }

  public static ShipEntity build(Ship s) {
    ShipEntity shipEntity = new ShipEntity();
    shipEntity.setShip(s.toString());
    return shipEntity;
  }


}
