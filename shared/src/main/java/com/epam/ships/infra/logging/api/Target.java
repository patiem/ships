package com.epam.ships.infra.logging.api;

/**
 * @author Sandor
 * @since 2017-12-10
 *
 * An implementing class has to ensure
 * it is possible to direct given messages
 * to a configurable target.
 */

public interface Target {

    /**
     * Directing a neutral message to
     * a configurable target.
     * @param message a message to be
     *                directed.
     */
    void info(Object message);


    /**
     * Directing an error message to
     * a configurable target.
     * @param message a message to be
     *                directed.
     */
    void error(Object message);

    /**
     * Directing a debug message to
     * a configurable target.
     * @param message a message to be
     *                directed.
     */
    void debug(Object message);
}
