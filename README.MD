# BanMan v2
## NOTICE
This plugin is currently in development and is not intended for use on public servers.
## About
This is the second version of my ban management plugin, the first having never been released.
## Description
BanMan is a permissions plugin for Spigot/Bukkit and soon for BungeeCord.
The plugin currently is a work in progress.
## Features
### Customisable web interface
BanMan features a seperatly hosted web interface powered by an intergrade REST API, that allows the querying of bans from the server.
This means that if you host the web app in [BanManWeb](https://github.com/Geek202/BanManWeb) and configure it to point to your sever, your community can
view the list of banned players or query how long they have been banned for.
### Punishment control
The plugin provides an inventory GUI for banning and also allows players to ban silently, or announce to the whole server.
## Planned features
- Pardoning - currently the only way to unban players is to directly remove rows from the database.
- BungeeCord support via a proxy-side plugin
- Different database storage provides (MySQL)
- API for other plugins
- Customisable ban reasons with automatic duration extention for repeat offenders (currenly the reasons are hard-coded)
## HOW TO BUILD
- Clone the code using git
- Open the downloaded folder in a terminal and execute `./gradlew shadowJar` (`./gradlew.bat shadowJar` on windows)
- The built plugin jar is in BanMan-Spigot/build/libs
