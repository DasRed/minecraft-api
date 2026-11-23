---
title: Installation
description: Download minecraft-api for a Minecraft 1.21.8 server with NeoForge and Java 21 as a JAR or build it yourself, put it into the mods folder and check it works.
order: 10
section: Getting started
---

The mod runs on the server. It adds no blocks, items or network channels and needs no other mods besides NeoForge.

## Requirements

- Minecraft server 1.21.8 with NeoForge 21.8.33 or newer
- Java 21 (required for NeoForge 1.21.8 anyway)
- a free port for the API, `9874` by default

## Download the JAR

[Releases](https://github.com/DasRed/minecraft-api/releases) has the ready-made file `minecraft_api-<version>.jar`.
Put it into the `mods/` folder of the server and restart it.

## Build it yourself

```bash
git clone https://github.com/DasRed/minecraft-api.git
cd minecraft-api
./gradlew build
```

The file is then in `build/libs/minecraft_api-<version>.jar`.

## Check

After the start the server log shows:

```text
[MinecraftAPI] API started at port 9874
```

A request returns players and dimensions:

```bash
curl http://localhost:9874/
```

> [!WARNING]
> The API has no authentication. Do not expose the port to the internet, see [Configuration](configuration.md#protection).
