package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.user.User;

/**
 * @author SrGutyerrez
 */
public class EconomyAPI {

    public static String MAGNATA_USERNAME = null;

    public static void add(User user, Currency currency, Double value) {
        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, value);
    }

    public static boolean remove(User user, Currency currency, Double value) {

        Double balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance != null && balance > 0 && !value.isInfinite() && !value.isNaN() && balance >= value) {
            Double newValue = (balance - value) <= 0 ? balance : value;
            EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, -newValue);
            return true;
        }

        return false;
    }

    public static void set(User user, Currency currency, Double value) {
        Double balance = EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);

        if (balance == null) {
            balance = 0.0;
        }

        double newValue = balance < value ? (value - balance) : -(balance - value);
        EconomyProvider.Repositories.ECONOMY.provide().update(user, currency, newValue);
    }

    public static Double get(User user, Currency currency) {
        return EconomyProvider.Repositories.ECONOMY.provide().get(user, currency);
    }

}
