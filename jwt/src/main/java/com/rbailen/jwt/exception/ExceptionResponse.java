package com.rbailen.jwt.exception;

import java.util.Date;

/**
 * The Class ExceptionResponse.
 */
public class ExceptionResponse {

    /** The timestamp. */
    private Date timestamp;

    /** The message. */
    private String message;

    /** The details. */
    private String details;

    /**
     * Instantiates a new exception response.
     *
     * @param timestamp the timestamp
     * @param message   the message
     * @param details   the details
     */
    public ExceptionResponse(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

}
