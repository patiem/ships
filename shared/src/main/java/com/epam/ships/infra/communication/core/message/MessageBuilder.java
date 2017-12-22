package com.epam.ships.infra.communication.core.message;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sandor
 * @see BaseMessage
 *
 * It builds a message with given or/and
 * default values.
 * Along with BaseMessage interface it allows
 * to encapsulate BaseMessage class.
 * @since 2017-12-10
 */

public class MessageBuilder {

    private Header header = Header.DEFAULT;
    private Status status = Status.OK;
    private Author author = Author.AUTO;
    private String statement = StringUtils.EMPTY;
    private Fleet fleet = Fleet.empty();
    
    /**
     * It builds a message (BaseMessage).
     * It sets default values if else were not provided.
     *
     * @return an unmodifiable BaseMessage instance.
     */
    public final BaseMessage build() {
        final BaseMessage message = new BaseMessage();
        message.setHeader(this.header);
        message.setStatus(this.status);
        message.setAuthor(this.author);
        message.setStatement(this.statement);
        message.setFleet(this.fleet);
        return message;
    }

    /**
     * @param header Header value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withHeader(final Header header) {
        this.header = header;
        return this;
    }

    /**
     * @param status Status value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withStatus(final Status status) {
        this.status = status;
        return this;
    }

    /**
     * @param author Author value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withAuthor(final Author author) {
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
     * @param fleet a Fleet object.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withFleet(final Fleet fleet) {
        this.fleet = fleet;
        return this;
    }
}
