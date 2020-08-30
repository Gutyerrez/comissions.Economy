package io.github.gutyerrez.economy.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.user.User;
import io.github.gutyerrez.economy.Currency;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class InsertOrUpdateCurrencySpec extends InsertSqlSpec<Void>
{

    private final User user;
    private final Currency currency;
    private final BigDecimal value;

    @Override
    public Void parser(int affectedRows, ResultSet keyHolder) throws SQLException
    {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator()
    {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` (`username`,`value`) VALUES (?,?) ON DUPLICATE KEY UPDATE `value`=VALUES(`value`);",
                    this.currency.getTableName()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, this.user.getName());
            preparedStatement.setString(2, this.value.toString());

            return preparedStatement;
        };
    }

}
