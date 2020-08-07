package io.github.gutyerrez.economy.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class SelectCurrencySpec extends SelectSqlSpec<Double> {

    private final User user;
    private final Currency currency;

    @Override
    public ResultSetExtractor<Double> getResultSetExtractor() {
        return resultSet -> resultSet.next() ? resultSet.getDouble("value") : 0;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s` WHERE `unique_id`=?;",
                    currency.getTableName()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.user.getUniqueId().toString());

            return preparedStatement;
        };
    }
}
