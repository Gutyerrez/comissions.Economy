package io.github.gutyerrez.economy.command.impl;

import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyProvider;
import io.github.gutyerrez.economy.command.CurrencySubCommand;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SrGutyerrez
 */
public class CurrencyTopSubCommand extends CurrencySubCommand {

    private final Currency currency;
    private final LinkedHashMap<UUID, Double> top = Maps.newLinkedHashMap();

    private Long cooldown;

    public CurrencyTopSubCommand(Currency currency) {
        super("top", CommandRestriction.CONSOLE_AND_IN_GAME);

        this.currency = currency;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(this.cooldown == null || this.cooldown < System.currentTimeMillis()){
            this.top.clear();;
            this.top.putAll(EconomyProvider.Repositories.ECONOMY.provide().fetchTop(this.currency));
            this.cooldown = System.currentTimeMillis() + 5000L;
        }

        StringBuilder message = new StringBuilder();

        message.append("\n")
                .append("§2Top 10 jogadores mais ricos §7(Atualizado a cada 5 minutos)")
                .append("\n \n");

        AtomicInteger count = new AtomicInteger(1);

        this.top.forEach((uuid, value) -> {
            User user = CoreProvider.Cache.Local.USERS.provide().get(uuid);
            String coins = NumberUtils.format(value);

            message.append(String.format(
                    "  §f%sº §7%s §7($ %s)\n",
                    count.getAndIncrement(),
                    EconomyProvider.Hooks.CHAT.get().getGroupPrefix("world", user.getName()) + user.getName(),
                    coins
            ));
        });

        message.append("\n ");

        sender.sendMessage(message.toString());
    }

}
