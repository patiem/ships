package pl.korotkevics.ships.shared.infra.communication.api.message;

/**
 * It represents a message author:
 *        a message can be signed explicitly
 *        by client or server, unsigned messages
 *        are get the default "auto" author.
 *
 * @author Sandor Korotkevics
 * @since 2017-12-22
 */

public enum Author {
  AUTO,
  CLIENT,
  SERVER
}