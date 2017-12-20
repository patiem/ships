package com.epam.ships.infra.communication.core.message;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.Message;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sandor
 * @see BaseMessage
 * @see Message
 * <p>
 * It builds a message with given or/and
 * default values.
 * Along with Message interface it allows
 * to encapsulate BaseMessage class.
 * @since 2017-12-10
 */

public class MessageBuilder {

    private String header = StringUtils.EMPTY;
    private String status = StringUtils.EMPTY;
    private String author = StringUtils.EMPTY;
    private String statement = StringUtils.EMPTY;
    private Fleet fleet = null;
    
    /**
     * It builds a message (BaseMessage).
     * It sets default values if else were not provided.
     *
     * @return an unmodifiable BaseMessage instance.
     */
    public final Message build() {
        final BaseMessage baseMessage = new BaseMessage();
        baseMessage.setHeader(this.header);
        baseMessage.setStatus(this.status);
        baseMessage.setAuthor(this.author);
        baseMessage.setStatement(this.statement);
        baseMessage.setFleet(this.fleet);
        return baseMessage;
    }

    /**
     * @param header String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withHeader(final String header) {
        this.header = header;
        return this;
    }

    /**
     * @param status String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withStatus(final String status) {
        this.status = status;
        return this;
    }

    /**
     * @param author String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withAuthor(final String author) {
        this.author = author;
        return this;
    }

    /**
     * @param statement a String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withStatement(final String statement) {
        this.statement = statement;
        return this;
    }
    
    /**
     * @param fleet an object implementing Attachable interface.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withFleet(final Fleet fleet) {
        this.fleet = fleet;
        return this;
    }
}
