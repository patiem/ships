package pl.korotkevics.ships.shared.infra.logging.core;

import pl.korotkevics.ships.shared.infra.logging.api.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger shared across modules. A Log4J2 wrapper.
 * Log4J2 can be configured with log4j2.xml.
 * A short manual is present within the XML file.
 * @author Sandor Korotkevics
 * @see Target
 * @since 2017-12-10
 */
public class SharedLogger implements Target {

  private final Logger logger;
  private final Class<?> clazz;

  /**
   * @param clazz class name to appear in log messages.
   */
  public SharedLogger(final Class<?> clazz) {
    this.logger = LogManager.getLogger();
    this.clazz = clazz;
  }

  /**
   * Used for logging normal (neutral) messages.
   *
   * @param message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void info(final Object message) {
    this.logger.info(this.prepareMessage(message));
  }

  /**
   * Used for logging error messages
   * (example: logging an exception
   * message).
   *
   * @param message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void error(final Object message) {
    this.logger.error(this.prepareMessage(message));
  }

  /**
   * Used for logging debug messages.
   *
   * @param message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void debug(final Object message) {
    this.logger.debug(this.prepareMessage(message));
  }

  private String prepareMessage(final Object message) {
    return this.clazz.getSimpleName() + ": " + message;
  }
}
