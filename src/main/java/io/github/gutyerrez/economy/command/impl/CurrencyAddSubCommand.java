package io.github.gutyerrez.economy.command.impl;

import com.google.common.primitives.Doubles;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.commands.Argument;
import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyAPI;
import io.github.gutyerrez.economy.command.CurrencySubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author SrGutyerrez
 */
public class CurrencyAddSubCommand extends CurrencySubCommand {

    private final Currency currency;

    public CurrencyAddSubCommand(Currency currency) {
        super(
                "add",
                CommandRestriction.CONSOLE_AND_IN_GAME,
                "give"
        );

        this.currency = currency;

        this.registerArgument(new Argument("nick", "Nome do jogador."));
        this.registerArgument(new Argument("valor", "Quantidade de " + currency.getSingular() + "."));
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("economy.commands.money.add")) {
            return;
        }

        User targetUser = CoreProvider.Cache.Local.USERS.provide().get(args[0]);

        if (targetUser == null) {
            sender.sendMessage("§cEste usuário não existe.");
            return;
        }

        Double amount = Doubles.tryParse(args[1]);

        if (amount == null || amount.isNaN() || amount < 1) {
            sender.sendMessage("§cVocê informou um valor inválido.");
            return;
        }

        EconomyAPI.add(targetUser, this.currency, amount);

        sender.sendMessage(String.format(
                "§aVocê adicionou §f%s §ano saldo de §f%s§a.",
                this.currency.format(amount),
                targetUser.getName()
        ));
    }

}
