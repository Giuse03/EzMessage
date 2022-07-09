package net.giuse.ezmessage.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.giuse.ezmessage.interfaces.Message;
import net.giuse.ezmessage.type.MessageType;

@AllArgsConstructor
public class MessageActionbar implements Message {
    @Getter
    private final String messageBar;

    @Override
    public MessageType getMessageType() {
        return MessageType.ACTION_BAR;
    }

}
