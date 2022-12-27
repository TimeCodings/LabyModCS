package dev.timecoding.labymodcs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import dev.timecoding.labymodcs.api.MetricsLite;
import dev.timecoding.labymodcs.api.UpdateChecker;
import dev.timecoding.labymodcs.api.reflection.SUBController;
import dev.timecoding.labymodcs.api.reflection.enums.SubVersion;
import dev.timecoding.labymodcs.command.SubtitleCommand;
import dev.timecoding.labymodcs.command.completer.SubtitleCompleter;
import dev.timecoding.labymodcs.file.FileManager;
import dev.timecoding.labymodcs.listener.ListenerClass;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import me.clip.placeholderapi.PlaceholderAPI;


public class CustomSubtitles extends JavaPlugin{
	
	public static File file = new File("plugins//LabyMod-Subtitles", "config.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	public static String prefix = "";
	public static boolean papi;
	public static boolean newupdate;

	private static SUBController controller;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		this.controller = new SUBController(this, getSubVersion());
		boolean disable = false;
		if(getSubVersion() == SubVersion.UNKNOWN){
			Bukkit.getConsoleSender().sendMessage("§eGerman: §cHey, das Plugin wurde soeben deaktiviert, da es auf der falschen Minecraft-Version verwendet worden ist! NUR diese Versionen sind kompatibel mit diesem Plugin: 1.8.8, 1.12.2, 1.16.5");
			Bukkit.getConsoleSender().sendMessage("§eEnglish: §cHey, this plugin got disabled because you used it with the wrong Minecraft-Version! The only Minecraft-Versions that are compatible are: 1.8.8, 1.12.2, 1.16.5");
		    disable = true;
		}
		if(!disable) {
			new MetricsLite(this, 9021);
			new UpdateChecker(this, 12345).getVersion(version -> {
				if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
					newupdate = false;
				} else {
					Bukkit.getConsoleSender().sendMessage("");
					Bukkit.getConsoleSender().sendMessage("§bLabyModCS §f| §eEs wurde ein neues Update gefunden! / There is a new update: §fhttps://www.spigotmc.org/resources/labymod-custom-subtitles-configurable-with-api-many-options-easy-with-papi-support.84437/");
					Bukkit.getConsoleSender().sendMessage("");
					newupdate = true;
				}
			});

			createConfig();
			save();
			prefix = cfg.getString("Prefix") + " ";
			FileManager.addDefaults();
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				Bukkit.getConsoleSender().sendMessage("§aEs wurde die PlaceholderAPI auf dem Server gefunden! Du kannst nun auch Placeholder aus dieser API nutzen!");
				Bukkit.getConsoleSender().sendMessage("§aWe found the PlaceholderAPI on this server! Now you can use Placeholders from this API!");
				papi = true;
			} else {
				papi = false;
			}
			getServer().getPluginManager().registerEvents(new ListenerClass(), this);
			PluginCommand command = this.getCommand("labymodsubtitle");
			command.setExecutor(new SubtitleCommand());
			command.setTabCompleter(new SubtitleCompleter());
			Bukkit.getConsoleSender().sendMessage("§e-----------------------------------");
			Bukkit.getConsoleSender().sendMessage("§fProdukt: §bLabyMod §9Custom Subtitles");
			Bukkit.getConsoleSender().sendMessage("§aEntwickler/Developer: §eTimeCode");
			Bukkit.getConsoleSender().sendMessage("§cVersion: §b" + getDescription().getVersion());
			Bukkit.getConsoleSender().sendMessage("§e-----------------------------------");
			if (cfg.getBoolean("RefreshSubtitles") == true) {
				Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

					@Override
					public void run() {
						for (Player all : Bukkit.getOnlinePlayers()) {
							if (FileManager.st.get(all.getUniqueId().toString()) != null) {
								refreshSubtitle(FileManager.st.getString(all.getUniqueId().toString()).replace("&", "§"), all);
							}
						}

					}
				}, 0, 20 * cfg.getInt("RefreshTime(Seconds)"));
			}
		}else{
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	public static String getMessage(String name) {
		String s = null;
		if(cfg.get("Messages." + name) != null) {
			s = prefix + cfg.getString("Messages." + name);
		}
		return s.replace("&", "§");
	}

	public SubVersion getSubVersion(){
		String version3 = "net.minecraft.server.v1_8_R3.PacketDataSerializer";
		String version4 = "net.minecraft.server.v1_12_R1.PacketDataSerializer";
		String version6 = "net.minecraft.server.v1_16_R3.PacketDataSerializer";
		if(classExists(version3)){
			return SubVersion.v1_8;
		}else if(classExists(version4)){
			return SubVersion.v1_12;
		}else if(classExists(version6)){
			return SubVersion.v1_16;
		}else{
			return SubVersion.UNKNOWN;
		}
	}

	private boolean classExists(String name){
		try {
			Class.forName(name);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public void createConfig() {
		cfg.options().copyDefaults(true);
		cfg.options().copyHeader(true);
		cfg.options().header("Hier kannst du generelle Permissions, Nachrichten oder Sonstiges einstellen!" + "\n" +
		"Wichtig: Die Größe der Subtitles kann nur zwischen 0.8-1.6 liegen, sonst kommen Fehler auf! (1.6 ist Minecraft Standart Größe)");
		cfg.addDefault("SubtitleSize", 1.2D);
		cfg.addDefault("Prefix", "&f[&bServer&f]");
		cfg.addDefault("ListFormat", "&f- &eID: &f{id} &7| &eSubtitle: &f{text}");
		cfg.addDefault("RefreshSubtitles", true);
		cfg.addDefault("RefreshTime(Seconds)", 10);
		cfg.addDefault("OppedMessage", true);
		addMessage("Permission", "labycs.use");
		addMessage("NoPerm", "§cDazu hast du keine Rechte!");
		addMessage("Opped", "§cDu bist §4Operator §cund hast alle Berechtigungen! §aUm dir einen einen Subtitle hinzuzufügen mache §e/lst set {player} <Subtitle-ID>");
		addMessage("Console", "§cDies darf man nur als Spieler!");
		addMessage("SyntaxError", "§cBenutzungsfehler! /lst help");
		addMessage("NoNumber", "§4Dies ist keine gültige Ziffer!");
		addMessage("SetPerm", "§aDie Permission §b{perm} §awurde der ID §b{id} §azugeordnet!");
		addMessage("RemovePerm", "§cDie Permission von ID §b{id} §cwurde gelöscht!");
		addMessage("IDNotExists", "§cDiese ID existiert nicht!");
		addMessage("SubtitleDeleted", "§aDu hast den Subtitle §b{id} §cgelöscht! [&7Text: {text}]");
		addMessage("PlayerNoSubtitle", "§cDieser Spieler besitzt keinen Subtitle!");
	   addMessage("SubtitleExists", "§cDieser Subtitle existiert bereits! §2Um ihn zu löschen mache §4/lst del <ID>");
		addMessage("NoSubtitles", "§cEs existieren aktuell keine Subtitles! §eErstelle einen mit /lst create <Text>");
	 addMessage("SubtitleGived", "§aDu hast §b{player} §aden Subtitle §f{text} §azugewiesen!");
		addMessage("SubtitleCreated", "§aDer Subtitle §b{text} §amit der ID §b{id} §awurde erstellt! §2Alle Subtitles und IDs bei /lst list");
	   addMessage("SubtitleRemoved", "§aDu hast den LabyMod Subtitle von §b{player} §centfernt!");
		save();
	}
	
	public static void save() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void refreshSubtitle(String text, Player p) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			sendSubtitle(all, p.getUniqueId(), text);
		}
	}
	
	public static void deleteSubtitle(Player p) {
		JsonArray a = new JsonArray();
		JsonObject s = new JsonObject();
		s.addProperty("uuid", p.getUniqueId().toString());
		a.add(s);
		for(Player all : Bukkit.getOnlinePlayers()) {
			controller.sendClientMessage(all, "account_subtitle", a);
		}
	}
	
	public static void sendSubtitle(Player p, UUID uuid, String text) {

	if(papi == true) {
		text = getTextWithPlaceholders(uuid.toString(), text);
	}		
		JsonArray a = new JsonArray();
		JsonObject s = new JsonObject();
		s.addProperty("uuid", uuid.toString());
		s.addProperty("size", (Double) cfg.get("SubtitleSize"));
		
		if(text != null) {
			    s.addProperty("value", text);		
			a.add(s);
			controller.sendClientMessage(p, "account_subtitle", a);
		}
	}
	
	public static String getTextWithPlaceholders(String uuid, String text) {
		return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(uuid), text);
	}
	
	public void addMessage(String n, String msg) {
		cfg.addDefault("Messages." + n, msg.replace("§", "&"));
	}

}
