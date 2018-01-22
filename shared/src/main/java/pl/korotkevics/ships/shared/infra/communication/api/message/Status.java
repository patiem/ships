package pl.korotkevics.ships.shared.infra.communication.api.message;

/**
 * It represents a message status:
 *      - OK if it is a regular message.
 *      - END if it is a last message in a batch.
 *
 * @author Sandor Korotkevics
 * @since 2017-12-22
 */

public enum Status {
  OK,
  END
}
