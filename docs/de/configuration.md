---
title: Konfiguration
description: Die Server-Konfiguration von minecraft-api mit dem Port der HTTP-API und wie du den Zugriff auf dein Netz beschränkst, denn die API kennt keine Anmeldung.
order: 20
slug: konfiguration
section: Einrichtung
---

Die Mod hat eine Server-Konfiguration. NeoForge legt sie beim ersten Start einer Welt an unter
`<welt>/serverconfig/minecraft_api-server.toml`.

## Einstellungen

| Name       | Standard | Beschreibung                                 |
|------------|----------|----------------------------------------------|
| `api.port` | `9874`   | Port des HTTP-Servers (1 bis 65535)          |

```toml
[api]
	port = 9874
```

Der HTTP-Server startet, wenn der Minecraft-Server fertig geladen ist, und stoppt mit ihm. Nach einer Änderung am Port
den Server neu starten.

## Absicherung

Der HTTP-Server lauscht auf allen Netzwerkschnittstellen, verlangt keine Anmeldung und erlaubt Abfragen von jeder
Webseite (`Access-Control-Allow-Origin: *`). Jeder, der den Port erreicht, sieht also Spielernamen, Positionen und alle
Entities.

> [!CAUTION]
> Gib den Port nicht ins Internet frei. Lass ihn nur im eigenen Netz oder zwischen Containern erreichbar, etwa per
> Firewall oder indem du ihn in Docker nicht nach außen veröffentlichst.
