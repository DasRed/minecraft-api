---
title: Überblick
description: NeoForge-Mod, die Spieler, Dimensionen, geladene Chunks und Entities eines Minecraft-Servers als JSON per HTTP bereitstellt.
order: 0
section: Einstieg
---

`minecraft-api` ist eine kleine Server-Mod für NeoForge. Sie startet zusammen mit dem Minecraft-Server einen
HTTP-Server und liefert dort den aktuellen Zustand der Welt als JSON:

- **Spieler:** Name, Dimension, Position, Blickrichtung, Leben, Rüstung, Spielmodus und Level
- **Dimensionen:** Spawnpunkt und Weltgrenze jeder Dimension
- **Geladene Chunks:** alle Chunks, die der Server gerade im Speicher hat, mit ihrem Status
- **Entities:** jedes Mob, Item und Fahrzeug mit Typ, Kategorie, Position, Chunk, Tags und – bei Items – Anzahl

Zusätzlich merkt sich die Mod für jede Entity, wann sie zum ersten Mal in der Welt aufgetaucht ist (Uhrzeit und
Spielzeit). So lässt sich später erkennen, welche Mobs schon ewig herumstehen.

Entstanden ist die Mod als Datenquelle für [minecraft-entity-view](https://www.marco-starker.de/de/projekte/minecraft-entity-view),
eine Live-Karte aller Entities im Browser. Die API ist aber bewusst schlicht gehalten und lässt sich genauso von eigenen
Skripten, Dashboards oder Home Assistant abfragen.

## Kompatibilität

| Spiel                    | Loader                        |
|--------------------------|-------------------------------|
| Minecraft 1.21.8 (genau) | NeoForge 21.8.33 oder neuer   |

## Status

> [!IMPORTANT]
> Die Mod wird nicht mehr weiterentwickelt. Sie funktioniert mit Minecraft 1.21.8, Anpassungen an neuere Versionen
> sind nicht geplant. Der Code bleibt öffentlich – Forks sind willkommen.

## Weiter

- [Installation](installation.md): Mod bauen oder herunterladen und auf den Server legen
- [Konfiguration](configuration.md): Port und Absicherung
- [HTTP-API](api.md): alle Endpunkte mit Beispielantworten

## Verwandte Projekte

- [minecraft-entity-view](https://www.marco-starker.de/de/projekte/minecraft-entity-view): Live-Karte und Statistik
  aller Entities, nutzt diese API.
- [mob-cap-multiplier](https://www.marco-starker.de/de/projekte/mob-cap-multiplier): verändert das Mob-Cap je
  Kategorie – praktisch zusammen mit der Live-Karte.
