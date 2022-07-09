package net.giuse.ezmessage;


import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.giuse.ezmessage.interfaces.Message;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class MessageLoader {
    @Getter private final AsyncCache<String, Message> cache = Caffeine.newBuilder()
            .executor(Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors()/2)).buildAsync();
    public final BukkitAudiences audience;

    /*
     * Send title to Player
     */
    public void sendTitle(Player player, Component title, Component subTitle, int fadeIn, int stay, int fadeOut) {
        Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
        Title titleToSend = Title.title(title, subTitle, times);
        audience.player(player).showTitle(titleToSend);
    }

    /*
     * Send message to Player
     */
    public void sendChat(CommandSender player, Component messageChat) {
        audience.sender(player).sendMessage(messageChat);

    }


    /*
     * Send message to Player
     */
    public void sendActionBar(Player player, Component send) {
        audience.player(player).sendActionBar(send);
    }

    /*
     * Add Message to cache
     */

    public void addMessageCache(String id, CompletableFuture<Message> message) {
        cache.put(id,message);
    }

}
