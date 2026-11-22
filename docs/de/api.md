---
title: HTTP-API
description: Alle Endpunkte von minecraft-api mit Feldern und Beispielantworten – Spieler, Dimensionen, Entities und geladene Chunks.
order: 30
slug: api
section: Benutzung
---

Alle Endpunkte antworten auf `GET` mit JSON (`Content-Type: application/json`). Es gibt keine Parameter, keine
Paginierung und keinen Cache: Jeder Aufruf liest den aktuellen Zustand des Servers. Unbekannte Pfade liefern dieselbe
Antwort wie `/`.

| Pfad             | Inhalt                                          |
|------------------|-------------------------------------------------|
| `/`              | Spieler und Dimensionen in einer Antwort        |
| `/players`       | Online-Spieler                                  |
| `/dimensions`    | Dimensionen mit Spawnpunkt und Weltgrenze       |
| `/entities`      | alle Entities aller Dimensionen                 |
| `/chunks/active` | alle geladenen Chunks aller Dimensionen         |

## `/`

Fasst `/players` und `/dimensions` zusammen:

```json
{
    "players":    { "max": 20, "players": { } },
    "dimensions": [ ]
}
```

## `/players`

`max` ist die maximale Spielerzahl des Servers, `players` ein Objekt mit der UUID als Schlüssel.

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

`gameMode` ist die Zahl des Spielmodus: `0` Überleben, `1` Kreativ, `2` Abenteuer, `3` Zuschauer. `armor` sind die
Rüstungspunkte (0 bis 30), `health` die Lebenspunkte (20 = zehn Herzen).

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

Dimensionen aus anderen Mods oder Datapacks erscheinen mit ihrer eigenen ID.

## `/entities`

Eine Liste aller Entities, die der Server gerade geladen hat – auch Spieler, Items, Erfahrungskugeln, Loren und
Rüstungsständer.

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

Das zweite Beispiel ist gekürzt; Items haben dieselben Felder wie alle anderen Entities und zusätzlich `item`.

| Feld         | Bedeutung                                                                                                                                       |
|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`       | Registry-ID der Entity                                                                                                                          |
| `category`   | Mob-Kategorie von Minecraft: `monster`, `creature`, `ambient`, `axolotls`, `underground_water_creature`, `water_creature`, `water_ambient`, `misc` – für Items `item` |
| `name`       | Anzeigename des Typs in der Sprache des Servers                                                                                                 |
| `customName` | per Namensschild vergebener Name oder `null`                                                                                                    |
| `born.at`    | erster Auftritt in der Welt: `timestamp` in Millisekunden seit 1970, `gameTime` in Ticks                                                          |
| `tags`       | Tags aus `/tag`                                                                                                                                 |
| `chunk`      | der Chunk, in dem die Entity steht, im Format von `/chunks/active`                                                                              |

`born` wird beim ersten Betreten der Welt in den Daten der Entity gespeichert und übersteht damit Neustarts. Entities,
die schon vor der Installation der Mod existierten, bekommen beim nächsten Laden den Zeitpunkt des Ladens.

## `/chunks/active`

Alle Chunks, die der Server im Speicher hält:

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

`position` enthält die Blockkoordinaten der beiden Ecken. `status` sagt, wie viel in dem Chunk gerade passiert:

| Status           | Bedeutung                                                         |
|------------------|-------------------------------------------------------------------|
| `ENTITY_TICKING` | voll aktiv: Mobs bewegen sich, Farmen laufen                      |
| `BLOCK_TICKING`  | Blöcke und Redstone laufen, Entities stehen still                 |
| `FULL`           | geladen, aber nichts wird berechnet                               |
| `INACCESSIBLE`   | im Ladevorgang oder gerade entladen                               |
| `UNKNOWN`        | der Chunk ist nicht mehr im Speicher                              |

## Beispiel

Alle Monster in der Oberwelt zählen, mit `jq`:

```bash
curl -s http://localhost:9874/entities \
  | jq '[.[] | select(.dimension == "minecraft:overworld" and .category == "monster")] | length'
```
