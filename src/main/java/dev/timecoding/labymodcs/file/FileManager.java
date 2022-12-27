package dev.timecoding.labymodcs.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File f = new File("plugins//LabyMod-Subtitles" , "subtitles.yml");
	public static YamlConfiguration st = YamlConfiguration.loadConfiguration(f);
	public static File file = new File("plugins//LabyMod-Subtitles", "datas.yml");
	public static YamlConfiguration d = YamlConfiguration.loadConfiguration(file);

	
	public static void addDefaults() {
		st.options().copyDefaults(true);
		st.options().copyHeader(true);
		d.options().
		copyDefaults(true);
		d.options().copyHeader(true);
		d.options().header("In dieser Datei werden wieder wichtige Daten für das Plugin eingespeichert! Bitte nicht ändern, falls du dich damit nicht auskennst!");
		st.options().header("In dieser Datei werden alles Subtitles mit Ids abgespeichert! Falls du dich damit nicht auskennst, lass lieber die Finger weg von dieser Datei ;)");
		st.addDefault("Subtitles", "[]");
		st.addDefault("Permissions", "[]");
		saveDatas();
		saveSubtitles();
	}
	
	public static void addSubtitle(String text) {
		List<String> list = st.getStringList("Subtitles");
		list.add(text);
		st.set("Subtitles", list);
		saveSubtitles();
	}
	
	public static void removeSubtitle(String text) {
		List<String> list = st.getStringList("Subtitles");
		list.remove(text);
		st.set("Subtitles", list);
		saveSubtitles();
	}
	
	public static void editSubtitle(Integer id, String text) {
		List<String> list = st.getStringList("Subtitles");
		list.set(id, text);
		st.set("Subtitles", list);
		saveSubtitles();
	}
	
	public static void saveDatas() {
		try {
			d.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSubtitles() {
		try {
			st.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
