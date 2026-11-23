---
title: Configuration
description: The server config of minecraft-api with the port of the HTTP API, and how to restrict access to your own network because the API has no authentication.
order: 20
slug: configuration
section: Setup
---

The mod has a server configuration. NeoForge creates it when a world starts for the first time, at
`<world>/serverconfig/minecraft_api-server.toml`.

## Settings

| Name       | Default | Description                                |
|------------|---------|--------------------------------------------|
| `api.port` | `9874`  | Port of the HTTP server (1 to 65535)       |

```toml
[api]
	port = 9874
```

The HTTP server starts once the Minecraft server has finished loading and stops with it. Restart the server after
changing the port.

## Protection

The HTTP server listens on all network interfaces, requires no authentication and allows requests from any website
(`Access-Control-Allow-Origin: *`). Anyone who can reach the port sees player names, positions and all entities.

> [!CAUTION]
> Do not expose the port to the internet. Keep it reachable only within your own network or between containers, for
> example with a firewall or by not publishing it in Docker.
