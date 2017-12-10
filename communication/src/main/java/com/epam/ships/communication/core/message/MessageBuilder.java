package com.epam.ships.communication.core.message;

import com.epam.ships.communication.api.Message;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see BaseMessage
 * @see Message
 *
 * It builds a message with given or/and
 * default values.
 * Along with Message interface it allows
 * to encapsulate BaseMessage class.
 */

public class MessageBuilder {

    private String header = StringUtils.EMPTY;
    private String status = StringUtils.EMPTY;
    private String author = StringUtils.EMPTY;
    private String statement = StringUtils.EMPTY;

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
        return baseMessage;
    }

    /**
     *
     * @param header String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withHeader(final String header) {
        this.header = header;
        return this;
    }

    /**
     *
     * @param status String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withStatus(final String status) {
        this.status = status;
        return this;
    }

    /**
     *
     * @param author String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withAuthor(final String author) {
        this.author = author;
        return this;
    }

    /**
     *
     * @param statement a String value.
     * @return a chain instance of MessageBuilder.
     */
    public MessageBuilder withStatement(final String statement) {
        this.statement = statement;
        return this;
    }

}
