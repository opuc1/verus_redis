package me.opuc1;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.opuc1.config.VelocityConfig;
import me.opuc1.handler.PunishmentHandler;
import me.opuc1.json.JsonConfigBuilder;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisManager;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "verus_redis_velocity",
        name = "verus_redis_velocity",
        version = "1.0",
        url = "https://verus.ac",
        authors = {
                "opuc1"
        }
)
@Getter
public class VelocityPlugin {
    private final ProxyServer server;
    private final Logger logger;
    private final Path directory;

    private RedisManager manager;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path directory) {
        this.server = server;
        this.logger = logger;
        this.directory = directory;
    }

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        File folder = directory.toFile();
        File file = new File(folder, "config.json");

        JsonConfigBuilder builder = new JsonConfigBuilder(file, new VelocityConfig());

        builder.makeParent();

        VelocityConfig config = (VelocityConfig) builder.loadFile();
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

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (manager != null) {
            manager.shutdown();
            logger.info("Shutdown.");
        }
    }
}
