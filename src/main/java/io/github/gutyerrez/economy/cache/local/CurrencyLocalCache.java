package io.github.gutyerrez.economy.cache.local;

import com.google.common.collect.HashBasedTable;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.economy.Currency;

import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class CurrencyLocalCache implements LocalCache
{

    private final HashBasedTable<UUID, Currency, Double> CACHE = HashBasedTable.create();

    public Double get(UUID uuid, Currency currency)
    {
        return this.CACHE.get(uuid, currency);
    }

    public void add(UUID uuid, Currency currency, Double value)
    {
        this.CACHE.put(uuid, currency, value);
    }

    public void remove(UUID uuid, Currency currency)
    {
        this.CACHE.remove(uuid, currency);
    }

}
