package dev.timecoding.labymodcs.command;

import java.util.List;

import dev.timecoding.labymodcs.api.CustomSubtitleAPI;
import dev.timecoding.labymodcs.CustomSubtitles;
import dev.timecoding.labymodcs.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.help.CustomIndexHelpTopic;
import org.bukkit.entity.Player;

public class SubtitleCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission(CustomSubtitles.getMessage("Permission"))) {
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("set")) {
					OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
					String id = args[2];
					if(id.startsWith("1") || id.startsWith("2") || id.startsWith("3") || id.startsWith("4") || id.startsWith("5") || id.startsWith("6") || id.startsWith("7") || id.startsWith("8") || id.startsWith("9")) {
						List<String> list = FileManager.st.getStringList("Subtitles");
						Integer n = Integer.valueOf(id);
						if(n <= list.size()) {
						   FileManager.st.set(t.getUniqueId().toString(), list.get(n - 1));
						   if(FileManager.st.get(list.get(n - 1)) != null) {
							   for(String s : list) {
								   if(FileManager.st.get(s) != null) {
									   List<String> na = FileManager.st.getStringList(s);
									   na.remove(t.getUniqueId().toString());
									   FileManager.st.set(s, na);
									   FileManager.saveSubtitles();
								   }
							   }
							   List<String> ta = FileManager.st.getStringList(list.get(n - 1));
								   ta.add(t.getUniqueId().toString());
								   FileManager.st.set(list.get(n - 1), ta);
								   FileManager.saveSubtitles();
							   
						   }
						   FileManager.saveSubtitles();
						   
						   if(t.isOnline()) {
							   Player t2 = Bukkit.getPlayer(args[1]);
							   CustomSubtitles.refreshSubtitle(FileManager.st.getString(t.getUniqueId().toString()).replace("&", "§"), t2);
							   
						   }
						   p.sendMessage(CustomSubtitles.getMessage("SubtitleGived").replace("{player}", t.getName()).replace("{text}", FileManager.st.getString(t.getUniqueId().toString()).replace("&", "§")).replace("{id}", String.valueOf(n - 1)));
						}else {
							p.sendMessage(CustomSubtitles.getMessage("IDNotExists"));
						}
					}else {
						p.sendMessage(CustomSubtitles.getMessage("NoNumber"));
					}
				}else if(args[0].equalsIgnoreCase("create")) {
					String msg = "";
				for(int i = 1; i<args.length; i++) 
					msg = String.valueOf(msg) + args[i] + " ";
				   msg = msg.trim();
					List<String> list = FileManager.st.getStringList("Subtitles");
					if(!(list.contains(msg))) {
						FileManager.addSubtitle(msg);
					    FileManager.st.set(msg, "[]");
					    FileManager.saveSubtitles();
						p.sendMessage(CustomSubtitles.getMessage("SubtitleCreated").replace("{text}", msg.replace("&", "§")).replace("{id}", String.valueOf(list.size() + 1)));
					}else {
						p.sendMessage(CustomSubtitles.getMessage("SubtitleExists"));
					}
					
				}else if(args[0].equalsIgnoreCase("setpermission") || args[0].equalsIgnoreCase("setperm")){
					String id = args[1];
					String perm = args[2];
					if(id.startsWith("1") || id.startsWith("2") || id.startsWith("3") || id.startsWith("4") || id.startsWith("5") || id.startsWith("6") || id.startsWith("7") || id.startsWith("8") || id.startsWith("9")) {
						List<String> list = FileManager.st.getStringList("Subtitles");
						Integer n = Integer.valueOf(id);
						if(n <= list.size()) {
							if(FileManager.st.get("Permissions") != null) {
								List<String> perms = FileManager.st.getStringList("Permissions");
								if(!(perms.contains(list.get(n - 1)))) {
								perms.add(list.get(n - 1));
								}
								FileManager.st.set("Permissions", perms);
								FileManager.d.set(list.get(n - 1), perm);
								FileManager.saveDatas();
								FileManager.saveSubtitles();
								p.sendMessage(CustomSubtitles.getMessage("SetPerm").replace("&", "§").replace("{id}", id).replace("{perm}", perm).replace("{permission}", perm));
							}else {
								p.sendMessage(CustomSubtitles.prefix.replace("&", "§") + "§cERROR! Please contact the Owner of this Plugin!");
							}
						}else {
							p.sendMessage(CustomSubtitles.getMessage("IDNotExists"));
						}
					}else {
						p.sendMessage(CustomSubtitles.getMessage("NoNumber"));
					}
				}else {
					p.sendMessage(CustomSubtitles.getMessage("SyntaxError"));
				}
			}else if(args.length == 0) {
				p.sendMessage(CustomSubtitles.getMessage("SyntaxError"));
			}else if(args.length >= 2 || args.length != 0 && args.length != 1 && args.length <= 2) {
				String args2 = args[0];
				if(args2.equalsIgnoreCase("create")) {
					String msg = "";
					
				for(int i = 1; i<args.length; i++) 
					msg = String.valueOf(msg) + args[i] + " ";
				 msg = msg.trim();
					List<String> list = FileManager.st.getStringList("Subtitles");
					if(!(list.contains(msg))) {
						FileManager.addSubtitle(msg);
					    FileManager.st.set(msg, "[]");
					    FileManager.saveSubtitles();
						p.sendMessage(CustomSubtitles.getMessage("SubtitleCreated").replace("{text}", msg.replace("&", "§")).replace("{id}", String.valueOf(list.size() + 1)));
					}else {
						p.sendMessage(CustomSubtitles.getMessage("SubtitleExists"));
					}
				
			
				
				}else if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("delete")) {
					String id = args[1];
					if(id.startsWith("1") || id.startsWith("2") || id.startsWith("3") || id.startsWith("4") || id.startsWith("5") || id.startsWith("6") || id.startsWith("7") || id.startsWith("8") || id.startsWith("9")) {
						List<String> list = FileManager.st.getStringList("Subtitles");
						Integer n = Integer.valueOf(id);
						if(n <= list.size()) {
							p.sendMessage(CustomSubtitles.getMessage("SubtitleDeleted").replace("{id}", id).replace("{text}", list.get(n - 1).replace("&", "§")));
							list.remove(list.get(n - 1));
							List<String> perms = FileManager.st.getStringList("Permissions");
							if(FileManager.st.get(id) != null) {
								FileManager.st.set(id, null);
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
												CustomSubtitles.refreshSubtitle(FileManager.st.getString(all.getUniqueId().toString()).replace("&", "§"), all);
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
							p.sendMessage(CustomSubtitles.getMessage("IDNotExists"));
						}
						
					}else {
						p.sendMessage(CustomSubtitles.getMessage("NoNumber"));
					}
				}else if(args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("remove")) {
					OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
					String uuid = t.getUniqueId().toString();
					if(FileManager.st.get(uuid) != null) {
						FileManager.st.set(uuid, null);
						FileManager.saveSubtitles();
						if(t.isOnline()) {
							Player t2 = Bukkit.getPlayer(args[1]);
							CustomSubtitles.deleteSubtitle(t2);
						}
						p.sendMessage(CustomSubtitles.getMessage("SubtitleRemoved").replace("{player}", t.getName()));
					
					}else {
						p.sendMessage(CustomSubtitles.getMessage("PlayerNoSubtitle").replace("{player}", t.getName()));
					}
				}else if(args[0].equalsIgnoreCase("remperm") || args[0].equalsIgnoreCase("removePermission") || args[0].equalsIgnoreCase("removePerm")){
					String id = args[1];
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
								p.sendMessage(CustomSubtitles.getMessage("RemovePerm").replace("&", "§").replace("{id}", id));
							}else {
								p.sendMessage(CustomSubtitles.prefix.replace("&", "§") + "§cERROR! Please contact the Owner of this Plugin!");
							}
						}else {
							p.sendMessage(CustomSubtitles.getMessage("IDNotExists"));
						}
					}else {
						p.sendMessage(CustomSubtitles.getMessage("NoNumber"));
					}
				}else {
					p.sendMessage(CustomSubtitles.getMessage("SyntaxError"));
				}
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					List<String> list = FileManager.st.getStringList("Subtitles");
					if(list.size() != 0) {
						Integer id = 0;
						p.sendMessage("§fAlle Subtitel / All Subtitles:");
						for(String lines : list) {
							id = id + 1;
							p.sendMessage(CustomSubtitles.cfg.getString("ListFormat").replace("&", "§").replace("{id}", String.valueOf(id)).replace("{text}", lines.replace("&", "§")));
						}
					}else {
						p.sendMessage(CustomSubtitles.getMessage("NoSubtitles"));
					}
				}else if(args[0].equalsIgnoreCase("help")) {
					p.sendMessage("§e----------------------------------------");
					p.sendMessage("§b/lst list §f| §eSehe alle Subtitel und Subtitel-IDs ein/You can see all Subtitles and all Subtitle-IDs");
					p.sendMessage("§b/lst create <Subtitle-Text> §f| §eErstelle einen neuen Subtitle/Create a new Subtitle");
					p.sendMessage("§b/lst delete <Subtitle-ID> §f| §eLösche einen Subtitel/Delete a Subtitle");
					p.sendMessage("§b/lst set <Player-Name> <Subtitle-ID> §f| §eGebe einem Spieler einen Subtitel/Give a player an Subtitle");
					p.sendMessage("§b/lst remove <Player-Name> §f| §eLösche den Subtitel eines Spielers/Remove a Subtitle for a player");
					p.sendMessage("§b/lst setperm <Subtitle-ID> <Permission> §f| §eGebe einem Subtitel eine Permission/Give a Subtitle an Permission");
					p.sendMessage("§b/lst remPerm <Subtitle-ID> §f| §eLösche eine Permission von einem Subtitel/Delete a Permission from a Subtitle");
					p.sendMessage("§c(Du kannst alle Permissions in der Data.yml auslesen / You can see all Permissions in the Data.yml)");
					p.sendMessage("§e----------------------------------------");
				}else {
					p.sendMessage(CustomSubtitles.getMessage("SyntaxError"));
				}
			}
			}else {
				p.sendMessage(CustomSubtitles.getMessage("NoPerm"));
			}
		}else {
			sender.sendMessage(CustomSubtitles.getMessage("Console"));
		}
		return false;
	}
	
	
	

}
