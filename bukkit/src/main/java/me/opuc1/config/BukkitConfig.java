package me.opuc1.config;

import lombok.Getter;
import me.opuc1.json.JsonConfig;

@Getter
public class BukkitConfig extends JsonConfig {
    private final Server server = new Server();
    private final Redis redis = new Redis();
    private final Punishments punishments = new Punishments();

    @Getter
    public static class Server {
        private final String name = "";
    }

    @Getter
    public static class Redis {
        private final String host = "localhost", password = "";
        private final int port = 6379;
    }

    @Getter
    public static class Punishments {
        private final String channel = "verus:punish";
        private final boolean enabled = false;
    }
}
