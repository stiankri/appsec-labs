package messaging.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import messaging.protocol.encryptor.InsecureBroadcaster;
import messaging.protocol.model.Encryptor;
import messaging.protocol.model.Transmitter;
import messaging.protocol.transmitter.InMemoryTransmitter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Config(int messageCount) {
    private static Transmitter TRANSMITTER = new InMemoryTransmitter();
    private static final Path CONFIG_ROOT_DIRECTORY = Path.of(System.getProperty("user.home") + "/.messaging/");
    private static final Charset CHARSET = StandardCharsets.US_ASCII;
    public static final int MAX_MESSAGE_LENGTH = 140;

    public static Charset getCharset() {
        return CHARSET;
    }

    public static Config load() {
        try {
            var mapper = new ObjectMapper(new YAMLFactory());
            var file = configDirectory().resolve("config").toFile();
            if (!file.exists()) {
                throw new RuntimeException("Config file does not exist");
            }

            var configFile = mapper.readValue(file, Config.class);
            return new Config(configFile.messageCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Contact> loadContacts() {
        var aliases = aliases();
        var contacts = new ArrayList<Contact>();

        for (var alias : aliases) {
            contacts.add(new Contact(alias, defaultEncryptor(alias), defaultTransmitter()));
        }

        return contacts;
    }

    public static Path configDirectory() {
        return CONFIG_ROOT_DIRECTORY;
    }

    public static Path contactsDirectory() {
        return configDirectory().resolve("contacts/");
    }

    public static Path contactDirectory(String alias) {
        return contactsDirectory().resolve(alias);
    }
    private static Encryptor defaultEncryptor(String alias) {
        return InsecureBroadcaster.create(alias);
    }

    private static Transmitter defaultTransmitter() {
        return TRANSMITTER;
    }

    public static Contact createDefaultContact(String alias) {
        return new Contact(alias, defaultEncryptor(alias), defaultTransmitter());
    }

    static List<String> aliases() {
        var base = contactsDirectory();
        try (var s = Files.list(base)) {
            return s.filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
