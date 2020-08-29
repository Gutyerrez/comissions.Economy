package io.github.gutyerrez.economy.storage;

import io.github.gutyerrez.core.shared.storage.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import io.github.gutyerrez.economy.storage.specs.CreateCurrencyTableSpec;
import io.github.gutyerrez.economy.storage.specs.InsertOrUpdateCurrencySpec;
import io.github.gutyerrez.economy.storage.specs.SelectCurrencySpec;
import io.github.gutyerrez.economy.storage.specs.SelectCurrencyTopSpec;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class EconomyRepository extends MysqlRepository
{

    public EconomyRepository(MysqlDatabaseProvider databaseProvider)
    {
        super(databaseProvider);

        for (Currency currency : Currency.values()) {
            this.createTable(currency);
        }
    }

    private void createTable(Currency currency)
    {
        this.query(new CreateCurrencyTableSpec(currency));
    }

    public BigDecimal get(User user, Currency currency)
    {
        return query(new SelectCurrencySpec(user, currency));
    }

    public BigDecimal update(User user, Currency currency, BigDecimal value)
    {
        return this.query(new InsertOrUpdateCurrencySpec(user, currency, value));
    }

    public LinkedHashMap<String, BigDecimal> fetchTop(Currency currency)
    {
        return this.query(new SelectCurrencyTopSpec(currency));
    }

}
