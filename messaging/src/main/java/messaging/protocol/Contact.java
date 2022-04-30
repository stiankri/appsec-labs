package messaging.protocol;

import messaging.protocol.model.BinaryMessage;
import messaging.protocol.model.Encryptor;
import messaging.protocol.model.Transmitter;

import java.util.Optional;

public class Contact {
    private final String alias;
    private final Encryptor encryptor;
    private final Transmitter transmitter;

    public Contact(String alias, Encryptor encryptor, Transmitter transmitter) {
        this.alias = alias;
        this.encryptor = encryptor;
        this.transmitter = transmitter;
    }

    public int send(String message) {
        var binaryMessage = new BinaryMessage(message.getBytes(Config.getCharset()));
        var binaryPayload = encryptor.encrypt(binaryMessage);
        transmitter.send(binaryPayload);
        return remainingSend();
    }

    public Optional<String> receive() {
        var nextId = encryptor.nextReceiveId();
        if (nextId.isPresent()) {
            var payload = transmitter.receive(nextId.get());

            if (payload.isPresent()) {
                var binaryMessage = encryptor.decrypt(payload.get());
                return Optional.of(new String(binaryMessage.data(), Config.getCharset()));
            }
        }

        return Optional.empty();
    }

    public String getAlias() {
        return alias;
    }

    public int remainingSend() {
        return encryptor.remainingSend();
    }

    public int remainingReceive() {
        return encryptor.remainingReceive();
    }

    public void resetKeyMaterial() {
        encryptor.resetKeyMaterial();
    }
}
