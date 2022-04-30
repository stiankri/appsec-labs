package messaging.protocol.model;

import java.util.Optional;

public interface Transmitter {
    Optional<BinaryPayload> receive(Id id);
    void send(BinaryPayload message);
}
