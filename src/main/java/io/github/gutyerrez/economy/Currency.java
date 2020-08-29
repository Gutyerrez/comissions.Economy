package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public enum Currency
{

    COINS(
            "player_money",
            "money",
            new String[]{
                    "coins"
            },
            "Coin",
            "Coins",
            ChatColor.YELLOW
    );

    private final String tableName;
    private final String commandName;
    private final String[] commandAliases;
    private final String singular;
    private final String plural;
    private final ChatColor color;

    public String format(Double v)
    {
        return String.format("%s %s", NumberUtils.format(v), (v > 1 ? this.plural : this.singular));
    }

}
