package com.epam.ships.infra.communication.core.message;

import com.epam.ships.infra.communication.api.Attachable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class EmptyAttachment implements Attachable {
    static EmptyAttachment create() {
        return new EmptyAttachment();
    }
}
