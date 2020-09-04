package io.github.gutyerrez.economy.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultChatHook<T extends Chat> extends Hook<T> {

    @Override
    public T prepare() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }

        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return null;
        }

        return this.setInstance((T) rsp.getProvider());
    }
}
