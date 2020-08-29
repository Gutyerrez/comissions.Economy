package io.github.gutyerrez.economy.storage.specs;

import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.storage.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.storage.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class SelectCurrencyTopSpec extends SelectSqlSpec<LinkedHashMap<String, BigDecimal>>
{

    private final Currency currency;

    @Override
    public ResultSetExtractor<LinkedHashMap<String, BigDecimal>> getResultSetExtractor()
    {
        return resultSet -> {
            LinkedHashMap<String, BigDecimal> out = Maps.newLinkedHashMap();

            while (resultSet.next()) {
                out.put(
                        resultSet.getString("username"),
                        new BigDecimal(
                                resultSet.getString("value")
                        )
                );
            }

            return out;
        };
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator()
    {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s` ORDER BY `value` DESC LIMIT 10;",
                    this.currency.getTableName()
            );

            return connection.prepareStatement(query);
        };
    }

}
