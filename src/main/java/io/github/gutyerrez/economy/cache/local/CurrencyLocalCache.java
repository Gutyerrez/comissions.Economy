package io.github.gutyerrez.economy.cache.local;

import com.google.common.collect.HashBasedTable;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.EconomyProvider;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class CurrencyLocalCache implements LocalCache
{

    private final HashBasedTable<UUID, Currency, BigDecimal> CACHE = HashBasedTable.create();

    public BigDecimal get(UUID uuid, Currency currency)
    {
        if (this.CACHE.containsColumn(uuid)) {
            return this.CACHE.get(uuid, currency);
        } else {
            return EconomyProvider.Repositories.ECONOMY.provide().get(
                    CoreProvider.Cache.Local.USERS.provide().get(uuid),
                    currency
            );
        }
    }

    public void add(UUID uuid, Currency currency, BigDecimal value)
    {
        this.CACHE.put(uuid, currency, value);
    }

    public void remove(UUID uuid, Currency currency)
    {
        this.CACHE.remove(uuid, currency);
    }

}
