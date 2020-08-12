package io.github.gutyerrez.economy.events;

import io.github.gutyerrez.economy.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class CurrencyChangeEvent extends Event {

    @Getter
    private static HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Currency currency;
    private final Double oldValue;
    private final Double newValue;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
