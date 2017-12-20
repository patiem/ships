package com.epam.ships.infra.communication.core.message;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.Message;
import lombok.*;

/**
 * @author Sandor
 * @see MessageBuilder
 * @see Message
 * <p>
 * Value-object used as a communication mean.
 * <p>
 * Setters are only available to MessageBuilder.
 * @since 2017-12-10
 */

@ToString
@EqualsAndHashCode
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
public class BaseMessage implements Message {
    //TODO introduce enums?
    private String header;
    private String status;
    private String author;
    private String statement;
    private Fleet fleet;
    
    /**
     * It is declared explicitly to
     * limit its access to MessageBuilder only.
     */
    BaseMessage() {
    }
}
