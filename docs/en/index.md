---
title: Overview
description: NeoForge mod that serves the players, dimensions, loaded chunks and entities of a Minecraft server as JSON over HTTP. Compatibility, status and next steps.
order: 0
section: Getting started
---

`minecraft-api` is a small server mod for NeoForge. Together with the Minecraft server it starts an HTTP server that
returns the current state of the world as JSON:

- **Players:** name, dimension, position, head rotation, health, armor, game mode and level
- **Dimensions:** spawn point and world border of every dimension
- **Loaded chunks:** every chunk the server currently keeps in memory, with its status
- **Entities:** every mob, item and vehicle with type, category, position, chunk, tags and – for items – the count

On top of that the mod remembers for every entity when it first appeared in the world (wall-clock time and game time).
That makes it easy to spot mobs that have been standing around forever.

The mod was built as the data source for [minecraft-entity-view](https://www.marco-starker.de/en/projects/minecraft-entity-view),
a live map of all entities in the browser. The API is deliberately simple, though, so your own scripts, dashboards or
Home Assistant can query it just as well.

## Compatibility

| Game                       | Loader                        |
|----------------------------|-------------------------------|
| Minecraft 1.21.8 (exactly) | NeoForge 21.8.33 or newer     |

## Status

> [!IMPORTANT]
> The mod is no longer developed. It works with Minecraft 1.21.8; updates for newer versions are not planned. The code
> stays public – forks are welcome.

## Next

- [Installation](installation.md): build or download the mod and put it on the server
- [Configuration](configuration.md): port and protection
- [HTTP API](api.md): all endpoints with example responses

## Related projects

- [minecraft-entity-view](https://www.marco-starker.de/en/projects/minecraft-entity-view): live map and statistics of
  all entities, built on this API.
- [mob-cap-multiplier](https://www.marco-starker.de/en/projects/mob-cap-multiplier): changes the mob cap per category –
  handy together with the live map.
