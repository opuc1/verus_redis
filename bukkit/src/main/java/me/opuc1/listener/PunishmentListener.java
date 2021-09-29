package me.opuc1.listener;

import lombok.RequiredArgsConstructor;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.events.PunishEvent;
import me.opuc1.redis.Punishment;
import me.opuc1.redis.RedisController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class PunishmentListener implements Listener {
    private final RedisController<Punishment> controller;

    @EventHandler
    public void onPunish(PunishEvent event) {
        Check check = event.getCheck();

        controller.publish(new Punishment(
                "",
                event.getPlayer().getName(),
                check.getType(), check.getSubType(), check.getDisplay(),
                event.getCommands(), event.isAnnounce()
        ));
    }
}
