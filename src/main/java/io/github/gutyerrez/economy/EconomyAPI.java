package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.events.CurrencyChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class EconomyAPI
{

    public static String MAGNATA_USERNAME = "";

    public static void add(User user, Currency currency, Double value)
    {
        Double balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);
        Double newValue = EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, value);

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                newValue
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static boolean remove(User user, Currency currency, Double value)
    {
        Double balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance != null && balance > 0 && !value.isInfinite() && !value.isNaN() && balance >= value) {
            Double newValue = (balance - value) <= 0 ? balance : value;

            Player player = Bukkit.getPlayerExact(user.getName());

            CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                    player,
                    currency,
                    balance,
                    newValue
            );

            Bukkit.getPluginManager().callEvent(currencyChangeEvent);

            EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, -newValue);
            return true;
        }

        return false;
    }

    public static void set(User user, Currency currency, Double value)
    {
        Double balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance == null) {
            balance = 0.0;
        }

        double newValue = balance < value ? (value - balance) : -(balance - value);
        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, newValue);

        Player player = Bukkit.getPlayerExact(user.getName());

        CurrencyChangeEvent currencyChangeEvent = new CurrencyChangeEvent(
                player,
                currency,
                balance,
                newValue
        );

        Bukkit.getPluginManager().callEvent(currencyChangeEvent);
    }

    public static Double get(User user, Currency currency)
    {
//        return EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);
        return EconomyProvider.Cache.Local.CURRENCY.provide().get(user.getUniqueId(), currency);
    }

}
