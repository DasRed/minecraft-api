# minecraft-api

NeoForge server mod that serves the players, dimensions, loaded chunks and entities of a Minecraft server as JSON over
HTTP. It is the data source for [minecraft-entity-view](https://github.com/DasRed/minecraft-entity-view), a live map
of all entities in the browser.

**Documentation:** https://www.marco-starker.de/en/projects/minecraft-api/docs
(German: https://www.marco-starker.de/de/projekte/minecraft-api/doku)

## Compatibility

- Minecraft 1.21.8
- NeoForge 21.8.33 or newer
- Java 21

## Status

This mod is **no longer developed**. It works with Minecraft 1.21.8; there are no plans to port it to newer versions.
Forks are welcome.

## Quick start

1. Download `minecraft_api-<version>.jar` from [Releases](https://github.com/DasRed/minecraft-api/releases) or build
   it with `./gradlew build` (output in `build/libs/`).
2. Put it into the `mods/` folder of the server and start it.
3. Query the API:

   ```bash
   curl http://localhost:9874/entities
   ```

The port is set in `<world>/serverconfig/minecraft_api-server.toml`. The API has no authentication – do not expose it
to the internet.

| Endpoint         | Content                                  |
|------------------|------------------------------------------|
| `/`              | players and dimensions                   |
| `/players`       | online players                           |
| `/dimensions`    | spawn point and world border             |
| `/entities`      | all loaded entities                      |
| `/chunks/active` | all loaded chunks with their status      |

## License

[MIT](LICENSE)
