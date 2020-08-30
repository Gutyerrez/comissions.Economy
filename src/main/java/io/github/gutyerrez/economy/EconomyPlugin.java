package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.spigot.CustomPlugin;
import io.github.gutyerrez.core.spigot.commands.CommandRegistry;
import io.github.gutyerrez.economy.command.CurrencyCommand;
import io.github.gutyerrez.economy.listeners.ChatMessageListener;
import io.github.gutyerrez.economy.listeners.connection.PlayerJoinListener;
import io.github.gutyerrez.economy.misc.integration.VaultEconomy;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;

/**
 * @author SrGutyerrez
 */
public class EconomyPlugin extends CustomPlugin
{

    @Getter
    private static EconomyPlugin instance;

    public EconomyPlugin()
    {
        EconomyPlugin.instance = this;
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        EconomyProvider.prepare();

        CommandRegistry.registerCommand(new CurrencyCommand(Currency.COINS));

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new ChatMessageListener(), this);

        this.getServer().getServicesManager().register(
                Economy.class,
                new VaultEconomy(),
                this,
                ServicePriority.Highest
        );
    }

}
