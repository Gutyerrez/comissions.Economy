package io.github.gutyerrez.economy.misc.utils;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

/**
 * @author SrGutyerrez
 */
public abstract class EconomyExecuteAction
{

    private Boolean canBeExecuted = true;

    public EconomyExecuteAction(CommandSender sender, Currency currency, String[] args)
    {
        User targetUser = CoreProvider.Cache.Local.USERS.provide().get(args[0]);

        if (targetUser == null) {
            sender.sendMessage("§cEste usuário não existe.");

            this.canBeExecuted = false;
        }

        BigDecimal amount = null;

        try {
             amount = new BigDecimal(args[1]);
        } catch (Exception ignored) { }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            sender.sendMessage("§cVocê informou um valor inválido.");

            this.canBeExecuted = false;
        }

        this.executeRaw(targetUser, currency, amount);
    }

    public final void executeRaw(User targetUser, Currency currency, BigDecimal amount)
    {
        if (this.canBeExecuted) {
            this.execute(targetUser, currency, amount);
        }
    }

    public abstract void execute(User targetUser, Currency currency, BigDecimal amount);

}
