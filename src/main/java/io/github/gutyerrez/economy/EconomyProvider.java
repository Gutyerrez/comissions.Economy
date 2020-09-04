package io.github.gutyerrez.economy;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.economy.cache.local.CurrencyLocalCache;
import io.github.gutyerrez.economy.misc.hooks.VaultChatHook;
import io.github.gutyerrez.economy.storage.EconomyRepository;

/**
 * @author SrGutyerrez
 */
public class EconomyProvider {

    public static void prepare() {
        EconomyProvider.Repositories.ECONOMY.prepare();

        Hooks.CHAT.prepare();

        Cache.Local.CURRENCY.prepare();
    }

    public static class Hooks {

        //public static LuckPermsHook<?> CHAT = new LuckPermsHook<>();
        public static final VaultChatHook<?> CHAT = new VaultChatHook<>();
    }

    public static class Cache {

        public static class Local {

            public static final LocalCacheProvider<CurrencyLocalCache> CURRENCY = new LocalCacheProvider<>(
                    new CurrencyLocalCache()
            );

        }

    }

    public static class Repositories {

        public static MysqlRepositoryProvider<EconomyRepository> ECONOMY = new MysqlRepositoryProvider<>(
                () -> CoreProvider.Database.MySQL.MYSQL_MAIN,
                EconomyRepository.class
        );

    }

}
