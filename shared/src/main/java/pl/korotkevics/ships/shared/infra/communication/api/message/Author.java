package pl.korotkevics.ships.shared.infra.communication.api.message;

import lombok.EqualsAndHashCode;

/**
 * @author Sandor Korotkevics
 * @since 2017-12-22
 *
 * It represents a message author:
 * a message can be signed explicitly
 * by client or server, unsigned messages
 * are get the default "auto" author.
 */

public enum Author {
  AUTO,
  CLIENT,
  SERVER
}