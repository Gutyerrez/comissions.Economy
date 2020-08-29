package io.github.gutyerrez.economy.misc.integration;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyAPI;
import io.github.gutyerrez.economy.EconomyPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author SrGutyerrez
 */
public class VaultEconomy implements Economy
{

    @Override
    public boolean isEnabled()
    {
        return EconomyPlugin.getInstance().isEnabled();
    }

    @Override
    public String getName()
    {
        return EconomyPlugin.getInstance().getName();
    }

    @Override
    public boolean hasBankSupport()
    {
        return false;
    }

    @Override
    public int fractionalDigits()
    {
        return 0;
    }

    @Override
    public String format(double v)
    {
        return NumberUtils.format(v);
    }

    @Override
    public String currencyNamePlural()
    {
        return null;
    }

    @Override
    public String currencyNameSingular()
    {
        return null;
    }

    @Override
    public boolean hasAccount(String s)
    {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer)
    {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1)
    {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s)
    {
        return false;
    }

    @Override
    public double getBalance(String s)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        BigDecimal value = EconomyAPI.get(user, Currency.COINS);

        return (value == null ? BigDecimal.ZERO : value).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        BigDecimal value = EconomyAPI.get(user, Currency.COINS);

        return (value == null ? BigDecimal.ZERO : value).doubleValue();
    }

    @Override
    public double getBalance(String s, String s1)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        BigDecimal value = EconomyAPI.get(user, Currency.COINS);

        return (value == null ? BigDecimal.ZERO : value).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        BigDecimal value = EconomyAPI.get(user, Currency.COINS);

        return (value == null ? BigDecimal.ZERO : value).doubleValue();
    }

    @Override
    public boolean has(String s, double v)
    {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v)
    {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v)
    {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v)
    {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        if (EconomyAPI.remove(user, Currency.COINS, new BigDecimal(v))) {
            return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
        }

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.FAILURE, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        if (EconomyAPI.remove(user, Currency.COINS, new BigDecimal(v))) {
            return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
        }

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.FAILURE, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        if (EconomyAPI.remove(user, Currency.COINS, new BigDecimal(v))) {
            return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
        }

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.FAILURE, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        if (EconomyAPI.remove(user, Currency.COINS, new BigDecimal(v))) {
            return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
        }

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.FAILURE, null);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        EconomyAPI.add(user, Currency.COINS, new BigDecimal(v));

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        EconomyAPI.add(user, Currency.COINS, new BigDecimal(v));

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(s);

        EconomyAPI.add(user, Currency.COINS, new BigDecimal(v));

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v)
    {
        User user = CoreProvider.Cache.Local.USERS.provide().get(offlinePlayer.getName());

        EconomyAPI.add(user, Currency.COINS, new BigDecimal(v));

        return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse createBank(String s, String s1)
    {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer)
    {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s)
    {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s)
    {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v)
    {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v)
    {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v)
    {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1)
    {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer)
    {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1)
    {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer)
    {
        return null;
    }

    @Override
    public List<String> getBanks()
    {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s)
    {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer)
    {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1)
    {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s)
    {
        return false;
    }

}
