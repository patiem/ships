package com.epam.ships.infra.logging.core;

import com.epam.ships.infra.logging.api.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Sandor
 * @see Target
 * <p>
 * A logger shared across modules.
 * <p>
 * It is a Log4J wrapper.
 * Log4J can be configured with log4j2.xml.
 * @since 2017-12-10
 */
public class SharedLogger implements Target {

  private final Logger logger;
  private final Class<?> clazz;

  /**
   * @param clazz a classname used in log messages.
   */
  public SharedLogger(final Class<?> clazz) {
    this.logger = LogManager.getLogger();
    this.clazz = clazz;
  }

  /**
   * It is used for logging normal (neutral)
   * messages.
   *
   * @param message a message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void info(final Object message) {
    this.logger.info(this.prepareMessage(message));
  }

  /**
   * It is used  for logging error messages
   * (for example, when logging an exception
   * message).
   *
   * @param message a message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void error(final Object message) {
    this.logger.error(this.prepareMessage(message));
  }

  /**
   * It is used  for logging debug messages.
   *
   * @param message a message to be logged.
   *                Extra information will be
   *                appended (such as class name,
   *                datetime, level).
   */
  @Override
  public void debug(Object message) {
    this.logger.debug(this.prepareMessage(message));
  }

  private String prepareMessage(Object message) {
    return this.clazz.getSimpleName() + ": " + message;
  }
}
