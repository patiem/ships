package pl.korotkevics.ships.shared.infra.communication.api.message;

public enum Header {
  DEFAULT, OPPONENT_CONNECTED, CONNECTION,
  YOUR_TURN, MISS, HIT, SHIP_DESTRUCTED,
  LOSE, WIN, SHOT, PLACEMENT
}
