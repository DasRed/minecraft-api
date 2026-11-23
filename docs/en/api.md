---
title: HTTP API
description: All HTTP endpoints of minecraft-api with fields and example JSON responses, for players, dimensions, entities and the loaded chunks of your Minecraft server.
order: 30
slug: api
section: Usage
---

All endpoints answer `GET` with JSON (`Content-Type: application/json`). There are no parameters, no pagination and no
cache: every request reads the current state of the server. Unknown paths return the same response as `/`.

| Path             | Content                                          |
|------------------|--------------------------------------------------|
| `/`              | players and dimensions in one response           |
| `/players`       | online players                                   |
| `/dimensions`    | dimensions with spawn point and world border     |
| `/entities`      | all entities of all dimensions                   |
| `/chunks/active` | all loaded chunks of all dimensions              |

## `/`

Combines `/players` and `/dimensions`:

```json
{
    "players":    { "max": 20, "players": { } },
    "dimensions": [ ]
}
```

## `/players`

`max` is the maximum number of players of the server, `players` an object keyed by UUID.

```json
{
    "max":     20,
    "players": {
        "0f6a…-…": {
            "uuid":            "0f6a…-…",
            "name":            "Steve",
            "dimension":       "minecraft:overworld",
            "x":               -112.5,
            "y":               71.0,
            "z":               48.3,
            "yHeadRot":        93.4,
            "armor":           20,
            "health":          20.0,
            "gameMode":        0,
            "gameModeName":    "survival",
            "isInvisible":     false,
            "experienceLevel": 30
        }
    }
}
```

`gameMode` is the number of the game mode: `0` survival, `1` creative, `2` adventure, `3` spectator. `armor` are the
armor points (0 to 30), `health` the health points (20 = ten hearts).

## `/dimensions`

```json
[
    {
        "id":     "minecraft:overworld",
        "spawn":  { "x": 0, "y": 64, "z": 0 },
        "border": {
            "x":      { "min": -29999984.0, "max": 29999984.0 },
            "z":      { "min": -29999984.0, "max": 29999984.0 },
            "center": { "x": 0.0, "z": 0.0 }
        }
    }
]
```

Dimensions from other mods or datapacks show up with their own ID.

## `/entities`

A list of all entities the server currently has loaded – including players, items, experience orbs, minecarts and
armor stands.

```json
[
    {
        "uuid":       "5b2c…-…",
        "type":       "minecraft:zombie",
        "category":   "monster",
        "name":       "Zombie",
        "customName": null,
        "dimension":  "minecraft:overworld",
        "x":          -98.7,
        "y":          63.0,
        "z":          51.2,
        "born":       { "at": { "timestamp": 1765530000000, "gameTime": 1834210 } },
        "tags":       [ ],
        "chunk":      { "id": "minecraft:overworld[-112 | 48] - [-97 | 63]", "dimension": "minecraft:overworld", "status": "ENTITY_TICKING", "position": { "min": { "x": -112, "z": 48 }, "max": { "x": -97, "z": 63 } } }
    },
    {
        "uuid":     "a91e…-…",
        "type":     "minecraft:item",
        "category": "item",
        "name":     "Item",
        "item":     { "name": "Rotten Flesh", "type": "minecraft:rotten_flesh", "count": 3 }
    }
]
```

The second example is shortened; items have the same fields as every other entity plus `item`.

| Field        | Meaning                                                                                                                                         |
|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`       | registry ID of the entity                                                                                                                       |
| `category`   | Minecraft mob category: `monster`, `creature`, `ambient`, `axolotls`, `underground_water_creature`, `water_creature`, `water_ambient`, `misc` – `item` for items |
| `name`       | display name of the type in the server's language                                                                                               |
| `customName` | name given with a name tag, or `null`                                                                                                           |
| `born.at`    | first appearance in the world: `timestamp` in milliseconds since 1970, `gameTime` in ticks                                                        |
| `tags`       | tags from `/tag`                                                                                                                                |
| `chunk`      | the chunk the entity stands in, in the format of `/chunks/active`                                                                               |

`born` is stored in the entity's data when it first joins the world, so it survives restarts. Entities that existed
before the mod was installed get the time they are next loaded.

## `/chunks/active`

All chunks the server keeps in memory:

```json
[
    {
        "id":        "minecraft:the_nether[16 | -32] - [31 | -17]",
        "dimension": "minecraft:the_nether",
        "status":    "BLOCK_TICKING",
        "position":  { "min": { "x": 16, "z": -32 }, "max": { "x": 31, "z": -17 } }
    }
]
```

`position` holds the block coordinates of both corners. `status` tells how much is happening in the chunk:

| Status           | Meaning                                                           |
|------------------|-------------------------------------------------------------------|
| `ENTITY_TICKING` | fully active: mobs move, farms run                                |
| `BLOCK_TICKING`  | blocks and redstone run, entities stand still                     |
| `FULL`           | loaded, but nothing is processed                                  |
| `INACCESSIBLE`   | loading or just unloaded                                          |
| `UNKNOWN`        | the chunk is no longer in memory                                  |

## Example

Count all monsters in the overworld with `jq`:

```bash
curl -s http://localhost:9874/entities \
  | jq '[.[] | select(.dimension == "minecraft:overworld" and .category == "monster")] | length'
```
