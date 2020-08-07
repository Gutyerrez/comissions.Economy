package io.github.gutyerrez.economy.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ExecuteSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCallback;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class CreateCurrencyTableSpec extends ExecuteSqlSpec<Void> {

    private final Currency currency;

    @Override
    public PreparedStatementCallback<Void> getPreparedStatementCallback() {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS `%s` (`unique_id` VARCHAR(32) PRIMARY KEY, `value` DOUBLE);",
                    this.currency.getTableName()
            );

            return connection.prepareStatement(query);
        };
    }
}
