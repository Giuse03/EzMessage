package net.giuse.ezmessage;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.giuse.ezmessage.interfaces.MessageLoader;
import net.giuse.ezmessage.messages.MessageActionbar;
import net.giuse.ezmessage.messages.MessageChat;
import net.giuse.ezmessage.messages.MessageTitle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MessageBuilder {
    private final MessageLoader messageLoader;
    private CommandSender commandSender;
    private String idMessage;

    /*
     * Set CommandSender to send Message
     */
    public MessageBuilder setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
        return this;
    }

    /*
     * Set ID of the messaage to search
     */
    public MessageBuilder setIDMessage(String ID) {
        this.idMessage = ID;
        return this;
    }

    /*
     * Send Message with replaces, regex split ===
     */
    @SneakyThrows
    public void sendMessage(String... replace) {
        messageLoader.getCache().getIfPresent(idMessage).whenCompleteAsync(((message, throwable) -> {
            switch (message.getMessageType()) {
                //SEND CHAT
                case CHAT:
                    MessageChat messageChat = (MessageChat) message;
                    String messageToReplace = messageChat.getMessageChat();
                    if (replace.length == 0) {
                        messageLoader.sendChat(commandSender, Component.text(messageToReplace));
                        break;
                    }
                    for (String args : replace) {
                        String[] arg = args.split("===");
                        messageToReplace = messageToReplace.replace(arg[0], arg[1]);
                    }
                    Component newComponentReplacedText = Component.text(messageToReplace);
                    messageLoader.sendChat(commandSender, newComponentReplacedText);
                    break;

                //Send Action Bar
                case ACTION_BAR:
                    if (commandSender instanceof Player) {
                        MessageActionbar messageActionbar = (MessageActionbar) message;
                        String placeHolder = messageActionbar.getMessageBar();
                        if (replace.length == 0) {
                            messageLoader.sendActionBar((Player) commandSender, Component.text(placeHolder));
                            break;
                        }
                        for (String args : replace) {
                            String[] arg = args.split("===");
                            placeHolder = placeHolder.replace(arg[0], arg[1]);
                        }
                        Component newComponentReplaced = Component.text(placeHolder);
                        messageLoader.sendActionBar((Player) commandSender, newComponentReplaced);
                        break;
                    }
                    //Send Title
                case TITLE:
                    if (commandSender instanceof Player) {
                        MessageTitle messageTitle = (MessageTitle) message;
                        String title = messageTitle.getTitle();
                        String subTitle = messageTitle.getSubTitle();
                        if (replace.length == 0) {
                            TextComponent newTitleComponent = Component.text(title);
                            TextComponent newSubTitleComponent = Component.text(subTitle);
                            messageLoader.sendTitle((Player) commandSender, newTitleComponent, newSubTitleComponent, messageTitle.getFadeIn(), messageTitle.getStay(), messageTitle.getFadeOut());
                            break;
                        }
                        for (String args : replace) {
                            String[] arg = args.split("===");
                            title = title.replace(arg[0], arg[1]);
                            subTitle = subTitle.replace(arg[0], arg[1]);
                        }
                        TextComponent newTitleComponent = Component.text(title);
                        TextComponent newSubTitleComponent = Component.text(subTitle);
                        messageLoader.sendTitle((Player) commandSender, newTitleComponent, newSubTitleComponent, messageTitle.getFadeIn(), messageTitle.getStay(), messageTitle.getFadeOut());
                    }
            }
        }));

    }

}
