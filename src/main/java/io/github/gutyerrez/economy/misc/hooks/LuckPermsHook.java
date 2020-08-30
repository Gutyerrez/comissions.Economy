package io.github.gutyerrez.economy.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.ChatMetaType;
import org.bukkit.Bukkit;

import java.util.concurrent.ExecutionException;

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

        return this.setInstance((T) LuckPermsProvider.get());
    }

    public String getChatMeta(String username, ChatMetaType type) {
        User user = this.get().getUserManager().getUser(username);


        try {
            if (user == null) {
                user = this.get().getUserManager().loadUser(
                        this.get().getUserManager().lookupUniqueId(username).get()
                ).get();
            }
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
        
        if (user == null) {
            return "";
        }

        CachedMetaData metaData = user.getCachedData().getMetaData();
        
        String val = type == ChatMetaType.PREFIX ? metaData.getPrefix() : metaData.getSuffix();

        this.get().getUserManager().cleanupUser(user);

        return val == null ? "" : val;
    }

}
