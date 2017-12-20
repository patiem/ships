package com.epam.ships.infra.communication.api;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.core.message.EmptyAttachment;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Sandor
 * @since 2017-12-20
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value=Fleet.class),
               @JsonSubTypes.Type(value=EmptyAttachment.class)
})
public interface Attachable {}
