package me.opuc1.handler;

import lombok.RequiredArgsConstructor;
import me.opuc1.BungeePlugin;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisHandler;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class PunishmentHandler implements RedisHandler<Punishment> {
    private final BungeePlugin plugin;

    public void handle(Punishment message) {
        ProxyServer proxy = plugin.getProxy();
        PluginManager pluginManager = proxy.getPluginManager();
        CommandSender console = proxy.getConsole();
        Logger logger = plugin.getLogger();
        String serverName = message.getServerName();

        message.getCommands().forEach(command -> {
            pluginManager.dispatchCommand(console, command);
            logger.log(Level.INFO, String.format("(%s) Executing %s", serverName, command));
        });
    }
}
