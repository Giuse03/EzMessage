package net.giuse.ezmessage.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.giuse.ezmessage.interfaces.Message;
import net.giuse.ezmessage.type.MessageType;

@AllArgsConstructor
public class MessageTitle implements Message {
    @Getter
    private final String title, subTitle;
    @Getter
    private final int fadeIn, stay, fadeOut;
    @Override
    public MessageType getMessageType() {
        return MessageType.TITLE;
    }

}
