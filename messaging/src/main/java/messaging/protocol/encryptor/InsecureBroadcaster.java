package messaging.protocol.encryptor;

import messaging.protocol.Config;
import messaging.protocol.model.BinaryMessage;
import messaging.protocol.model.BinaryPayload;
import messaging.protocol.model.Encryptor;
import messaging.protocol.model.Id;

import java.math.BigInteger;
import java.util.Optional;

public class InsecureBroadcaster implements Encryptor {
    private int send;
    private int receive;
    private int remainingSend;
    private int remainingReceive;

    public InsecureBroadcaster() {
        resetKeyMaterial();
    }

    public static InsecureBroadcaster create(String alias) {
        var file = Config.contactDirectory(alias).toFile();
        file.mkdirs();

        return new InsecureBroadcaster();
    }

    @Override
    public BinaryPayload encrypt(BinaryMessage binaryMessage) {
        var result = new BinaryPayload(Id.of(BigInteger.valueOf(send).toByteArray()), binaryMessage.data());
        send++;
        remainingSend--;
        return result;
    }

    @Override
    public BinaryMessage decrypt(BinaryPayload binaryPayload) {
        var result = new BinaryMessage(binaryPayload.data());
        receive++;
        remainingReceive--;
        send = receive;
        remainingSend = remainingReceive;
        return result;
    }

    @Override
    public int remainingSend() {
        return remainingSend;
    }

    @Override
    public int remainingReceive() {
        return remainingReceive;
    }

    @Override
    public void resetKeyMaterial() {
        send = 0;
        receive = 0;

        var config = Config.load();
        remainingSend = config.messageCount();
        remainingReceive = config.messageCount();
    }

    @Override
    public Optional<Id> nextReceiveId() {
        if (remainingReceive() > 0) {
            return Optional.of(Id.of(BigInteger.valueOf(receive).toByteArray()));
        } else {
            return Optional.empty();
        }
    }
}
