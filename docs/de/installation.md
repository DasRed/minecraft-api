---
title: Installation
description: minecraft-api als JAR herunterladen oder selbst bauen und in den mods-Ordner des Servers legen.
order: 10
section: Einstieg
---

Die Mod läuft auf dem Server. Sie fügt keine Blöcke, Items oder Netzwerkkanäle hinzu und braucht außer NeoForge keine
weiteren Mods.

## Voraussetzungen

- Minecraft-Server 1.21.8 mit NeoForge 21.8.33 oder neuer
- Java 21 (wie für NeoForge 1.21.8 ohnehin nötig)
- ein freier Port für die API, standardmäßig `9874`

## JAR herunterladen

Unter [Releases](https://github.com/DasRed/minecraft-api/releases) liegt die fertige Datei
`minecraft_api-<version>.jar`. Lege sie in den Ordner `mods/` des Servers und starte ihn neu.

## Selbst bauen

```bash
git clone https://github.com/DasRed/minecraft-api.git
cd minecraft-api
./gradlew build
```

Die Datei liegt danach in `build/libs/minecraft_api-<version>.jar`.

## Prüfen

Nach dem Start steht im Server-Log:

```text
[MinecraftAPI] API started at port 9874
```

Ein Aufruf liefert Spieler und Dimensionen:

```bash
curl http://localhost:9874/
```

> [!WARNING]
> Die API hat keine Anmeldung. Gib den Port nicht ins Internet frei, siehe [Konfiguration](configuration.md#absicherung).
