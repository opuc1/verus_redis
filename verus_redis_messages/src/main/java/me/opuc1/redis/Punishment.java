package me.opuc1.redis;

import lombok.Getter;

import java.util.List;

@Getter
public class Punishment extends CheckMessage {
    private final List<String> commands;
    private final boolean announce;

    public Punishment(String serverName, String name, String checkType, String checkSubType,
                      String checkDisplay, List<String> commands, boolean announce) {
        super(serverName, name, checkType, checkSubType, checkDisplay);
        this.commands = commands;
        this.announce = announce;
    }
}
