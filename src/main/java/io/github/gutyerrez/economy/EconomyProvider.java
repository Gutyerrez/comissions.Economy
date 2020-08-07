package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.economy.misc.hooks.ChatHook;
import io.github.gutyerrez.economy.storage.EconomyRepository;

/**
 * @author SrGutyerrez
 */
public class EconomyProvider {

    public static void prepare() {
        EconomyProvider.Repositories.ECONOMY.prepare();

        Hooks.CHAT.prepare();
    }

    public static class Hooks {

        public static ChatHook<?> CHAT = new ChatHook<>();

    }

    public static class Repositories {

        public static MysqlRepositoryProvider<EconomyRepository> ECONOMY = new MysqlRepositoryProvider<>(
                () -> CoreProvider.Database.MySQL.MYSQL_MAIN,
                EconomyRepository.class
        );

    }

}
