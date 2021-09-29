package me.opuc1.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public abstract class CheckMessage extends RedisMessage {
    private final String serverName, name, type, subType, display;
}
