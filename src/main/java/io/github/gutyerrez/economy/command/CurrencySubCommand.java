package io.github.gutyerrez.economy.command;

import io.github.gutyerrez.core.shared.commands.CommandRestriction;
import io.github.gutyerrez.core.spigot.commands.CustomCommand;

/**
 * @author SrGutyerrez
 */
public class CurrencySubCommand extends CustomCommand {

    public CurrencySubCommand(String name, CommandRestriction restriction, String... aliases) {
        super(name, restriction, aliases);
    }

}
