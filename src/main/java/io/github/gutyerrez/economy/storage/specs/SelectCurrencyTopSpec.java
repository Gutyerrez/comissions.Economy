package io.github.gutyerrez.economy.storage.specs;

import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class SelectCurrencyTopSpec extends SelectSqlSpec<LinkedHashMap<UUID, Double>> {

    private final Currency currency;

    @Override
    public ResultSetExtractor<LinkedHashMap<UUID, Double>> getResultSetExtractor() {
        return resultSet -> {
            LinkedHashMap<UUID, Double> out = Maps.newLinkedHashMap();

            while (resultSet.next()) {
                out.put(
                        UUID.fromString(
                                resultSet.getString("unique_id")
                        ),
                        resultSet.getDouble("value")
                );
            }

            return out;
        };
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s` ORDER BY `value` DESC LIMIT 10;",
                    this.currency.getTableName()
            );

            return connection.prepareStatement(query);
        };
    }

}
