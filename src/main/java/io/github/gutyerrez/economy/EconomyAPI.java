package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.events.CurrencyChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

/**
 * @author SrGutyerrez
 */
public class EconomyAPI
{

    public static String MAGNATA_USERNAME = "";

    public static void add(User user, Currency currency, BigDecimal value)
    {
        BigDecimal balance = EconomyAPI.get(user, currency);
        BigDecimal newValue = EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, value);

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                newValue
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static boolean remove(User user, Currency currency, BigDecimal value)
    {
        BigDecimal balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0 && balance.compareTo(value) > 0) {
            BigDecimal newValue = balance.subtract(value.compareTo(BigDecimal.ZERO) > 0 ? balance : value);

            Player player = Bukkit.getPlayerExact(user.getName());

            CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                    player,
                    currency,
                    balance,
                    newValue
            );

            Bukkit.getPluginManager().callEvent(currencyChangeEvent);

            EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, balance.subtract(newValue));
            EconomyProvider.Cache.Local.CURRENCY.provide().add(
                    user.getUniqueId(),
                    currency,
                    EconomyAPI.get(
                            user,
                            currency
                    ).subtract(newValue)
            );
            return true;
        }

        return false;
    }

    public static void set(User user, Currency currency, BigDecimal value)
    {
        BigDecimal balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        BigDecimal newValue = balance.compareTo(value) < 0 ? (value.subtract(balance)) : balance.subtract(value);

        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, newValue);
        EconomyProvider.Cache.Local.CURRENCY.provide().add(
                user.getUniqueId(),
                currency,
                EconomyAPI.get(
                        user,
                        currency
                ).add(newValue)
        );

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                newValue
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static BigDecimal get(User user, Currency currency)
    {
        return EconomyProvider.Cache.Local.CURRENCY.provide().get(user.getUniqueId(), currency);
    }

}
