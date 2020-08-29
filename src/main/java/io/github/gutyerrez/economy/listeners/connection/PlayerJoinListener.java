package io.github.gutyerrez.economy.listeners.connection;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.math.BigDecimal;

/**
 * @author SrGutyerrez
 */
public class PlayerJoinListener implements Listener
{

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void on(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();


        User user = CoreProvider.Cache.Local.USERS.provide().get(
                player.getName()
        );

        BigDecimal value = EconomyProvider.Repositories.ECONOMY.provide().get(
                user,
                Currency.COINS
        );

        EconomyProvider.Cache.Local.CURRENCY.provide().add(
                user.getUniqueId(),
                Currency.COINS,
                value == null ? BigDecimal.ZERO : value
        );
    }

}
