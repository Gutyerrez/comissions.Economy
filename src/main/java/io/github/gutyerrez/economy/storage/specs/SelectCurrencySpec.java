package io.github.gutyerrez.economy.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.storage.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.storage.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class SelectCurrencySpec extends SelectSqlSpec<BigDecimal>
{

    private final User user;
    private final Currency currency;

    @Override
    public ResultSetExtractor<BigDecimal> getResultSetExtractor()
    {
        return resultSet -> resultSet.next() ? resultSet.getBigDecimal("value") : null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator()
    {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s` WHERE `username`=?;",
                    currency.getTableName()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.user.getName().toLowerCase());

            return preparedStatement;
        };
    }

}
