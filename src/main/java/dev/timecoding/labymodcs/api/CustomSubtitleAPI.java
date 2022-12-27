package dev.timecoding.labymodcs.api;

import java.util.List;

import dev.timecoding.labymodcs.CustomSubtitles;
import dev.timecoding.labymodcs.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CustomSubtitleAPI {
	
	public static void createSubtitle(String text) {
		List<String> list = FileManager.st.getStringList("Subtitles");
		if(!(list.contains(text))) {
			FileManager.addSubtitle(text);
		    FileManager.st.set(text, "[]");
		    FileManager.saveSubtitles();
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("SubtitleCreated").replace("{text}", text.replace("&", "Â§")).replace("{id}", String.valueOf(list.size() + 1)));
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("SubtitleExists"));
		}
	}
	
	public static Integer getSubtitleSize() {
		Integer i = 0;
		if(FileManager.st.get("Subtitles") != null) {
              i = FileManager.st.getStringList("Subtitles").size();
		}
		return i;
	}
	
	public static String getSubtitleName(Integer id) {
		String s = null;
		if(FileManager.st.get("Subtitles") != null) {
			s = FileManager.st.getStringList("Subtitles").get(id + 1);
		}
		return s;
	}
	
	
	public static void removePerm(String id) {
		if(id.startsWith("1") || id.startsWith("2") || id.startsWith("3") || id.startsWith("4") || id.startsWith("5") || id.startsWith("6") || id.startsWith("7") || id.startsWith("8") || id.startsWith("9")) {
			List<String> list = FileManager.st.getStringList("Subtitles");
			Integer n = Integer.valueOf(id);
			if(n <= list.size()) {
				if(FileManager.st.get("Permissions") != null) {
					List<String> perms = FileManager.st.getStringList("Permissions");
					if(perms.contains(list.get(n - 1))) {
					perms.remove(list.get(n - 1));
					}
					FileManager.d.set(CustomSubtitleAPI.getSubtitles().get(n- 1), null);
					FileManager.st.set("Permissions", perms);
					FileManager.saveDatas();
					FileManager.saveSubtitles();
					Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.getMessage("RemovePerm").replace("&", "§").replace("{id}", id));
				}else {
					Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.prefix.replace("&", "§") + "§cERROR! Please contact the Owner of this Plugin!");
				}
			}else {
				Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.getMessage("IDNotExists"));
			}
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.getMessage("NoNumber"));
		}
	}
	
	public static List<String> getSubtitles() {
		List<String> list = null;
		if(FileManager.st.get("Subtitles") != null) {
			list = FileManager.st.getStringList("Subtitles");
		}
		return list;
	}
	
	public static String getPlayerSubtitleName(String playeruuid) {
		String n = null;
		if(FileManager.st.getString(playeruuid) != null) {
			n = FileManager.st.getString(playeruuid);
		}
		return n;
	}
	
	public static Integer getPlayerSubtitleID(String playeruuid) {
		Integer id = null;
		
		for(int i = 0; i <= getSubtitles().size(); i++) {
			if(getSubtitles().get(i).equalsIgnoreCase(getPlayerSubtitleName(playeruuid))) {
				id = i + 1;
			}
		}
		return id;
	}
	
	public static void deleteSubtitle(Integer id) {
		List<String> list = FileManager.st.getStringList("Subtitles");
		Integer n = Integer.valueOf(id);
		if(n <= list.size()) {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("SubtitleDeleted").replace("{id}", ""+id).replace("{text}", list.get(n - 1).replace("&", "Â§")));
			list.remove(list.get(n - 1));
			List<String> perms = FileManager.st.getStringList("Permissions");
			if(FileManager.st.get("" + id) != null) {
				FileManager.st.set("" + id, null);
				perms.remove(FileManager.st.getString(list.get(n )));
				FileManager.st.set("Permissions", perms);
				List<String> uuid = FileManager.st.getStringList(list.get(n - 1));
				for(String uuids : uuid) {
					OfflinePlayer t = Bukkit.getOfflinePlayer(uuids);
					FileManager.st.set(uuids, null);
					FileManager.saveSubtitles();
					if(t != null) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(FileManager.st.get(all.getUniqueId().toString()) != null) {
								CustomSubtitles.refreshSubtitle(FileManager.st.getString(all.getUniqueId().toString()).replace("&", "Â§"), all);
							}
						}
					}
				}
				FileManager.st.set(list.get(n ), null);
				FileManager.d.set(list.get(n ), null);
				FileManager.saveDatas();
				FileManager.saveSubtitles();
			}
			FileManager.st.set("Subtitles", list);
			FileManager.saveSubtitles();
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("IDNotExists"));
		}
	}
	
	public static void setSubtitle(String playername, Integer id) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(playername);
		String playeruuid = p.getUniqueId().toString();
		List<String> list = FileManager.st.getStringList("Subtitles");
		Integer n = Integer.valueOf(id);
		if(n <= list.size()) {
		   FileManager.st.set(playeruuid, list.get(n - 1));
		   if(FileManager.st.get(list.get(n - 1)) != null) {
			   for(String s : list) {
				   if(FileManager.st.get(s) != null) {
					   List<String> na = FileManager.st.getStringList(s);
					   na.remove(playeruuid);
					   FileManager.st.set(s, na);
					   FileManager.saveSubtitles();
				   }
			   }
			   List<String> ta = FileManager.st.getStringList(list.get(n - 1));
				   ta.add(playeruuid);
				   FileManager.st.set(list.get(n - 1), ta);
				   FileManager.saveSubtitles();
			   
		   }
		   FileManager.saveSubtitles();
		   
		   if(p.isOnline()) {
			   Player t2 = Bukkit.getPlayer(playername);
			   CustomSubtitles.refreshSubtitle(FileManager.st.getString(playeruuid).replace("&", "Â§"), t2);
			   
		   }
		   Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("SubtitleGived").replace("{player}", playername).replace("{text}", FileManager.st.getString(playeruuid).replace("&", "Â§")).replace("{id}", String.valueOf(n - 1)));
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("IDNotExists"));
		}
	}
	
	public static void removeSubtitle(String playername) {
		OfflinePlayer t = Bukkit.getOfflinePlayer(playername);
		String uuid = t.getUniqueId().toString();
		if(FileManager.st.get(uuid) != null) {
			FileManager.st.set(uuid, null);
			FileManager.saveSubtitles();
			if(t.isOnline()) {
				Player t2 = Bukkit.getPlayer(playername);
				CustomSubtitles.deleteSubtitle(t2);
			}
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("SubtitleRemoved").replace("{player}", t.getName()));
		
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" + CustomSubtitles.getMessage("PlayerNoSubtitle").replace("{player}", t.getName()));
		}
	}
	
	public static void setPerm(Integer id, String permission) {
		List<String> list = FileManager.st.getStringList("Subtitles");
		Integer n = Integer.valueOf(id);
		if(n <= list.size()) {
			if(FileManager.st.get("Permissions") != null) {
				List<String> perms = FileManager.st.getStringList("Permissions");
				if(!(perms.contains(list.get(n - 1)))) {
				perms.add(list.get(n - 1));
				}
				FileManager.st.set("Permissions", perms);
				FileManager.d.set(list.get(n - 1), permission);
				FileManager.saveDatas();
				FileManager.saveSubtitles();
			}else {
				Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.prefix.replace("&", "Â§") + "Â§cERROR! Please contact the Owner of this Plugin!");
			}
		}else {
			Bukkit.getConsoleSender().sendMessage("§f[§aAPI§f]" +CustomSubtitles.getMessage("IDNotExists"));
		}
	}

}
