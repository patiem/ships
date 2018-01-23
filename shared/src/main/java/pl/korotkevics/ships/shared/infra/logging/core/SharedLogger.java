package pl.korotkevics.ships.shared.infra.logging.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.korotkevics.ships.shared.infra.logging.api.Target;

/**
 * Logger shared across modules. A Log4J2 wrapper.
 * Log4J2 can be configured with log4j2.xml.
 * A short manual is present within the XML file.
 *
 * @author Sandor Korotkevics
 * @see Target
 * @since 2017-12-10
 */
public class SharedLogger implements Target {
  
  final Level REPORT = Level.forName("REPORT", 50);
  
  private final Logger logger;
  private final Class<?> clazz;
  
  /**
   * @param clazz
   *     class name to appear in log messages.
   */
  public SharedLogger(final Class<?> clazz) {
    this.logger = LogManager.getLogger();
    this.clazz = clazz;
  }
  
  /**
   * Used for logging normal (neutral) messages.
   *
   * @param message
   *     to be logged.
   *     Extra information will be
   *     appended (such as class name,
   *     datetime, level).
   */
  @Override
  public void info(final Object message) {
    this.logger.info(this.prepareDefaultMessage(message));
  }
  
  /**
   * Used for logging error messages
   * (example: logging an exception
   * message).
   *
   * @param message
   *     to be logged.
   *     Extra information will be
   *     appended (such as class name,
   *     datetime, level).
   */
  @Override
  public void error(final Object message) {
    this.logger.error(this.prepareDefaultMessage(message));
  }
  
  /**
   * Used for logging debug messages.
   *
   * @param message
   *     to be logged.
   *     Extra information will be
   *     appended (such as class name,
   *     datetime, level).
   */
  @Override
  public void debug(final Object message) {
    this.logger.debug(this.prepareDefaultMessage(message));
  }
  
  
  /**
   * Used for logging game messages.
   *
   * @param message
   *     to be logged.
   * @param destinationLoggerName
   *     an arbitrary logger name set with a configuration file.
   */
  @Override
  public void report(final Object message, final String destinationLoggerName) {
    this.logger.log(REPORT, this.prepareGameMessage(message, destinationLoggerName));
  }
  
  private String prepareDefaultMessage(final Object message) {
    return this.clazz.getSimpleName() + ": " + message;
  }
  
  private String prepareGameMessage(final Object message, final String destinationLoggerName) {
    return destinationLoggerName + ": " + message;
  }
}
