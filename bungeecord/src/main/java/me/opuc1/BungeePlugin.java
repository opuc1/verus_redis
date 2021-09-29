package me.opuc1;

import me.opuc1.config.BungeeConfig;
import me.opuc1.handler.PunishmentHandler;
import me.opuc1.json.JsonConfigBuilder;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public class BungeePlugin extends Plugin {
    private Logger logger;
    private RedisManager manager;

    public void onEnable() {
        logger = getProxy().getLogger();

        File file = new File(getDataFolder(), "config.json");
        JsonConfigBuilder builder = new JsonConfigBuilder(file, new BungeeConfig());

        builder.makeParent();

        BungeeConfig config = (BungeeConfig) builder.loadFile();
        logger.info("Loaded configuration.");

        manager = new RedisManager(
                config.getRedis().getHost(),
                config.getRedis().getPort(),
                config.getRedis().getPassword()
        );

        if (config.getPunishments().isEnabled()) {
            String channel = config.getPunishments().getChannel();
            manager.subscribe(Punishment.class,
                    config.getPunishments().getChannel(), new PunishmentHandler(this));
            logger.info("Subscribed to " + channel + ".");
        }
    }

    public void onDisable() {
        if (manager != null) {
            manager.shutdown();
            logger.info("Shutdown.");
        }
    }
}
