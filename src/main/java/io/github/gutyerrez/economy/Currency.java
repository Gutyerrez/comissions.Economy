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
public enum Currency {

    COINS(
            "player_money",
            "money",
            new String[]{
                    "coins"
            },
            "coin",
            "coins",
            ChatColor.GREEN
    );

    private final String tableName;
    private final String commandName;
    private final String[] commandAliases;
    private final String singular;
    private final String plural;
    private final ChatColor color;

    public String format(Double v) {
        return NumberUtils.format(v);
    }

}
