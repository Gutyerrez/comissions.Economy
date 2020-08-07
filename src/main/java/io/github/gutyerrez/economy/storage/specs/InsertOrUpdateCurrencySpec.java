package io.github.gutyerrez.economy.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class InsertOrUpdateCurrencySpec extends InsertSqlSpec<Void> {

    private final User user;
    private final Currency currency;
    private final Double value;

    @Override
    public Void parser(int affectedRows, ResultSet generatedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` (`username`,`value`) VALUES (?,?) ON DUPLICATE KEY UPDATE `value`=`value` + ?;",
                    this.currency.getTableName()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, this.user.getName().toLowerCase());
            preparedStatement.setDouble(2, this.value);
            preparedStatement.setDouble(3, this.value);

            return preparedStatement;
        };
    }
}
