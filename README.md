![Unbenannt-2 (2) (2)](https://user-images.githubusercontent.com/94994775/209662058-f24c60ce-9133-4abf-a2a6-3d72ba4a33b2.png)

**Spigot-Site:** https://www.spigotmc.org/resources/labymod-custom-subtitles-configurable-with-api-many-options-easy-with-papi-support.84437/

*German:*

**WICHTIG: DIESES PLUGIN LÄUFT NUR NOCH AUF FOLGENDEN MINECRAFT-VERSIONEN: 1.8.9 (bzw. 1.8.8); 1.12.2 und 1.16.5
AUF ANDEREN MINECRAFT-VERSIONEN WIRD DAS PLUGIN NICHT EINMAL STARTEN**

**Information:** Die LabyMod API wird für dieses Plugin NICHT benötigt! Außerdem sind die Subtitle nur sichtbar falls man aktuell LabyMod spielt! Außerdem enthält das Plugin einen PlaceholderAPI Support

Falls ihr Hilfe bezüglich des Plugin benötigt, joint doch gerne meinem Discord und eröffnet ein Support-Ticket: https://discord.gg/8QWmU4ebCC

Hallo, ich habe mal wieder mich hingesetzt und ein neues Plugin für euch programmiert mit den ihr Spielern LabyMod Subtitel geben könnt! Dieses Plugin enthält eine Große Config, eine Developer API und vieles weitere...

Kommen wir als erstes einmal zu den Commands:

/lst list | Zeigt dir alle Subtitel und Subtitel-IDs ein
/lst create <Subtitle-Text> | Erstellt einen neuen Subtitel
/lst delete <Subtitle-ID> | Löscht den Subtitle welcher mit der ID ausgewählt wurde
/lst set <Player-Name> <Subtitle-ID> | Gebe einem Spieler einen Subtitel
/lst remove <Player-Name> | Lösche den Subtitel eines Spielers
/lst setperm <Subtitle-ID> <Permission> | Gebe einem Subtitel eine Permission/Berechtigung
/lst remperm <Subtitle-ID> | Lösche die Berechtigung eines Subtitels
Wenn du alle Berechtigungen der Subtitel einsehen willst schau doch mal in die Data.yml ;)

Nochmal eine Kurze Zusammenfassung was du mit den Subtiteln machen kannst:

- Erstellen
- Löschen
- Anzeigen/Auflisten
- Spielern geben
- Spielern wegnehmen
- Berechtigungen zuweisen

Empfohlene Anleitung

1. Erstelle einen Subtitel (mit /lst create <Subtitle-Text>)
2. Merke dir die Subtitel-ID oder sehe sie ein mit /lst list
3. Gebe ihm einen Spieler (mit /lst set <Player-Name> <Subtitle-ID>) oder stelle eine Permission ein und gib sie dem Spieler/Spielern welche diesen Subtitel haben sollen
4. Das wars! So einfach geht es :D

API

Du kannst die API benutzen wenn du das Plugin in die Build Path deiner Entwicklungsumgebung hinzufügst! Jetzt musst du nur noch CustomSubtitleAPI. machen und du siehst jede menge Funktionen der API!

Wichtig: Damit du die API nutzen kannst muss logischerweise auch das Plugin auf deinem Server sein!

--------------------------------------------------------------------------------------------------

*English:*

**IMPORTANT: THIS PLUGIN ONLY RUNS ON FOLLOWING MINECRAFT-VERSIONS: 1.8.9 (1.8.8); 1.12.2 und 1.16.5
ON OTHER MINECRAFT-VERSIONS THE PLUGIN WON'T START**

**Information:** The LabyMod API is NOT required in this plugin! In addition, the subtitles are only visible if you are currently playing LabyMod! Also there is a PlaceHolderAPI support!

If you need help because for example something isnt working on this plugin, just join my discord and create a ticket: https://discord.gg/8QWmU4ebCC

Hello, I sat down again and programmed a new plugin for you with which you can give players LabyMod subtitles! This plugin contains a large config, a developer API and much more ...

First of all, let I show you the commands:

/ lst list | Shows you all subtitles and subtitle IDs
/ lst create <subtitle text> | Creates a new subtitle
/ lst delete <subtitle ID> | Deletes the subtitle which was selected with the ID
/ lst set <player name> <subtitle ID> | Give a player a subtitle
/ lst remove <player name> | Delete a player's subtitle
/ lst setperm <Subtitle-ID> <Permission> | Give a subtitle a permission
/ lst remperm <Subtitle-ID> | Remove the Permission of an Subtitle
You can see all Subtitle-Permissions in the Data.yml ;)

Again a short summary of what you can do with the subtitles:

- Create
- Clear
- View / list
- give to players
- Take away from players
- Assign permissions

Recommended guide

1. Create a subtitle (with / lst create <Subtitle-Text>)
2. Make a note of the subtitle ID or see it with / lst list
3. Give him a player (with / lst set <Player-Name> <Subtitle-ID>) or set a permission and give it to the player / players who should have this subtitle
4. That's it! It's that easy: D

API

You can use the API when you put the plugin in the your build path!
Make CustomSubtitleAPI. and you can see all things you can use!

Important: For using the API, the API also need to be on your Minecraft-Server!
