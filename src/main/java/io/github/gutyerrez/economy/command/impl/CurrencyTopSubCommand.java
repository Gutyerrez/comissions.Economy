package io.github.gutyerrez.economy.command.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyAPI;
import io.github.gutyerrez.economy.EconomyProvider;
import io.github.gutyerrez.economy.command.CurrencySubCommand;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.ChatMetaType;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author SrGutyerrez
 */
public class CurrencyTopSubCommand extends CurrencySubCommand
{

    private final Currency currency;
    private final LinkedHashMap<String, BigDecimal> top = Maps.newLinkedHashMap();

    private Long cooldown;

    public CurrencyTopSubCommand(Currency currency)
    {
        super(
                "top",
                CommandRestriction.CONSOLE_AND_IN_GAME
        );

        this.currency = currency;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args)
    {
        if (this.cooldown == null || this.cooldown < System.currentTimeMillis()) {
            this.top.clear();
            this.top.putAll(EconomyProvider.Repositories.ECONOMY.provide().fetchTop(this.currency));
            this.cooldown = System.currentTimeMillis() + 5000L;

            if (!this.top.isEmpty()) {
                EconomyAPI.MAGNATA_USERNAME = ImmutableList.copyOf(this.top.keySet()).get(0);
            }
        }

        StringBuilder message = new StringBuilder();

        message.append("\n")
                .append("§e§l MONEY TOP §r§7(Atualiza a cada 5 minutos)")
                .append("\n")
                .append("§f Jogadores mais ricos do servidor.")
                .append("\n \n");

        AtomicInteger count = new AtomicInteger(1);

        this.top.forEach((name, value) -> {

            String prefix = CompletableFuture.supplyAsync(supplyPrefix(name)).join();

            message.append(String.format(
                    "  §a%sº §7%s §7%s\n",
                    count.getAndIncrement(),
                    (count.get() == 2 ? "§2[$] " : "") + prefix + name,
                    this.currency.format(value)
            ));
        });

        /*this.top.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .forEach(entry -> {
                    User user = EconomyProvider.Hooks.CHAT.get().getUserManager().getUser(
                            entry.getKey()
                    );

                    if (user == null) {
                        try {
                            user = EconomyProvider.Hooks.CHAT.get().getUserManager().loadUser(
                                    EconomyProvider.Hooks.CHAT.get().getUserManager().lookupUniqueId(
                                            entry.getKey()
                                    ).get()
                            ).get();
                        } catch (InterruptedException | ExecutionException exception) {
                            exception.printStackTrace();
                        }
                    }

                    message.append(String.format(
                            "  §a%sº §7%s §7%s\n",
                            count.getAndIncrement(),
                            (count.get() == 2 ? "§2[$] " : "") + ChatColor.translateAlternateColorCodes(
                                    '&',
                                    EconomyProvider.Hooks.CHAT.getChatMeta(
                                            user.getUsername(),
                                            ChatMetaType.PREFIX
                                    )
                            ) + entry.getKey(),
                            this.currency.format(entry.getValue())
                    ));
                });
         */

        message.append("\n ");

        sender.sendMessage(message.toString());
    }

    private Supplier<String> supplyPrefix(String name) {
        return () -> ChatColor.translateAlternateColorCodes(
                '&', EconomyProvider.Hooks.CHAT.get().getPlayerPrefix("world",
                        Bukkit.getOfflinePlayer(name))
        );
    }

}
