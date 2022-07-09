package net.giuse.ezmessage.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.giuse.ezmessage.interfaces.Message;
import net.giuse.ezmessage.type.MessageType;

@AllArgsConstructor
public class MessageChat implements Message {

    @Getter
    private final String messageChat;

    @Override
    public MessageType getMessageType() {
        return MessageType.CHAT;
    }

}
