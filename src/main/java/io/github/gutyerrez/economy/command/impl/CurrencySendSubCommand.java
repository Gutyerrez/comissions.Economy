package io.github.gutyerrez.economy.command.impl;

import com.google.common.primitives.Doubles;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.commands.Argument;
import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.core.spigot.commands.CustomCommand;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyAPI;
import io.github.gutyerrez.economy.EconomyPlugin;
import io.github.gutyerrez.economy.EconomyProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class CurrencySendSubCommand extends CustomCommand {

    private final Currency currency;

    public CurrencySendSubCommand(Currency currency) {
        super("pay", CommandRestriction.IN_GAME, "enviar");

        this.currency = currency;

        this.registerArgument(new Argument("nick", "Nome do jogador."));
        this.registerArgument(new Argument("valor", "Quantidade de " + currency.getSingular() + "."));
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String targetName = args[0];

        if (player.getName().equalsIgnoreCase(targetName)) {
            sender.sendMessage("§cVocê não pode enviar coins para si mesmo.");
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            return;
        }

        User targetUser = CoreProvider.Cache.Local.USERS.provide().get(targetName);

        if (targetUser == null) {
            sender.sendMessage("§cEste usuário não existe.");
            return;
        }

        Double amount = Doubles.tryParse(args[1]);

        if (amount == null || amount.isNaN() || amount < 1) {
            sender.sendMessage("§cVocê informou um valor inválido.");
            return;
        }

        User user = CoreProvider.Cache.Local.USERS.provide().get(sender.getName());

        Double coins = EconomyProvider.Repositories.ECONOMY.provide().get(user, this.currency);

        if (coins < amount) {
            sender.sendMessage("§cVocê não possui coins suficientes.");
            return;
        }

        EconomyAPI.remove(user, this.currency, amount);
        EconomyAPI.add(targetUser, this.currency, amount);

        Bukkit.getScheduler().runTaskAsynchronously(
                EconomyPlugin.getInstance(),
                () -> {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);

                    sender.sendMessage(String.format(
                            "§eVocê enviou §f%s §epara §f%s§e.\n ",
                            this.currency.format(amount),
                            ChatColor.translateAlternateColorCodes(
                                    '&',
                                    EconomyProvider.Hooks.CHAT.get().getPlayerPrefix("world", targetName)
                            ) + offlinePlayer.getName()
                    ));

                    Player targetPlayer = offlinePlayer.getPlayer();

                    if (targetPlayer != null) {
                        sender.sendMessage(String.format(
                                "§eVocê recebeu §f%s §ede §f%s§e.\n ",
                                this.currency.format(amount),
                                ChatColor.translateAlternateColorCodes(
                                        '&',
                                        EconomyProvider.Hooks.CHAT.get().getPlayerPrefix("world", sender.getName())
                                ) + sender.getName()
                        ));
                    }
                });
    }
}
