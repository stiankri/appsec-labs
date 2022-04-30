package messaging.protocol.model;

import java.util.Optional;

public interface Encryptor {
    BinaryPayload encrypt(BinaryMessage binaryMessage);
    BinaryMessage decrypt(BinaryPayload binaryPayload);

    Optional<Id> nextReceiveId();
    int remainingSend();
    int remainingReceive();
    void resetKeyMaterial();
}
