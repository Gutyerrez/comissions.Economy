package io.github.gutyerrez.economy.command.impl;

import io.github.gutyerrez.core.shared.commands.Argument;
import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyAPI;
import io.github.gutyerrez.economy.command.CurrencySubCommand;
import io.github.gutyerrez.economy.misc.utils.EconomyExecuteAction;
import org.bukkit.command.CommandSender;

/**
 * @author SrGutyerrez
 */
public class CurrencyAddSubCommand extends CurrencySubCommand
{

    private final Currency currency;

    public CurrencyAddSubCommand(Currency currency)
    {
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
    public void onCommand(CommandSender sender, String[] args)
    {
        if (!sender.hasPermission("economy.commands.money.add")) {
            return;
        }

        new EconomyExecuteAction(sender, this.currency, args)
        {
            @Override
            public void execute(User targetUser, Currency currency, Double amount)
            {
                EconomyAPI.add(targetUser, currency, amount);

                sender.sendMessage(String.format(
                        "§aVocê adicionou §f%s §ano saldo de §f%s§a.",
                        currency.format(amount),
                        targetUser.getName()
                ));
            }
        };
    }

}
