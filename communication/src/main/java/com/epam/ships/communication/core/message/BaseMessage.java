package com.epam.ships.communication.core.message;

import com.epam.ships.communication.api.Message;

import java.util.Objects;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see MessageBuilder
 * @see Message
 *
 * Value-object used as a communication mean.
 *
 * Setters are only available to MessageBuilder.
 *
 */

public class BaseMessage implements Message {
    //TODO introduce enums?
    private String header;
    private String status;
    private String author;
    private String statement;

    /**
     * It is declared explicitly to
     * limit its access to MessageBuilder only.
     */
    BaseMessage() {
    }

    /**
     *
     * @return header String value.
     */
    public final String getHeader() {
        return header;
    }

    /**
     *
     * @return status String value.
     */
    public final String getStatus() {
        return status;
    }

    /**
     *
     * @return author String value.
     */
    public final String getAuthor() {
        return author;
    }

    /**
     *
     * @return statement String value.
     */
    public final String getStatement() {
        return statement;
    }

    /**
     * It has to be exclusively used by MessageBuilder.
     * @param header String value.
     */
    void setHeader(String header) {
        this.header = header;
    }

    /**
     * It has to be exclusively used by MessageBuilder.
     * @param status String value.
     */
    void setStatus(String status) {
        this.status = status;
    }

    /**
     * It has to be exclusively used by MessageBuilder.
     * @param author String value.
     */
    void setAuthor(String author) {
        this.author = author;
    }

    /**
     * It has to be exclusively used by MessageBuilder.
     * @param statement String value.
     */
    void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "header='" + header + '\'' +
                ", status='" + status + '\'' +
                ", author='" + author + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMessage that = (BaseMessage) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(status, that.status) &&
                Objects.equals(author, that.author) &&
                Objects.equals(statement, that.statement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, status, author, statement);
    }
}
