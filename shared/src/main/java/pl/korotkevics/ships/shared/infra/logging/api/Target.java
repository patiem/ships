package pl.korotkevics.ships.shared.infra.logging.api;

/**
 * An implementing class has to ensure
 * it is possible to direct given messages
 * to a configurable target.
 *
 * @author Sandor Korotkevics
 * @since 2017-12-10
 */

public interface Target {

  /**
   * Directs a neutral message to
   * a configurable target.
   *
   * @param message to be directed.
   */
  void info(Object message);


  /**
   * Directs an error message to
   * a configurable target.
   *
   * @param message to be directed.
   */
  void error(Object message);

  /**
   * Directs a debug message to
   * a configurable target.
   *
   * @param message to be directed.
   */
  void debug(Object message);
  
  
  /**
   * Directs a report (game) message to
   * a configurable target.
   *
   * @param message to be directed.
   * @param destinationLoggerName
   */
  void report(Object message, final String destinationLoggerName);

}
