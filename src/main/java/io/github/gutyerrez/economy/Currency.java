package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

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

    public String format(BigDecimal v)
    {
        if (v == null) {
            return "0" + this.singular;
        }

        if (EconomyPlugin.getInstance().getConfig().getBoolean("settings.enabled_k_format")) {
            return String.format("%s %s", NumberUtils.toK(v), (v.compareTo(BigDecimal.ONE) > 0 ? this.plural : this.singular));
        } else {
            return String.format("%s %s", NumberUtils.format(v.doubleValue()), (v.compareTo(BigDecimal.ONE) > 0 ? this.plural : this.singular));
        }
    }

}
