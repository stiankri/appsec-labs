package messaging.protocol.model;

public record BinaryPayload(Id id, byte[] data) {
}
