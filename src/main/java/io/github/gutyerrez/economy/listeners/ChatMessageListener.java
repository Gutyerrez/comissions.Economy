package io.github.gutyerrez.economy.listeners;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import io.github.gutyerrez.economy.EconomyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author SrGutyerrez
 */
public class ChatMessageListener implements Listener
{

    @EventHandler
    public void on(ChatMessageEvent event)
    {
        Player player = event.getSender().getPlayer();

        String magnataTag = "";

        if (EconomyAPI.MAGNATA_USERNAME != null && EconomyAPI.MAGNATA_USERNAME.equalsIgnoreCase(player.getName())) {
            magnataTag = "§2[$]";
        }

        event.setTagValue("{magnata}", magnataTag);
    }

}
