package messaging.frontend.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messaging.protocol.Config;
import messaging.frontend.view.ChatView;
import messaging.frontend.view.MessageView;
import messaging.protocol.Contact;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ViewModel {
    private Map<String, Contact> contactsMap;
    private final Map<String, ChatView> chatMap = new ConcurrentHashMap<>();

    private final ObservableList<String> contacts = FXCollections.observableArrayList();

    public ViewModel() {
        this.contactsMap = loadContacts();
    }

    public void start() {
        for (var alias : getAliases()) {
            chatMap.put(alias, new ChatView(this, alias));
            contacts.add(alias);
        }

        var executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::fetchLatestMessages, 0, 1, TimeUnit.SECONDS);
    }

    void fetchLatestMessages() {
        for (var contact : contactsMap.values()) {
            var candidate = contact.receive();

            while (candidate.isPresent()) {
                var message = candidate.get();
                Platform.runLater(() -> {
                    var chat = chatMap.get(contact.getAlias());
                    chat.addMessage(new MessageView(message, false));
                    try {
                        chat.setRemainingReceive(remainingReceive(contact.getAlias()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                candidate = contact.receive();
            }
        }
    }

    public void sendMessage(String alias, String message) {
        var contact = contactsMap.get(alias);
        var sendLeft = contact.send(message);
        chatMap.get(alias).setRemainingSend(sendLeft);
    }

    public void resetKeyMaterial(String alias) {
        contactsMap.get(alias).resetKeyMaterial();
        contactsMap = loadContacts();

        Platform.runLater(() -> {
            for (var c : contactsMap.values()) {
                var chat = chatMap.get(c.getAlias());
                chat.setRemainingSend(remainingSend(c.getAlias()));
                chat.setRemainingReceive(remainingReceive(c.getAlias()));
            }
        });
    }

    public void createContact(String alias) {
        var contact = Config.createDefaultContact(alias);

        contactsMap.put(contact.getAlias(), contact);
        var chat = addContact(contact.getAlias());
        chatMap.put(contact.getAlias(), chat);
    }

    public int remainingSend(String alias) {
        return contactsMap.get(alias).remainingSend();
    }

    public int remainingReceive(String alias) {
        return contactsMap.get(alias).remainingReceive();
    }

    public ChatView getChat(String alias) {
        return chatMap.get(alias);
    }

    List<String> getAliases() {
        return contactsMap.values().stream()
                .map(Contact::getAlias)
                .sorted()
                .collect(Collectors.toList());
    }

    static Map<String, Contact> loadContacts() {
        return Config.loadContacts().stream()
                .collect(Collectors.toConcurrentMap(Contact::getAlias, Function.identity()));
    }

    public ObservableList<String> getContacts() {
        return this.contacts;
    }

    ChatView addContact(String alias) {
        contacts.add(alias);
        return new ChatView(this, alias);
    }
}
