package pl.korotkevics.ships.shared.infra.communication.api.message;

/**
 * @author Sandor Korotkevics
 * @since 2017-12-22
 *
 * It represents a message header defining
 * a message content (main subject/what a
 * message has inside).
 */

public enum Header {
  DEFAULT,
  OPPONENT_CONNECTED,
  CONNECTION,
  YOUR_TURN,
  MISS,
  HIT,
  SHIP_DESTRUCTED,
  LOSE,
  WIN,
  SHOT,
  MANUAL_PLACEMENT,
  RANDOM_PLACEMENT
}
