package messaging.protocol.transmitter;

import messaging.protocol.model.BinaryPayload;
import messaging.protocol.model.Id;
import messaging.protocol.model.Transmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTransmitter implements Transmitter {
    private final Map<Id, byte[]> map = new HashMap<>();

    @Override
    public Optional<BinaryPayload> receive(Id id) {
        var candidate = map.get(id);

        if (candidate == null) {
            return Optional.empty();
        } else {
            return Optional.of(new BinaryPayload(id, candidate));
        }
    }

    @Override
    public void send(BinaryPayload message) {
        map.put(message.id(), message.data());
    }
}
