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

        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, balance.add(value));
        EconomyProvider.Cache.Local.CURRENCY.provide().add(user.getUniqueId(), currency, balance.add(value));

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                balance.add(value)
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static boolean remove(User user, Currency currency, BigDecimal value)
    {
        BigDecimal balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, balance.subtract(value));
        EconomyProvider.Cache.Local.CURRENCY.provide().add(user.getUniqueId(), currency, balance.subtract(value));

        BigDecimal newValue = balance.subtract(value.compareTo(BigDecimal.ZERO) > 0 ? balance : value);

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                newValue
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
        return true;
    }

    public static void set(User user, Currency currency, BigDecimal value)
    {
        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, value);
        EconomyProvider.Cache.Local.CURRENCY.provide().add(user.getUniqueId(), currency, value);

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                BigDecimal.ZERO,
                value
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static BigDecimal get(User user, Currency currency)
    {
        return EconomyProvider.Cache.Local.CURRENCY.provide().get(user.getUniqueId(), currency);
    }

}
