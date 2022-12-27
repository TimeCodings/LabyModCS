package dev.timecoding.labymodcs.listener;

import dev.timecoding.labymodcs.CustomSubtitles;
import dev.timecoding.labymodcs.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerClass implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(FileManager.st.get(all.getUniqueId().toString()) != null) {
				CustomSubtitles.refreshSubtitle(FileManager.st.getString(all.getUniqueId().toString()).replace("&", "§"), all);
			}else {
				CustomSubtitles.deleteSubtitle(all);
			}

		}
		if(CustomSubtitles.cfg.getBoolean("OppedMessage") == true) {
		if(!(p.isOp())) {
		for(String classes : FileManager.st.getStringList("Permissions")) {
			if(FileManager.d.get(classes) != null) {
				if(p.hasPermission(FileManager.d.getString(classes))) {
					CustomSubtitles.refreshSubtitle(classes, p);
				}
			}
		}
		}else {
			if(CustomSubtitles.newupdate == true) {
				p.sendMessage("§e----------------------------------");
				p.sendMessage("§bEin neues Update ist verfügbar! /");
				p.sendMessage("§bA new update is available");
				p.sendMessage("§cDownload:");
				p.sendMessage("https://www.spigotmc.org/resources/labymod-custom-subtitles-configurable-with-api-many-options-easy-with-papi-support.84437/");
				p.sendMessage("§e----------------------------------");
			}
			if(FileManager.st.get(uuid) == null) {
			p.sendMessage(CustomSubtitles.getMessage("Opped").replace("{player}", p.getName()));
			}
		}
		}else {
			if(!(p.isOp())) {
				for(String classes : FileManager.st.getStringList("Permissions")) {
					if(FileManager.d.get(classes) != null) {
						if(p.hasPermission(FileManager.d.getString(classes))) {
							CustomSubtitles.refreshSubtitle(classes, p);
						}
					}
				}
				}
		}
		if(FileManager.st.get(uuid) != null) {
			CustomSubtitles.refreshSubtitle(FileManager.st.getString(uuid).replace("&", "§"), p);
		}
	}

}
