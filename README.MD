# Verus AC | Redis Addon

Forward commands to your BungeeCord/Velocity proxy via Redis.

## Features
* Punishment command(s) forwarding

## Requirements
* Verus (https://verus.ac)
* Verus API (Download via https://verus.ac/dashboard)

## Installation
1. Install the bukkit implementation and the VerusAPI onto your server.
2. Install the corresponding proxy implementation depending on which proxy software you are using.
3. Configure plugins using the generated config.json files, making sure "punish" is set to true.

**Default Configuration**
```json
{
  "redisData": {
    "host": "localhost",
    "port": 6379,
    "password": "",
    "punishChannel": "verus:punish",
    "punish": false
  }
}
```

## Future Releases
* MC|Brand Forwarding
* Alert Forwarding


