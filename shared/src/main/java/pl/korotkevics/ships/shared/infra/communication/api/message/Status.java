package pl.korotkevics.ships.shared.infra.communication.api.message;

/**
 * @author Sandor Korotkevics
 * @since 2017-12-22
 *
 * It represents a message status:
 * - OK if it is a regular message.
 * - END if it is a last message in a batch.
 */

public enum Status {
  OK,
  END
}
