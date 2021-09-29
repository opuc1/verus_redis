package me.opuc1;

import me.opuc1.config.BukkitConfig;
import me.opuc1.json.JsonConfigBuilder;
import me.opuc1.listener.PunishmentListener;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisController;
import me.opuc1.redis.RedisManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitPlugin extends JavaPlugin {
    private RedisManager manager;

    public void onEnable() {
        File file = new File(getDataFolder(), "config.json");
        JsonConfigBuilder builder = new JsonConfigBuilder(file, new BukkitConfig());

        builder.makeParent();

        BukkitConfig config = (BukkitConfig) builder.loadFile();
        getLogger().info("Loaded configuration.");

        manager = new RedisManager(
                config.getRedis().getHost(),
                config.getRedis().getPort(),
                config.getRedis().getPassword()
        );

        if (config.getPunishments().isEnabled()) {
            RedisController<Punishment> controller = new RedisController<>(manager, config.getPunishments().getChannel());
            PunishmentListener listener = new PunishmentListener(controller);
            getServer().getPluginManager().registerEvents(listener, this);
            getLogger().info("Registered Punishment listener.");
        }
    }

    public void onDisable() {
        if (manager != null) {
            manager.shutdown();
        }
    }
}
