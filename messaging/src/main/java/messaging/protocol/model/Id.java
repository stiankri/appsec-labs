package messaging.protocol.model;

import java.util.Arrays;

public class Id {
    private final byte[] id;

    Id(byte[] id) {
        this.id = id;
    }

    public static Id of(byte[] id) {
        return new Id(id);
    }

    public byte[] toByteArray() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return Arrays.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }
}
