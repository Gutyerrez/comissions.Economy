package io.github.gutyerrez.economy.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author SrGutyerrez
 */
public class LuckPermsHook<T extends LuckPerms> extends Hook<T>
{

    @Override
    public T prepare()
    {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            return null;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {
            return null;
        }

        return this.setInstance((T) provider.getProvider());
    }

}
