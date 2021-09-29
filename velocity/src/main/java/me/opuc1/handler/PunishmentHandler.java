package me.opuc1.handler;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import me.opuc1.VelocityPlugin;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisHandler;

@RequiredArgsConstructor
public class PunishmentHandler implements RedisHandler<Punishment> {
    private final VelocityPlugin plugin;

    public void handle(Punishment punishment) {
        ProxyServer server = plugin.getServer();
        ConsoleCommandSource console = server.getConsoleCommandSource();
        CommandManager commandManager = server.getCommandManager();
        String serverName = punishment.getServerName();

        punishment.getCommands().forEach(command -> {
            commandManager.executeAsync(console, command);
            plugin.getLogger().info(String.format("(%s) Executing %s", serverName, command));
        });
    }
}
